package api;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.enums.HttpStatus;
import org.example.helpers.DataGenerator;
import org.example.requests.user.ApiUser;
import org.example.requests.user.LoginApiUser;
import org.example.responses.api_user_response.RegistrationUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.SimpleAction;

import static org.example.constants.Constants.CREATE_USER_SCHEMA;
import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.assertResponseCode;
import static utils.TestUtils.assertResponseSchema;

public class RegistrationUserTests extends BaseApiTest {

  @DisplayName("[API. User]. POST method. Create a new user")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Assert a response.
      """)
  @Test
  public void registrationUserTest() {
    ApiUser apiUser = new ApiUser(
        new DataGenerator().generateRandomName(8, 30),
        new DataGenerator().generateRandomEmail(true),
        new DataGenerator().generateRandomPassword(8, 30));

    Response createUser = createUser(apiUser, authToken);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);
    assertResponseSchema(CREATE_USER_SCHEMA, createUser);

    RegistrationUserResponse body = createUser.as(RegistrationUserResponse.class);
    Assertions.assertAll(
        () -> assertTrue(body.success(),
            "Expected success=true, but was false"),

        () -> assertEquals(HttpStatus.CREATED.code(), body.status(),
            "Incorrect status in response body"),

        () -> assertEquals("User account created successfully", body.message(),
            "Unexpected creation message"),

        () -> assertNotNull(body.data().id(),
            "User ID must not be null"),

        () -> assertEquals(apiUser.name(), body.data().name(),
            "Returned name does not match the created one"),

        () -> assertEquals(apiUser.email().toLowerCase(), body.data().email(),
            "Returned email does not match the created one")
    );
  }

  @DisplayName("[API. User]. POST method. Login user")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Assert a response.
      """)
  @Test
  public void loginUserTest() {
    ApiUser apiUser = new ApiUser(
        new DataGenerator().generateRandomName(8, 30),
        new DataGenerator().generateRandomEmail(true),
        new DataGenerator().generateRandomPassword(8, 30));

    Response createUser = createUser(apiUser, authToken);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    RegistrationUserResponse body = createUser.as(RegistrationUserResponse.class);
    assertNotNull(body.data().id(),"User ID must not be null");

    String userId = body.data().id();

    LoginApiUser loginApiUser = new LoginApiUser(apiUser.email(), apiUser.password());

    Response loginUser = SimpleAction.userLogin(loginApiUser);
    assertResponseCode(HttpStatus.OK.code(), loginUser);

    RegistrationUserResponse secondBody = loginUser.as(RegistrationUserResponse.class);
    Assertions.assertAll(
        () -> assertTrue(secondBody.success(),
            "Expected success=true, but was false"),

    () -> assertEquals(HttpStatus.OK.code(), secondBody.status(),
        "Incorrect status in response body"),

        () -> assertEquals("Login successful", secondBody.message(),
            "Unexpected creation message"),

        () -> assertEquals(userId, secondBody.data().id(),
            "User ID is not equal"),

        () -> assertEquals(apiUser.name(), secondBody.data().name(),
            "Returned name does not match the created one"),

    () -> assertEquals(apiUser.email().toLowerCase(), secondBody.data().email(),
        "Returned email does not match the created one")
    );
  }
}
