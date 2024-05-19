package org.megaline.httpserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.megaline.httpserver.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

public class HttpParser {

  private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

  private static final int SP = 0x20;
  private static final int CR = 0x0D;
  private static final int LF = 0x0A;
  private static final int COLON = 0x3A;

  public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
    InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
    HttpRequest request = new HttpRequest();

    try {
      parseRequestLine(inputStream, request);
      parseHeaders(inputStream, request);
      parseBody(inputStream, request);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return request;
  }

  private void parseRequestLine(InputStream inputStream, HttpRequest request)
      throws IOException, HttpParsingException {

    StringBuilder buffer = new StringBuilder();
    boolean methodParsed = false;
    boolean requestTargetParsed = false;

    int byte_;
    while ((byte_ = inputStream.read()) >= 0) {
      if (byte_ == CR) {
        byte_ = inputStream.read();
        if (byte_ == LF) {
          LOGGER.info("Request Line VERSION to Process : {}", buffer.toString());
          if (!methodParsed || !requestTargetParsed)
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);

          try {
            request.setHttpVersion(buffer.toString());
          } catch (HttpParsingException e) {
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
          }

          return;
        } else {
          throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
      }

      if (byte_ == SP) {
        if (!methodParsed) {
          LOGGER.info("Request Line METHOD to Process : {}", buffer.toString());
          request.setMethod(buffer.toString());
          methodParsed = true;

        } else if (!requestTargetParsed) {
          LOGGER.info("Request Line REQ TARGET to Process : {}", buffer.toString());
          request.setRequestTarget(buffer.toString());
          requestTargetParsed = true;
        } else {
          throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

        buffer.delete(0, buffer.length());

      } else {
        buffer.append((char) byte_);

        if (!methodParsed)
          if (buffer.length() > HttpMethod.MAX_LENGTH)
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
      }
    }

  }

  private void parseHeaders(InputStream inputStream, HttpRequest request) throws IOException {
    StringBuilder buffer = new StringBuilder();
    boolean isValue = false;
    String fieldName = null;
    StringBuilder valueBuffer = new StringBuilder();

    int byte_;
    while ((byte_ = inputStream.read()) >= 0) {
      if (byte_ == CR) {
        byte_ = inputStream.read(); // should be LF
        if (byte_ == LF) {
          if (buffer.length() == 0 && valueBuffer.length() == 0) {
            // End of headers
            return;
          }

          if (fieldName != null) {
            LOGGER.info("Adding header: {}={}", fieldName, valueBuffer.toString().trim());
            request.addHeader(fieldName, valueBuffer.toString().trim());
          }

          // Reset for the next header
          fieldName = null;
          buffer.setLength(0);
          valueBuffer.setLength(0);
          isValue = false;
          continue;
        }
      }

      if (byte_ == COLON && !isValue) {
        fieldName = buffer.toString().trim();
        buffer.setLength(0);
        isValue = true;
      } else if (isValue && (byte_ == SP || byte_ == '\t') && valueBuffer.length() == 0) {
        continue;
      } else if (isValue) {
        valueBuffer.append((char) byte_);
      } else {
        buffer.append((char) byte_);
      }
    }

    if (fieldName != null && valueBuffer.length() > 0) {
      request.addHeader(fieldName, valueBuffer.toString().trim());
    }
  }

  private void parseBody(InputStream inputStream, HttpRequest request) throws IOException {
    StringBuilder body = new StringBuilder();
    int byte_;
    while ((byte_ = inputStream.read()) >= 0) {
      body.append((char) byte_);
    }
    LOGGER.info("Request BODY : {}", body.toString());
    request.setBody(body.toString());

    // Try to parse the body as JSON
    try {
      JsonNode jsonNode = Json.parse(body.toString());
      request.setJsonBody(jsonNode);
    } catch (IOException e) {
      LOGGER.warn("Failed to parse body as JSON: {}", e.getMessage());
    }
  }

}
