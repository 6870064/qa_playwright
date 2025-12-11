package requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.requests.user.LoginApiUser;
import utils.AllureUtils;

import java.util.Map;

import static api.BaseApiTest.BASE_API_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.URLENC;
import static org.example.constants.Constants.BASE_URL;

public class RequestLibrary {

  @Step("Sending POST request on {url}")
  public static Response sendPostRequest(LoginApiUser loginApiUser, String url) {

    Response response = RestAssured.given()
        .baseUri(BASE_API_URL)
        .contentType(URLENC)
        .formParams(toForm(loginApiUser))
        .log()
        .all()
        .when()
        .post(url)
        .then()
        .log().body()
        .extract()
        .response();

    AllureUtils.attachResponseToAllure(response);
    return response;
  }

  @Step("Sending POST request on {url}")
  public static Response sendPostRequest(Record record, String authContent, String url) {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = null;

    try {
      jsonBody = objectMapper.writeValueAsString(record);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    Response response = RestAssured.given()
        .body(jsonBody)
        .header("accept", "application/json")
        .contentType("application/json")
        .header("x-auth-token", authContent)
        .log()
        .all()
        .when()
        .post(BASE_URL + url)
        .then()
        .log().body()
        .extract()
        .response();

    AllureUtils.attachResponseToAllure(response);
    return response;
  }

  @Step("Sending GET request on {url}")
  public static Response sendGetRequest(String url, String token) {
    Response response = given()
        .header("accept", "application/json")
        .header("x-auth-token", token)
        .when()
        .get(BASE_URL + url)
        .then()
        .log().all()
        .extract().response();

    AllureUtils.attachResponseToAllure(response);
    return response;
  }

  @Step("Sending GET request on {url}")
  public static Response sendGetRequest(String url) {
    Response response = given()
        .header("accept", "application/json")
        .when()
        .get(BASE_URL + url)
        .then()
        .log().all()
        .extract().response();

    AllureUtils.attachResponseToAllure(response);
    return response;
  }

  @Step("Sending DELETE request on {url}")
  public static Response sendDeleteRequest(String url, String token) {
    Response response = given()
        .header("accept", "application/json")
        .header("x-auth-token", token)
        .when()
        .delete(BASE_URL + url)
        .then()
        .log().all()
        .extract().response();

    AllureUtils.attachResponseToAllure(response);
    return response;
  }

  public static Map<String, String> toForm(LoginApiUser user) {
    return Map.of(
        "email", user.email(),
        "password", user.password()
    );
  }

  @Step("Sending PATCH request on {url}")
  public static Response sendPatchRequest(Record record, String authContent, String url) {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = null;

    try {
      jsonBody = objectMapper.writeValueAsString(record);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    Response response = RestAssured.given()
        .body(jsonBody)
        .header("accept", "application/json")
        .contentType("application/json")
        .header("x-auth-token", authContent)
        .log()
        .all()
        .when()
        .patch(BASE_URL + url)
        .then()
        .log().body()
        .extract()
        .response();

    AllureUtils.attachResponseToAllure(response);
    return response;
  }

  @Step("Sending PUT request on {url}")
  public static Response sendPutRequest(Record record, String authContent, String url) {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = null;

    try {
      jsonBody = objectMapper.writeValueAsString(record);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    Response response = RestAssured.given()
        .body(jsonBody)
        .header("accept", "application/json")
        .contentType("application/json")
        .header("x-auth-token", authContent)
        .log()
        .all()
        .when()
        .put(BASE_URL + url)
        .then()
        .log().body()
        .extract()
        .response();

    AllureUtils.attachResponseToAllure(response);
    return response;
  }
}
