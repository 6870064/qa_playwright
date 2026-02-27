package org.example.utils;

import io.qameta.allure.Step;

/**
 * Global configuration utility for the application.
 * Manages environment-level variables such as the base URL,
 * prioritizing system properties over property files.
 */
public class AppConfig {
  protected static PropertyReader propertyReader = new PropertyReader("src/main/resources/configuration.properties");

  private static final String BASE_URL = System
      .getProperty("baseUrl",
          propertyReader.getPropertyValueByKey("baseUrl"));

  /**
   * Default constructor for AppConfig.
   */
  public AppConfig() {
  }

  /**
   * Retrieves the configured Base URL for the application.
   * * @return the base URL as a String
   */
  @Step("Get application base URL")
  public static String baseUrl() {
    return BASE_URL;
  }
}