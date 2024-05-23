package org.megaline.httpserver.http;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class HttpRequest extends HttpMessage {

  private HttpMethod method;
  private String requestTarget;
  private String originalHttpVersion; // Literal version from request
  private HttpVersion BestCompatibleHttpVersion;
  private Map<String, String> headerFields = new HashMap<>();
  private String body;
  private JsonNode jsonBody;

  HttpRequest() {
  }

  public String getRequestTarget() {
    return requestTarget;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public HttpVersion getHttpVersion() {
    return BestCompatibleHttpVersion;
  }

  public String getOriginalHttpVersion() {
    return originalHttpVersion;
  }

  public Map<String, String> getHeaders() {
    return headerFields;
  }

  public String getHeader(String name) {
    return headerFields.get(name);
  }

  public void addHeader(String fieldName, String fieldValue) {
    headerFields.put(fieldName, fieldValue);
  }

  public JsonNode getJsonBody() {
    return jsonBody;
  }

  public void setJsonBody(JsonNode jsonBody) {
    this.jsonBody = jsonBody;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public void setOriginalHttpVersion(String originalHttpVersion) {
    this.originalHttpVersion = originalHttpVersion;
  }

  public HttpVersion getBestCompatibleHttpVersion() {
    return BestCompatibleHttpVersion;
  }

  public void setBestCompatibleHttpVersion(HttpVersion bestCompatibleHttpVersion) {
    BestCompatibleHttpVersion = bestCompatibleHttpVersion;
  }

  public Map<String, String> getHeaderFields() {
    return headerFields;
  }

  public void setHeaderFields(Map<String, String> headerFields) {
    this.headerFields = headerFields;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setRequestTarget(String requestTarget) throws HttpParsingException {
    if (requestTarget == null || requestTarget.length() == 0)
      throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_ERROR);
    this.requestTarget = requestTarget;
  }

  public void setMethod(String methodName) throws HttpParsingException {
    for (HttpMethod method : HttpMethod.values())
      if (methodName.equals(method.name())) {
        this.method = method;
        return;
      }
    throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
  }

  public void setHttpVersion(String originalHttpVersion) throws HttpParsingException {
    this.originalHttpVersion = originalHttpVersion;
    this.BestCompatibleHttpVersion = HttpVersion.getBestCompatibleVerison(originalHttpVersion);
    if (BestCompatibleHttpVersion == null)
      throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);

  }
}
