package com.terry.economicsimulator.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.economicsimulator.integration.dto.DataValidation;
import com.terry.economicsimulator.integration.dto.IntegrationTestData;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test starts the whole application with an embedded h2 database, loads in test specific SQL, sends a GraphQL request to the /graphQL endpoint and
 * validates the response.
 * <p>
 * To set up a new test case, the following is needed.
 * - Create an optional SQL file for the test data under the `src/test/resources/integrationtest/sql` directory
 * - Create a GraphQL input file under the `src/test/resources/integrationtest/graphql` directory
 * - Create a JSON file of the expected response under the `src/test/resources/integrationtest/json` directory
 * - Create a 'testcase' json file with all necessary testcase fields under the `src/test/resources/integrationtest/testcases` directory
 * testFileNames stream
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class IntegrationTest {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final Pattern UUID_PATTERN = Pattern.compile("[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}");

//  @Autowired
//  @Qualifier("ucUserDetailsService")
//  private UserDetailsService userDetailsService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DSLContext dslContext;

  /**
   * Main test execution method
   * @param testcase
   * @throws Exception
   */
  @ParameterizedTest(name = "{0}")
  @MethodSource("namedTestData")
  @Sql(scripts = {
      "/integrationtest/sql/0-clearData.sql",
      "/integrationtest/sql/0-init.sql"
  }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void integrationTest(IntegrationTestData testcase) throws Exception {
    List<String> dbDataFileName = testcase.getDbDataFileName();
    String graphQLRequestFileName = testcase.getRequest();
    String dataResponseFileName = testcase.getResponse();

    insertDbData(dbDataFileName);

    String requestContent = new JSONObject()
        .put("query", stringFromFile("graphql", graphQLRequestFileName))
        .toString();

    final String correlationId = UUID.randomUUID().toString();

    mockMvc.perform(post("/graphql")
            .servletPath("/graphql")
            .contentType("application/json")
            .content(requestContent))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(json(stringFromFile("json", dataResponseFileName)));

    List<DataValidation> dataValidation = testcase.getDbDataValidation();

    if (dataValidation != null) {
      validateDbData(dataValidation);
    }
  }

  /**
   * Insert test data into DB if it exists, if more than 1 file is present throws error if any files are missing
   * @param dbDataFileName
   */
  private void insertDbData(List<String> dbDataFileName) {
    dbDataFileName.stream()
        .map(fileName -> fileExists("sql", fileName))
        .filter(found -> !found)
        .findAny()
        .map(found -> {
          throw new IllegalStateException("Missing sql file(s)");
        });

    if (dbDataFileName.size() > 0) {
      dbDataFileName.forEach(fileName -> this.dslContext.batch(stringFromFile("sql", fileName)).execute());
    } else {
      log.info("Skipping loading SQL data as no SQL file exists for the test '{}'", dbDataFileName);
    }
  }

  /**
   * Loads all files from the integrationtest/testcases directory, maps the contents to an IntegrationTestData object and finally maps to a named argument.
   */
  static Stream<Arguments> namedTestData() throws Exception {
    //noinspection resource
    return Files.list(Paths.get(ClassLoader.getSystemResource("integrationtest/testcases/").toURI()))
        .filter(file -> !Files.isDirectory(file))
        .map(Path::toFile)
        .map(IntegrationTest::getIntegrationTestData)
        .sorted(Comparator.comparing(IntegrationTestData::getTestCase))
        .map(testData -> Arguments.of(Named.of(testData.getTestCase(), testData)));
  }

  /**
   * Returns the integration test data as the java object type IntegrationTestData
   * @param file
   * @return
   */
  @SneakyThrows
  private static IntegrationTestData getIntegrationTestData(File file) {
    return objectMapper.readValue(file, IntegrationTestData.class);
  }

  /**
   * Checks if a given file exists by file name
   * @param path
   * @param fileName
   * @return
   */
  private boolean fileExists(String path, String fileName) {
    String fullPath = "integrationtest/" + path + "/" + fileName;
    return new ClassPathResource(fullPath).exists();
  }

  /**
   * Returns a string from a file by file name
   *
   * @param path
   * @param fileName
   * @return
   */
  @SneakyThrows
  private String stringFromFile(String path, String fileName) {
    String fullPath = "integrationtest/" + path + "/" + fileName;
    byte[] bytes = new ClassPathResource(fullPath).getInputStream().readAllBytes();
    return new String(bytes, StandardCharsets.UTF_8);
  }

  /**
   * Result matcher which compares the given expectedJson against the result content.
   * Overrides the compareValues function of the DefaultComparator to support ignoring fields
   * by marking them as @IGNORED@ in the expected json.
   */
  public ResultMatcher json(String expectedJson) {
    return (result) -> {
      String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
      DefaultComparator defaultComparator = new DefaultComparator(JSONCompareMode.LENIENT) {
        @Override
        public void compareValues(String prefix, Object expectedValue, Object actualValue, JSONCompareResult result) throws JSONException {
          if ("@IGNORED@".equals(expectedValue)) {
            System.out.println("Ignored field='" + prefix + "', actual value='" + actualValue + "'");
          } else {
            super.compareValues(prefix, expectedValue, actualValue, result);
          }
        }
      };
      JSONCompareResult compareResult = JSONCompare.compareJSON(expectedJson, content, defaultComparator);
      if (compareResult.failed()) {
        throw new AssertionError(compareResult.getMessage());
      }
    };
  }

  /**
   * For each DataValidation in the dbDataValidation list, queries the database and validates the results of the query against the expected results provided in
   * the data field. Comparison is achieved through usage of strings to avoid types (Ex: Int vs short)
   * @param dbDataValidation
   */
  private void validateDbData(List<DataValidation> dbDataValidation) {
    for (DataValidation dataValidation : dbDataValidation) {
      List<List<Object>> expectedData = dataValidation.getData();
      Result<Record> result = dslContext.fetch(dataValidation.getQuery());

      assertEquals(expectedData.size(), result.size(), "Result size from data validation query does not match expected data size");

      List<String> resultStrings = result.stream()
          .map(Record::intoList)
          .map(Object::toString)
          .toList();

      List<String> expectedDataStrings = expectedData.stream()
          .map(Object::toString)
          .toList();

      assertEquals(expectedDataStrings, resultStrings);
    }
  }
}
