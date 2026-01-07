package api;

import io.restassured.response.Response;
import org.example.enums.HttpStatus;
import org.example.helpers.DataGenerator;
import org.example.requests.user.ApiUser;
import org.example.requests.user.LoginApiUser;
import org.example.responses.user_response.UserResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import requests.SimpleAction;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.http.ContentType.URLENC;
import static org.example.constants.Constants.API_USER;
import static org.example.constants.Constants.API_USER_PW;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test_utils.TestUtils.assertResponseCode;

public abstract class BaseApiTest implements SimpleAction {
  public static final String BASE_API_URL = "https://practice.expandtesting.com/notes/api";
  private static String authToken;

  @BeforeAll
  public static void authUser() {
    DataGenerator dataGenerator = new DataGenerator();
    io.restassured.RestAssured.config = io.restassured.RestAssured.config()
        .encoderConfig(encoderConfig()
            .defaultContentCharset("UTF-8")
            .encodeContentTypeAs("application/x-www-form-urlencoded", URLENC));

    ApiUser apiUser = new ApiUser(
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

    LoginApiUser loginApiUser = new LoginApiUser(apiUser.email(), apiUser.password());

    Response response = SimpleAction.userLogin(loginApiUser);
    authToken = response.getBody().jsonPath().getString("data.token");
  }

  @AfterAll
  static void cleanUp(){
    Response deleteUser = SimpleAction.deleteUser(authToken);
    assertResponseCode(HttpStatus.OK.code(), deleteUser);
  }

  protected static String token() {
    return authToken;
  }
}