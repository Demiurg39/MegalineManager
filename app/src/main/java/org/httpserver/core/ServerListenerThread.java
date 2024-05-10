package org.httpserver.core;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerThread implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
  private int port;
  private ServerSocket serverSocket;
  Thread thread;

  public ServerListenerThread(int port) throws IOException {
    this.port = port;
    this.serverSocket = new ServerSocket(this.port);
    this.thread = new Thread(this, "ServerListenerThread");
  }

  public void startThread() {
    thread.start();
  }

  @Override
  public void run() {
    try {
      while (!serverSocket.isClosed() && serverSocket.isBound()) {
        Socket socket = serverSocket.accept();

        LOGGER.info("Accepted connection : " + socket.getInetAddress());

        // HttpWorkerThread workerThread = new HttpWorkerThread(socket);
        // workerThread.startThread();
      }
    } catch (IOException e) {
      LOGGER.error("Problem with Server Socket.", e);
    } finally {
      if (serverSocket != null)
        try {
          serverSocket.close();
        } catch (IOException e) {
        }
    }
  }
}
