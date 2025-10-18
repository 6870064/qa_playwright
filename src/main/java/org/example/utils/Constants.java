package org.example.utils;

public class Constants {

  protected static PropertyReader propertyReader = new PropertyReader("src/main/resources/configuration.properties");
  public static final String BASE_URL = System.getProperty("baseUrl", propertyReader.getPropertyValueByKey("baseUrl"));
  public static final String USERNAME = System.getProperty("username", propertyReader.getPropertyValueByKey("username"));
  public static final String PASSWORD = System.getProperty("password", propertyReader.getPropertyValueByKey("password"));
  public static final String AUTOMATION_PRACTICE_TEXT = "//h1[contains(normalize-space(.),'Automation Testing Practice WebSite')]";
  public static final String LINK_LOCATOR = "//a[normalize-space(text())='%s']";
  public static final String TEXT_LOCATOR = "//h1[contains(normalize-space(.), '%s')]";
  public static final String INPUT_LOCATOR = "//input[@id='%s']";
  public static final String LOGIN_BUTTON = "//button[@id='submit-login']";
  public static final String REGISTRATION_BUTTON = "//button[@type='submit']";
  public static final String GREETING_MESSAGE = "//h3[@id='username' and normalize-space()='Hi, %s!']";
  public static final String USERNAME_LOCATOR = "#username";
  public static final String LOGOUT_BUTTON = "//a[@class='button secondary radius btn btn-danger']";

  //URLs
  public static final String SECURE = "secure";
  public static final String LOGIN = "login";
}