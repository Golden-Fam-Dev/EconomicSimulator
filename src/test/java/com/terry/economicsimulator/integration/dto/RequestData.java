package com.terry.economicsimulator.integration.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.HashMap;

@Value
@Builder
@Jacksonized
public class RequestData {
  String method;
  String endpoint;
  HashMap<String, String> queryParams;
  JsonNode body;
  @Builder.Default
  String clientId = "testClient"; // Set to null to not include a JWT token in the request

  public String getBody() {
    return body == null ? null : body.toString();
  }

  public static class RequestDataBuilder {
    private JsonNode body;

    public RequestDataBuilder body(JsonNode node) {
      this.body = node;
      return this;
    }
  }
}

