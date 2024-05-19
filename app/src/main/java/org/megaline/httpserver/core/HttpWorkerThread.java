package org.megaline.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.megaline.httpserver.http.HttpParser;
import org.megaline.httpserver.http.HttpParsingException;
import org.megaline.httpserver.http.HttpRequest;
import org.megaline.httpserver.http.HttpResponse;
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
    HttpResponse response = null;

    try {
      inputStream = socket.getInputStream();
      outputStream = socket.getOutputStream();
      boolean flag = false;

      request = httpParser.parseHttpRequest(inputStream);

      LOGGER.info("Request METHOD is : {}", request.getMethod());
      LOGGER.info("Request TARGET is : {}", request.getRequestTarget());
      LOGGER.info("Request VERSION is : {}", request.getHttpVersion());
      LOGGER.info("Request BODY is : {}", request.getBody());

      if (request.getRequestTarget().contains("user")) {
        LOGGER.info("Starting User processing thread.");

      } else if (request.getRequestTarget().contains("/employe")) {
        LOGGER.info("Starting Employe processing thread.");

      } else if (request.getRequestTarget().contains("/connection")) {
        LOGGER.info("Starting Connection processing thread.");

      } else if (request.getRequestTarget().contains("/tariffplan")) {
        LOGGER.info("Starting Tariff processing thread.");

      }

    } catch (

    IOException e) {
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
  }
}
