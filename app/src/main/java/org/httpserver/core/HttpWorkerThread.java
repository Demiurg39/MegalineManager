package org.httpserver.core;

import java.net.Socket;

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

  @Override
  public void run() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'run'");
  }
}
