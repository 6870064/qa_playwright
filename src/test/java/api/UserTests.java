package api;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.enums.HttpStatus;
import org.example.helpers.DataGenerator;
import org.example.requests.user.ApiUser;
import org.example.requests.user.LoginApiUser;
import org.example.responses.BaseResponse;
import org.example.responses.login_user_response.LoginUserResponse;
import org.example.responses.user_response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.SimpleAction;

import java.util.stream.Stream;

import static org.example.constants.Alerts.INVALID_EMAIL_MESSAGE;
import static org.example.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.assertResponseCode;
import static utils.TestUtils.assertResponseSchema;

public class UserTests extends BaseApiTest {

  private static Stream<Arguments> provideTestData() {
    return Stream.of(
        Arguments.of(
            "invalid password",
            USERNAME,
            "password",
            HttpStatus.BAD_REQUEST,
            INVALID_EMAIL_MESSAGE),
        Arguments.of(
            "invalid email",
            "@gmail.com",
            "password",
            HttpStatus.BAD_REQUEST,
            INVALID_EMAIL_MESSAGE),
        Arguments.of(
            "empty email",
            "",
            "password",
            HttpStatus.BAD_REQUEST,
            INVALID_EMAIL_MESSAGE));
  }

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

    Response createUser = createUser(authToken, apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);
    assertResponseSchema(USER_RESPONSE_SCHEMA, createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertAll(
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

    Response createUser = createUser(authToken, apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

    String userId = body.data().id();

    LoginApiUser loginApiUser = new LoginApiUser(apiUser.email(), apiUser.password());

    Response loginUser = SimpleAction.userLogin(loginApiUser);
    assertResponseCode(HttpStatus.OK.code(), loginUser);
    assertResponseSchema(USER_RESPONSE_SCHEMA, loginUser);

    LoginUserResponse secondBody = loginUser.as(LoginUserResponse.class);
    assertAll(
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
            "Returned email does not match the created one"),

        () -> assertNotNull(secondBody.data().token()));
  }

  @DisplayName("[API. User]. POST method. Attempt to Login by user")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Assert a response.
      """)
  @MethodSource("provideTestData")
  @ParameterizedTest(name = " with {0}")
  public void loginWithInvalidUserTest(
      String testParameter,
      String login,
      String password,
      HttpStatus httpStatus,
      String errorMessage) {
    LoginApiUser loginApiUser = new LoginApiUser(login, password);

    Response loginUser = SimpleAction.userLogin(loginApiUser);
    assertResponseCode(httpStatus.code(), loginUser);
    assertResponseSchema(BASE_RESPONSE_SCHEMA, loginUser);

    BaseResponse body = loginUser.as(BaseResponse.class);
    assertAll(
        () -> assertFalse(body.success(),
            "Expected success=false, but was true"),

        () -> assertEquals(httpStatus.code(), body.status(),
            "Incorrect status in response body"),

        () -> assertEquals(errorMessage, body.message(),
            "Unexpected login message"));
  }

  @DisplayName("[API. User]. POST method. Get user profile")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Get user profile.
      4. Assert a response.
      """)
  @Test
  public void getUserProfileTest() {
    ApiUser apiUser = new ApiUser(
        new DataGenerator().generateRandomName(8, 30),
        new DataGenerator().generateRandomEmail(true),
        new DataGenerator().generateRandomPassword(8, 30));

    Response createUser = createUser(authToken, apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

    String userId = body.data().id();

    LoginApiUser loginApiUser = new LoginApiUser(apiUser.email(), apiUser.password());

    Response loginUser = SimpleAction.userLogin(loginApiUser);
    assertResponseCode(HttpStatus.OK.code(), loginUser);

    LoginUserResponse secondBody = loginUser.as(LoginUserResponse.class);
    assertAll(
        () -> assertTrue(secondBody.success(),
            "Expected success=true, but was false"),

        () -> assertEquals(HttpStatus.OK.code(), secondBody.status(),
            "Incorrect status in response body"));

    String token = secondBody.data().token();

    Response getProfile = getUserProfile(token);
    assertResponseCode(HttpStatus.OK.code(), getProfile);
    assertResponseSchema(USER_RESPONSE_SCHEMA, getProfile);

    UserResponse thirdBody = getProfile.as(UserResponse.class);
    assertAll(
        () -> assertTrue(thirdBody.success(),
            "Expected success=true, but was false"),

        () -> assertEquals(HttpStatus.OK.code(), thirdBody.status(),
            "Incorrect status in response body"),

        () -> assertEquals("Profile successful", thirdBody.message(),
            "Unexpected creation message"),

        () -> assertEquals(userId, thirdBody.data().id(),
            "User ID is not equal"),

        () -> assertEquals(apiUser.name(), thirdBody.data().name(),
            "Returned name does not match the created one"),

        () -> assertEquals(apiUser.email().toLowerCase(), thirdBody.data().email(),
            "Returned email does not match the created one"));
  }
}
