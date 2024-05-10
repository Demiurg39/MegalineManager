package org.httpserver.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.httpserver.HttpConfigurationException;
import org.httpserver.util.Json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class ConfigurationManager {

  private static ConfigurationManager configManager = getInstance();
  private static Configuration config;

  public static ConfigurationManager getInstance() {
    if (configManager == null)
      configManager = new ConfigurationManager();

    return configManager;
  }

  /**
   * Used to load configuration from file provided by path
   * 
   * @param filePath
   */
  public void loadConfig(String filePath) {
    FileReader fileReader = null;

    try {
      fileReader = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      throw new HttpConfigurationException(e);
    }

    StringBuffer stringBuffer = new StringBuffer();

    int i;
    try {
      while ((i = fileReader.read()) != -1) {
        stringBuffer.append((char) i);
      }
    } catch (IOException e) {
      try {
        fileReader.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      throw new HttpConfigurationException(e);
    }

    JsonNode conf;

    try {
      conf = Json.parse(stringBuffer.toString());
    } catch (IOException e) {
      try {
        fileReader.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      throw new HttpConfigurationException("Fail To Parse Json File.", e);
    }

    try {
      config = Json.fromJson(conf, Configuration.class);
    } catch (JsonProcessingException e) {
      try {
        fileReader.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      throw new HttpConfigurationException("Fail To Parse Json File internal.", e);
    }

    try {
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Used to get current configuration
   * 
   * @return
   */
  public Configuration getConfig() {
    if (config == null)
      throw new HttpConfigurationException("No Current Configuration User.");

    return config;
  }
}
