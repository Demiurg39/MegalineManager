package org.megaline.httpserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse extends HttpMessage {

  private HttpVersion httpVersion;
  private HttpStatusCode statusCode;
  private int contentLength;
  private String body;
  private Map<String, String> headers = new HashMap<>();

  public HttpResponse() {
    this.httpVersion = HttpVersion.HTTP_1_1;
    this.statusCode = HttpStatusCode.SERVER_CONNECTED_200;
    this.body = "";
    this.contentLength = 0;
  }

  public HttpResponse(HttpStatusCode statusCode, String body) {
    this.httpVersion = HttpVersion.HTTP_1_1;
    this.statusCode = statusCode;
    this.body = body;
    this.contentLength = body.length();
  }

  public HttpVersion getHttpVersion() {
    return httpVersion;
  }

  public void setHttpVersion(HttpVersion httpVersion) {
    this.httpVersion = httpVersion;
  }

  public HttpStatusCode getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(HttpStatusCode statusCode) {
    this.statusCode = statusCode;
  }

  public int getContentLength() {
    return contentLength;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
    this.contentLength = body.length();
  }

  public void addHeader(String name, String value) {
    headers.put(name, value);
  }

  public String getHeader(String name) {
    return headers.get(name);
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  @Override
  public String toString() {
    StringBuilder response = new StringBuilder();
    response.append(httpVersion).append(" ").append(statusCode).append("\r\n");
    headers.forEach((key, value) -> response.append(key).append(": ").append(value).append("\r\n"));
    response.append("Content-Length: ").append(contentLength).append("\r\n");
    response.append("\r\n");
    response.append(body);
    return response.toString();
  }
}
