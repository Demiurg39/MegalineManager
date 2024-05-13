package org.megaline.httpserver.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {

  public static ObjectMapper objectMapper = defaultObjectMapper();

  public static JsonNode parse(String jsonSrc) throws IOException {
    return objectMapper.readTree(jsonSrc);
  }

  public static <T> T fromJson(JsonNode node, Class<T> class_) throws JsonProcessingException {
    return objectMapper.treeToValue(node, class_);
  }

  public static JsonNode toJson(Object obj) {
    return objectMapper.valueToTree(obj);
  }

  public static String stringify(JsonNode node) throws JsonProcessingException {
    return generateJson(node, false);
  }

  public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
    return generateJson(node, true);
  }

  private static ObjectMapper defaultObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper;
  }

  private static String generateJson(Object obj, boolean pretty) throws JsonProcessingException {
    ObjectWriter objectWriter = objectMapper.writer();
    if (pretty)
      objectWriter.with(SerializationFeature.INDENT_OUTPUT);

    return objectWriter.writeValueAsString(obj);
  }
}
