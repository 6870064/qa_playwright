package org.example.constants;

import org.example.utils.PropertyReader;

public class Constants {

  //Properties
  protected static PropertyReader propertyReader = new PropertyReader("src/main/resources/configuration.properties");
  public static final String BASE_URL = System.getProperty("baseUrl", propertyReader.getPropertyValueByKey("baseUrl"));
  public static final String USERNAME = System.getProperty("username", propertyReader.getPropertyValueByKey("username"));
  public static final String API_USER = System.getProperty("api_user", propertyReader.getPropertyValueByKey("api_user"));
  public static final String API_USER_PW = System.getProperty("api_user_pw", propertyReader.getPropertyValueByKey("api_user_pw"));
  public static final String PASSWORD = System.getProperty("password", propertyReader.getPropertyValueByKey("password"));
  public static final String USER_EMAIL = System.getProperty("email", propertyReader.getPropertyValueByKey("email"));
  public static final String OTP_CODE = System.getProperty("otp_code", propertyReader.getPropertyValueByKey("otp_code"));

  //Locators
  public static final String AUTOMATION_PRACTICE_TEXT = "//h1[contains(normalize-space(.),'Automation Testing Practice WebSite')]";
  public static final String LINK_LOCATOR = "//a[normalize-space(text())='%s']";
  public static final String PAGE_TITLE_LOCATOR = "//h1[contains(normalize-space(.), '%s')]";
  public static final String INPUT_LOCATOR = "//input[@id='%s']";
  public static final String BUTTON_LOCATOR = "//button[@id='%s']";
  public static final String OTP_MESSAGE_LOCATOR = "//p[@id='otp-message' and contains(normalize-space(.), \"We've sent an OTP code to your email: %s\")]";
  public static final String OTP_ERROR_MESSAGE_LOCATOR = "//p[@id='otp-message' and contains(text(), 'The provided OTP code is incorrect. Please check your code and try again.')]";

  //URLs
  public static final String SECURE_URL = "/secure";
  public static final String LOGIN_URL = "/users/login";
  public static final String GET_PROFILE_URL = "/users/profile";
  public static final String REGISTER_URL = "/notes/api/users/register";
  public static final String CREATE_A_NEW_NOTE = "/notes/api/notes";
  public static final String GET_NOTES = "/notes/api/notes";
  public static final String GET_NOTE_BY_ID = "/notes/api/notes/%s";
  public static final String DELETE_NOTE = "/notes/api/notes/%s";
  public static final String PATCH_NOTE = "/notes/api/notes/%s";
  public static final String PUT_NOTE = "/notes/api/notes/%s";
  public static final String HEALTH_CHECK_URL = "/notes/api/health-check";

  //JSON SCHEMA'S
  public static final String USER_RESPONSE_SCHEMA = "json_schemas/user_response.json";
  public static final String BASE_RESPONSE_SCHEMA = "json_schemas/base_response.json";
  public static final String CREATE_NOTE_SCHEMA = "json_schemas/create_note.json";
}