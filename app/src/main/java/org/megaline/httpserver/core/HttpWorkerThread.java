package org.megaline.httpserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.megaline.httpserver.http.HttpParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWorkerThread implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpWorkerThread.class);
  private Socket socket;
  private Thread thread;

  public HttpWorkerThread(Socket socket) {
    this.socket = socket;
    this.thread = new Thread(this, "HttpWorkerThread");
  }

  public void startThread() {
    this.thread.start();
  }

  @Override
  public void run() {
    InputStream inputStream = null;
    OutputStream outputStream = null;

    try {
      inputStream = socket.getInputStream();
      outputStream = socket.getOutputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

      while (true) {
        String line = bufferedReader.readLine();
        LOGGER.info("Header : " + line);

        if (line.equals(""))
          break;

        if (line.contains("GET")) {
          int startIndex = line.indexOf("/");
          int endIndex = line.indexOf(" HTTP");
          String[] URI = line.substring(startIndex+1, endIndex).split("/");

        }
      }

      // Yellow TODO parse http request

    } catch (IOException e) {
      LOGGER.error("Problem with comunication", e);
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
