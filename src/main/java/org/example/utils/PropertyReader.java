package org.example.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

  private final Properties properties = new Properties();
  String propertyFile;

  public PropertyReader(String filePath) {
    try {
      FileInputStream fileInputStream = new FileInputStream(filePath);
      properties.load(fileInputStream);
      propertyFile = filePath;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
