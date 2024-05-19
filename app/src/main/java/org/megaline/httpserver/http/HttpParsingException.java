package org.megaline.httpserver.http;

public class HttpParsingException extends Exception {

  private HttpStatusCode error_code;

  public HttpParsingException(HttpStatusCode error_code) {
    super(error_code.MESSAGE);
    this.error_code = error_code;
  }

  public HttpStatusCode getError_code() {
    return error_code;
  }

}
