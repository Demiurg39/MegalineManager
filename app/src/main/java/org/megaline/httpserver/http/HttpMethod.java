package org.megaline.httpserver.http;

public enum HttpMethod {
  GET, HEAD, POST, DELETE, PUT;

  public static final int MAX_LENGTH;

  static {
    int tempLength = -1;
    for (HttpMethod method : values())
      if (method.name().length() > tempLength)
        tempLength = method.name().length();

    MAX_LENGTH = tempLength;
  }
}
