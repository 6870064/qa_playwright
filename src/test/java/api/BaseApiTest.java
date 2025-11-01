package api;

import io.restassured.response.Response;
import org.example.helpers.AuthContent;
import org.example.requests.user.UiUser;
import org.junit.jupiter.api.BeforeAll;
import requests.SimpleAction;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.http.ContentType.URLENC;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseApiTest implements SimpleAction {
  public static String authToken = "";
  public static final String BASE_API_URL = "https://practice.expandtesting.com/notes/api";

  @BeforeAll
  public static void authUser() {
    io.restassured.RestAssured.config = io.restassured.RestAssured.config()
        .encoderConfig(encoderConfig()
            .defaultContentCharset("UTF-8")
            .encodeContentTypeAs("application/x-www-form-urlencoded", URLENC));

    UiUser testUser = new UiUser("6870064@gmail.com", "meo5rnyk", "meo5rnyk");
    AuthContent authorizeContent = new AuthContent(authToken);

    Response response = SimpleAction.authUser(testUser);
    authToken = response.getBody().jsonPath().getString("data.token");
  }

}
