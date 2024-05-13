package org.megaline.httpserver;

import java.io.IOException;

import org.megaline.httpserver.config.Configuration;
import org.megaline.httpserver.config.ConfigurationManager;
import org.megaline.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer {

  private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

  public static void main(String[] args) {
    LOGGER.info("Server starting...");

    ConfigurationManager.getInstance().loadConfig("src/main/resources/http.json");
    Configuration config = ConfigurationManager.getInstance().getConfig();

    LOGGER.info("Using port : " + config.getPort());

    LOGGER.info("Starting Server Listener Thread");

    try {
      ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort());
      serverListenerThread.startThread();
    } catch (IOException e) {
      LOGGER.error("Something Went Wrong with ServerListenerThread.");
      e.printStackTrace();
    }
    
  }

}
