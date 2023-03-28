package com.terry.economicsimulator.integration.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ResponseData {
  Integer status;
  JsonNode body;

  public String getBody() {
    return body == null ? null : body.toString();
  }

  public static class ResponseDataBuilder {
    private JsonNode body;

    public ResponseDataBuilder body(JsonNode node) {
      this.body = node;
      return this;
    }
  }
}
