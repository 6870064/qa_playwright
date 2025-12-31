package org.example.utils;

public class AppConfig {
  protected static PropertyReader propertyReader = new PropertyReader("src/main/resources/configuration.properties");

  private static final String BASE_URL = System
      .getProperty("baseUrl",
          propertyReader.getPropertyValueByKey("baseUrl"));

  public static String baseUrl() {
    return BASE_URL;
  }

  public AppConfig() {
  }
}
