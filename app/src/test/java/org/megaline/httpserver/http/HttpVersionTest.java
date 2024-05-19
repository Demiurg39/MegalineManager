package org.megaline.httpserver.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class HttpVersionTest {

  @Test
  void getBestCompatibleVersionExactMatch() {
    HttpVersion version = null;
    try {
      version = HttpVersion.getBestCompatibleVerison("HTTP/1.1");
    } catch (HttpParsingException e) {
      fail();
    }

    assertNotNull(version);
    assertEquals(HttpVersion.HTTP_1_1, version);
  }

  @Test
  void getBestCompatibleVersionBadFormat() {
    HttpVersion version = null;
    try {
      version = HttpVersion.getBestCompatibleVerison("http/1.1");
      fail();
    } catch (HttpParsingException e) {

    }
  }

  @Test
  void getBestCompatibleVersionHigherVersion() {
    HttpVersion version = null;
    try {
      version = HttpVersion.getBestCompatibleVerison("HTTP/1.2");
    } catch (HttpParsingException e) {
      fail();
    }

    assertNotNull(version);
    assertEquals(HttpVersion.HTTP_1_1, version);
  }

}
