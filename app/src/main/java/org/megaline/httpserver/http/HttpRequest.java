package org.megaline.httpserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpMessage {

  private static final int MAX_BODY_LENGTH = 10 * 1024 * 1024; // 10 MB
  private static final int MAX_HEADER_NAME_LENGTH = 1024; // Maximum length for header name
  private static final int MAX_HEADER_VALUE_LENGTH = 4096; // Maximum length for header value

  private HttpMethod method;
  private String requestTarget;
  private String originalHttpVersion;
  private String body;
  private Map<String, String> headers = new HashMap<>();

  // private HttpVersion bestCompatibleHttpVersion;

  HttpRequest() {
  }

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(String methodName) throws HttpParsingException {
    for (HttpMethod method : HttpMethod.values()) {
      if (methodName.equals(method.name())) {
        this.method = method;
        return;
      } else {
        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
      }
    }
  }

  public String getRequestTarget() {
    return requestTarget;
  }

  public void setRequestTarget(String requestTarget) throws HttpParsingException {
    if (requestTarget == null || requestTarget.length() == 0)
      throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);

    this.requestTarget = requestTarget;
  }

  public String getOriginalHttpVersion() {
    return originalHttpVersion;
  }

  public void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
    this.originalHttpVersion = originalHttpVersion;
    // this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
    // if (this.bestCompatibleHttpVersion == null)
    //   throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) throws HttpParsingException {
    if (body == null)
      throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
    else if (body.length() > MAX_BODY_LENGTH)
      throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
  }

  public String getHeader(String name) {
    return headers.get(name);
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void addHeaders(String headerName, String headerValue) throws HttpParsingException {
    if (headerName == null || headerName.isEmpty() || headerName.length() > MAX_HEADER_NAME_LENGTH)
      throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
    if (headerValue == null || headerValue.length() > MAX_HEADER_VALUE_LENGTH)
      throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);

    headers.put(headerName, headerValue);
  }

  // public HttpVersion getBestCompatibleHttpVersion() {
  //   return bestCompatibleHttpVersion;
  // }
}
