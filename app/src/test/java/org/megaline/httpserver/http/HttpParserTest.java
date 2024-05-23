package org.megaline.httpserver.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

  private HttpParser httpParser;

  @BeforeAll
  public void beforeClass() {
    httpParser = new HttpParser();
  }

  /* ---Yellow TEST-CASES --- */

  @Test
  void parseHttpRequestGet1() {
    HttpRequest request = null;
    try {
      request = httpParser.parseHttpRequest(generateValidGETTestCase());
    } catch (HttpParsingException e) {
      fail(e);
    }

    assertNotNull(request);

    assertEquals(HttpMethod.GET, request.getMethod());
    assertEquals("/", request.getRequestTarget());
    assertEquals(HttpVersion.HTTP_1_1, request.getHttpVersion());
    assertEquals("HTTP/1.1", request.getOriginalHttpVersion());

    // Проверка заголовков
    assertEquals("localhost:8080", request.getHeader("Host"));
    assertEquals("Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0",
        request.getHeader("User-Agent"));
    assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
        request.getHeader("Accept"));
    assertEquals("en-US,en;q=0.5", request.getHeader("Accept-Language"));
    assertEquals("gzip, deflate, br", request.getHeader("Accept-Encoding"));
    assertEquals("keep-alive", request.getHeader("Connection"));
    assertEquals("1", request.getHeader("Upgrade-Insecure-Requests"));
    assertEquals("document", request.getHeader("Sec-Fetch-Dest"));
    assertEquals("navigate", request.getHeader("Sec-Fetch-Mode"));
    assertEquals("none", request.getHeader("Sec-Fetch-Site"));
    assertEquals("?1", request.getHeader("Sec-Fetch-User"));
  }

  @Test
  void parseHttpRequestPost() {
    HttpRequest request = null;
    try {
      request = httpParser.parseHttpRequest(generateValidPOSTTestCase());
    } catch (HttpParsingException e) {
      fail(e);
    }

    assertNotNull(request);

    assertEquals(HttpMethod.POST, request.getMethod());
    assertEquals("/", request.getRequestTarget());
    assertEquals(HttpVersion.HTTP_1_1, request.getHttpVersion());
    assertEquals("HTTP/1.1", request.getOriginalHttpVersion());

    // Проверка заголовков
    assertEquals("application/json", request.getHeader("Content-Type"));
    assertEquals("PostmanRuntime/7.38.0", request.getHeader("User-Agent"));
    assertEquals("*/*", request.getHeader("Accept"));
    assertEquals("5854a73e-5fe4-4c05-9a3a-ed244ad44c9a", request.getHeader("Postman-Token"));
    assertEquals("localhost:8080", request.getHeader("Host"));
    assertEquals("gzip, deflate, br", request.getHeader("Accept-Encoding"));
    assertEquals("keep-alive", request.getHeader("Connection"));
    assertEquals("26", request.getHeader("Content-Length"));

    // Проверка тела запроса
    String expectedBody = "{\n    \"userId\" : \"hello\"\n}";
    assertEquals(expectedBody, request.getBody());
    assertTrue(request.getBody().contains("\"userId\""));
  }

  void parseHttpRequestHead1() {
    HttpRequest request = null;
    try {
      request = httpParser.parseHttpRequest(generateValidHeadTestCase());
    } catch (HttpParsingException e) {
      fail(e);
    }

    assertEquals(HttpMethod.GET, request.getMethod());
  }

  @Test
  void parseHttpSupportedHttpVersion() {
    HttpRequest request = null;
    try {
      request = httpParser.parseHttpRequest(generateSupportedHttpVersionTestCase());
    } catch (HttpParsingException e) {
      fail(e);
    }

    assertNotNull(request);

    assertEquals(HttpVersion.HTTP_1_1, request.getHttpVersion());
    assertEquals("HTTP/1.2", request.getOriginalHttpVersion());
  }

  @Test
  void parseHttpRequestBadMethod1() {
    try {
      HttpRequest request = httpParser.parseHttpRequest(generateBadMethodTestCase1());
      fail();
    } catch (HttpParsingException e) {
      assertEquals(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED, e.getError_code());
    }
  }

  @Test
  void parseHttpRequestBadMethod2() {
    try {
      HttpRequest request = httpParser.parseHttpRequest(generateBadMethodTestCase2());
      fail();
    } catch (HttpParsingException e) {
      assertEquals(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED, e.getError_code());
    }
  }

  @Test
  void parseHttpRequestBadMethod3() {
    try {
      HttpRequest request = httpParser.parseHttpRequest(generateBadMethodTestCase3());
      fail();
    } catch (HttpParsingException e) {
      assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, e.getError_code());
    }
  }

  @Test
  void parseHttpRequestBadMethod4() {
    try {
      HttpRequest request = httpParser.parseHttpRequest(generateBadMethodTestCase4());
      fail();
    } catch (HttpParsingException e) {
      assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, e.getError_code());
    }
  }

  @Test
  void parseHttpRequestInvalidNumItems1() {
    try {
      HttpRequest request = httpParser.parseHttpRequest(generateInvalidReqLineTestCase1());
      fail();
    } catch (HttpParsingException e) {
      e.printStackTrace();
    }
  }

  @Test
  void parseHttpRequestEmptyRequestLine() {
    try {
      HttpRequest request = httpParser.parseHttpRequest(generateInvalidReqLineTestCase2());
      fail();
    } catch (HttpParsingException e) {
      assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, e.getError_code());
    }
  }

  @Test
  void parseHttpRequestCRnoLF() {
    try {
      HttpRequest request = httpParser.parseHttpRequest(generateInvalidReqLineTestCase3());
      fail();
    } catch (HttpParsingException e) {
      assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, e.getError_code());
    }
  }

  /* ---Green VALID-TEST-CASES --- */

  private InputStream generateValidGETTestCase() {
    String rawData = "GET / HTTP/1.1\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "Accept-Encoding: gzip, deflate, br\r\n" +
        "Connection: keep-alive\r\n" +
        "Upgrade-Insecure-Requests: 1\r\n" +
        "Sec-Fetch-Dest: document\r\n" +
        "Sec-Fetch-Mode: navigate\r\n" +
        "Sec-Fetch-Site: none\r\n" +
        "Sec-Fetch-User: ?1\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  private InputStream generateValidPOSTTestCase() {
    String rawData = "POST / HTTP/1.1\r\n" +
        "Content-Type: application/json\r\n" +
        "User-Agent: PostmanRuntime/7.38.0\r\n" +
        "Accept: */*\r\n" +
        "Postman-Token: 5854a73e-5fe4-4c05-9a3a-ed244ad44c9a\r\n" +
        "Host: localhost:8080\r\n" +
        "Accept-Encoding: gzip, deflate, br\r\n" +
        "Connection: keep-alive\r\n" +
        "Content-Length: 26\r\n" +
        "\r\n" +
        "{\n    \"userId\" : \"hello\"\n}";

    return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
  }

  private InputStream generateValidHeadTestCase() {
    String rawData = "HEAD / HTTP/1.1\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "Accept-Encoding: gzip, deflate, br\r\n" +
        "Connection: keep-alive\r\n" +
        "Upgrade-Insecure-Requests: 1\r\n" +
        "Sec-Fetch-Dest: document\r\n" +
        "Sec-Fetch-Mode: navigate\r\n" +
        "Sec-Fetch-Site: none\r\n" +
        "Sec-Fetch-User: ?1\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  private InputStream generateSupportedHttpVersionTestCase() {
    String rawData = "GET / HTTP/1.2\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "Accept-Encoding: gzip, deflate, br\r\n" +
        "Connection: keep-alive\r\n" +
        "Upgrade-Insecure-Requests: 1\r\n" +
        "Sec-Fetch-Dest: document\r\n" +
        "Sec-Fetch-Mode: navigate\r\n" +
        "Sec-Fetch-Site: none\r\n" +
        "Sec-Fetch-User: ?1\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  /* ---Red INVALID-BAD-TEST-CASES --- */

  private InputStream generateBadMethodTestCase1() {
    String rawData = "GeT / HTTP/1.1\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  private InputStream generateBadMethodTestCase2() {
    String rawData = "HEADD / HTTP/1.1\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  private InputStream generateBadMethodTestCase3() {
    String rawData = "GET / HTP/1.1\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  private InputStream generateBadMethodTestCase4() {
    String rawData = "GET / HTTP/2.1\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  private InputStream generateInvalidReqLineTestCase1() {
    String rawData = "HEAD / AAAA HTTP/1.1\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  private InputStream generateInvalidReqLineTestCase2() {
    String rawData = "\r\n" +
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

  private InputStream generateInvalidReqLineTestCase3() {
    String rawData = "HEAD / HTTP/1.1\r" + // <-- No LF "\n"
        "Host: localhost:8080\r\n" +
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:125.0) Gecko/20100101 Firefox/125.0\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
        "Accept-Language: en-US,en;q=0.5\r\n" +
        "\r\n";

    InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    return inputStream;
  }

}
