package org.example.utils;

import io.qameta.allure.Step;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading configuration properties from a specified file.
 * Provides a centralized way to access environment variables and settings.
 */
public class PropertyReader {

  private final Properties properties = new Properties();
  String propertyFile;

  /**
   * Initializes the PropertyReader by loading properties from the given file path.
   *
   * @param filePath the path to the .properties file
   */
  public PropertyReader(String filePath) {
    try {
      FileInputStream fileInputStream = new FileInputStream(filePath);
      properties.load(fileInputStream);
      propertyFile = filePath;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves the value associated with the specified key from the properties file.
   * Throws a RuntimeException if the key is not found.
   *
   * @param key the property key to look up
   * @return the property value as a String
   * @throws RuntimeException if the key does not exist in the property file
   */
  @Step("Read property value for key: {key}")
  public String getPropertyValueByKey(String key) {
    if(properties.getProperty(key) != null) {
      return properties.getProperty(key);
    } else {
      String errorMessage = String.format(
          "Cannot find property by key: %s. Make sure that it exists inside %s",
          key,
          propertyFile);
      throw new RuntimeException(errorMessage);
    }
  }
}