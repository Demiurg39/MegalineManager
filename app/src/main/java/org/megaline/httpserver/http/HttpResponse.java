package org.megaline.httpserver.http;

public class HttpResponse extends HttpMessage {

  private HttpVersion httpVersion;
  private HttpStatusCode statusCode;
  private int contentLength;
  private String message;

  public HttpResponse(String message) {
    httpVersion = HttpVersion.HTTP_1_1;
    statusCode = HttpStatusCode.SERVER_CONNECTED_200;
    contentLength = message.length();
    this.message = message;
  }

  public HttpVersion getHttpVersion() {
    return httpVersion;
  }

  public HttpStatusCode getStatusCode() {
    return statusCode;
  }

  public int getContentLength() {
    return contentLength;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
    this.contentLength = message.length();
  }
}
