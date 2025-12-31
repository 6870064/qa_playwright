package api;

import io.restassured.response.Response;
import org.example.helpers.AuthContent;
import org.example.requests.user.LoginApiUser;
import org.junit.jupiter.api.BeforeAll;
import requests.SimpleAction;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.http.ContentType.URLENC;
import static org.example.constants.Constants.*;

public class BaseApiTest implements SimpleAction {
  public static final String BASE_API_URL = "https://practice.expandtesting.com/notes/api";
  public static String authToken = "";

  @BeforeAll
  public static void authUser() {
    io.restassured.RestAssured.config = io.restassured.RestAssured.config()
        .encoderConfig(encoderConfig()
            .defaultContentCharset("UTF-8")
            .encodeContentTypeAs("application/x-www-form-urlencoded", URLENC));

    LoginApiUser loginApiUser = new LoginApiUser(API_USER, API_USER_PW);
    AuthContent authorizeContent = new AuthContent(authToken);

    Response response = SimpleAction.userLogin(loginApiUser);
    authToken = response.getBody().jsonPath().getString("data.token");
  }

}
