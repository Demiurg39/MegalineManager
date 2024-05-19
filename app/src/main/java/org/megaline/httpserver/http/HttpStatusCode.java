package org.megaline.httpserver.http;

public enum HttpStatusCode {

  /* --- CLIENT_ERROR --- */
  CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
  CLIENT_ERROR_401_NOT_ALLOWED(401, "Not Allowed"),
  CLIENT_ERROR_414_BAD_REQUEST(414, "URI Too Long"),

  /* --- SERVER_ERROR --- */
  SERVER_ERROR_500_INTERNAL_ERROR(500, "Internal Error"),
  SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Iplemented"),
  SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "Http Version Not Supported"),

  /* --- SERVER_CODES --- */
  SERVER_CONNECTED_200(200, HttpVersion.HTTP_1_1 + " 200 OK" ),

  ;

  public final int STATUS_CODE;
  public final String MESSAGE;

  private HttpStatusCode(int STATUS_CODE, String MESSAGE) {
    this.STATUS_CODE = STATUS_CODE;
    this.MESSAGE = MESSAGE;
  }
}
