package org.example.constants;

import org.example.utils.PropertyReader;

public class Constants {

  public static final String INVALID_USERNAME = "wrongUser";
  public static final String AUTOMATION_PRACTICE_TEXT = "//h1[contains(normalize-space(.),'Automation Testing Practice WebSite')]";
  public static final String LINK_LOCATOR = "//a[normalize-space(text())='%s']";
  public static final String PAGE_TITLE_LOCATOR = "//h1[contains(normalize-space(.), '%s')]";
  public static final String INPUT_LOCATOR = "//input[@id='%s']";
  public static final String LOGIN_BUTTON = "//button[@id='submit-login']";
  public static final String BUTTON_LOCATOR = "//button[@id='%s']";
  public static final String REGISTRATION_BUTTON = "//button[@type='submit']";
  public static final String GREETING_MESSAGE = "//h3[@id='username' and normalize-space()='Hi, %s!']";
  public static final String USERNAME_LOCATOR = "#username";
  public static final String LOGOUT_BUTTON = "//a[@class='button secondary radius btn btn-danger']";
  public static final String ERROR_MESSAGE_LOCATOR = "//*[@id='flash']//b[normalize-space(text())='%s']";
  public static final String OTP_MESSAGE_LOCATOR = "//p[@id='otp-message' and contains(normalize-space(.), \"We've sent an OTP code to your email: %s\")]";
  public static final String OTP_ERROR_MESSAGE_LOCATOR = "//p[@id='otp-message' and contains(text(), 'The provided OTP code is incorrect. Please check your code and try again.')]";
  public static final String PAGE_COMMON_TITLE = " page for Automation Testing Practice";
  //URLs
  public static final String SECURE_URL = "/secure";
  public static final String LOGIN_URL = "/users/login";
  public static final String REGISTER_URL = "/notes/api/users/register";
  public static final String CREATE_A_NEW_NOTE = "/notes/api/notes";
  public static final String GET_NOTES = "/notes/api/notes";
  public static final String GET_NOTE_BY_ID = "/notes/api/notes/%s";
  public static final String DELETE_NOTE = "/notes/api/notes/%s";
  public static final String PATCH_NOTE = "/notes/api/notes/%s";
  public static final String PUT_NOTE = "/notes/api/notes/%s";
  public static final String HEALTH_CHECK_URL = "/notes/api/health-check";
  //JSON SCHEMA'S
  public static final String CREATE_USER_SCHEMA = "json_schemas/create_user.json";
  public static final String CREATE_NOTE_SCHEMA = "json_schemas/create_note.json";
  protected static PropertyReader propertyReader = new PropertyReader("src/main/resources/configuration.properties");
  public static final String BASE_URL = System.getProperty("baseUrl", propertyReader.getPropertyValueByKey("baseUrl"));
  public static final String USERNAME = System.getProperty("username", propertyReader.getPropertyValueByKey("username"));
  public static final String PASSWORD = System.getProperty("password", propertyReader.getPropertyValueByKey("password"));
  public static final String USER_EMAIL = System.getProperty("email", propertyReader.getPropertyValueByKey("email"));
  public static final String OTP_CODE = System.getProperty("otp_code", propertyReader.getPropertyValueByKey("otp_code"));

  //Response messages
  public static final String NOTES_API_RUNNING = "Notes API is Running";
  public static final String NOTE_CREATED = "Note successfully created";
  public static final String NOTE_NOT_FOUND = "No note was found with the provided ID, Maybe it was deleted";
  public static final String NOTES_RETRIEVED = "Notes successfully retrieved";
  public static final String NOTE_RETRIEVED = "Note successfully retrieved";

}