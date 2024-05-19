package org.megaline.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.megaline.httpserver.http.HttpParser;
import org.megaline.httpserver.http.HttpParsingException;
import org.megaline.httpserver.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWorkerThread implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpWorkerThread.class);
  private Socket socket;
  private Thread thread;
  private HttpParser httpParser;

  public HttpWorkerThread(Socket socket) {
    this.socket = socket;
    this.httpParser = new HttpParser();
    this.thread = new Thread(this, "HttpWorkerThread");
  }

  public void startThread() {
    this.thread.start();
  }

  @Override
  public void run() {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    HttpRequest request = null;

    try {
      inputStream = socket.getInputStream();
      outputStream = socket.getOutputStream();

      request = httpParser.parseHttpRequest(inputStream);

      LOGGER.info("Request METHOD is : {}", request.getMethod());
      LOGGER.info("Request TARGET is : {}", request.getRequestTarget());
      LOGGER.info("Request VERSION is : {}", request.getHttpVersion());
      // LOGGER.info("Header fiels : {}");

    } catch (IOException e) {
      LOGGER.error("Problem with comunication", e);
    } catch (HttpParsingException e) {
      e.printStackTrace();
    } finally {
      try {
        if (inputStream != null)
          inputStream.close();
      } catch (IOException e) {
      }

      try {
        if (outputStream != null)
          outputStream.close();
      } catch (IOException e) {
      }

      try {
        if (socket != null)
          socket.close();
      } catch (IOException e) {
      }
    }

    // Red TODO complete run method
    // throw new UnsupportedOperationException("Unimplemented method 'run'");
  }
}
