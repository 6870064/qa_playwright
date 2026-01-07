package api;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.enums.HttpStatus;
import org.example.helpers.DataGenerator;
import org.example.requests.ChangePasswordDto;
import org.example.requests.user.ApiUser;
import org.example.requests.user.LoginApiUser;
import org.example.requests.user.UpdateUserProfileDto;
import org.example.responses.BaseResponse;
import org.example.responses.login_user_response.LoginUserResponse;
import org.example.responses.update_user_response.UpdateUserResponse;
import org.example.responses.user_response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.SimpleAction;

import java.util.stream.Stream;

import static org.example.constants.Alerts.*;
import static org.example.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static test_utils.TestUtils.assertResponseCode;
import static test_utils.TestUtils.assertResponseSchema;

public class UserTests extends BaseApiTest {

  DataGenerator dataGenerator = new DataGenerator();

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

  private static Stream<Arguments> provideInvalidTestData() {
    return Stream.of(
        Arguments.of("Empty password", ""),
        Arguments.of("null password", null),
        Arguments.of("Short password", "123"));
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
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);
    assertResponseSchema(USER_RESPONSE_SCHEMA, createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertAll(
        () -> assertTrue(body.success(),
            "Expected success=true, but was false"),

        () -> assertEquals(HttpStatus.CREATED.code(), body.status(),
            "Incorrect status in response body"),

        () -> assertEquals(USER_CREATED_MESSAGE, body.message(),
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
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
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

        () -> assertEquals(OK_LOGIN_MESSAGE, secondBody.message(),
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

  @DisplayName("[API. User]. GET method. Get user profile")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Get user profile.
      5. Assert a response.
      """)
  @Test
  public void getUserProfileTest() {
    ApiUser apiUser = new ApiUser(
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
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

        () -> assertEquals(GET_PROFILE_MESSAGE, thirdBody.message(),
            "Unexpected creation message"),

        () -> assertEquals(userId, thirdBody.data().id(),
            "User ID is not equal"),

        () -> assertEquals(apiUser.name(), thirdBody.data().name(),
            "Returned name does not match the created one"),

        () -> assertEquals(apiUser.email().toLowerCase(), thirdBody.data().email(),
            "Returned email does not match the created one"));
  }

  @DisplayName("[API. User]. DELETE method. Logout user")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Logout user.
      5. Assert a response.
      """)
  @Test
  public void logoutUserTest() {
    ApiUser apiUser = new ApiUser(
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

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

    Response logoutUser = SimpleAction.logoutUser(token);
    assertResponseCode(HttpStatus.OK.code(), logoutUser);
    assertResponseSchema(BASE_RESPONSE_SCHEMA, logoutUser);

    BaseResponse thirdBody = logoutUser.as(BaseResponse.class);
    assertAll(
        () -> assertTrue(thirdBody.success(),
            "Expected success=true, but was false"),

        () -> assertEquals(HttpStatus.OK.code(), thirdBody.status(),
            "Incorrect status in response body"),

        () -> assertEquals(USER_LOGGED_OUT_MESSAGE, thirdBody.message(),
            "Unexpected logout message"));
  }

  @DisplayName("[API. User]. DELETE method. Delete user")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Delete user.
      5. Assert a response.
      """)
  @Test
  public void deleteUserTest() {
    ApiUser apiUser = new ApiUser(
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

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

    Response deleteUser = SimpleAction.deleteUser(token);
    assertResponseCode(HttpStatus.OK.code(), deleteUser);
    assertResponseSchema(BASE_RESPONSE_SCHEMA, deleteUser);

    BaseResponse thirdBody = deleteUser.as(BaseResponse.class);
    assertAll(
        () -> assertTrue(thirdBody.success(),
            "Expected success=true, but was false"),

        () -> assertEquals(HttpStatus.OK.code(), thirdBody.status(),
            "Incorrect status in response body"),

        () -> assertEquals(USER_DELETED_MESSAGE, thirdBody.message(),
            "Unexpected logout message"));
  }

  @DisplayName("[API. User]. PATCH method. Update user profile information")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Update user profile information.
      5. Assert a response.
      """)
  @Test
  public void updateUserTest() {
    ApiUser apiUser = new ApiUser(
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

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
    String id = secondBody.data().id();

    UpdateUserProfileDto updateUserProfileDto = new UpdateUserProfileDto(
        dataGenerator.generateRandomName(8,30),
        dataGenerator.generateRandomPhoneNumber("48"),
        dataGenerator.generateRandomCompanyName());

    Response updateUserProfile = SimpleAction.updateUserProfile(token, updateUserProfileDto);
    assertResponseCode(HttpStatus.OK.code(), updateUserProfile);
    assertResponseSchema(UPDATE_USER_RESPONSE_SCHEMA, updateUserProfile);

    UpdateUserResponse thirdBody = updateUserProfile.as(UpdateUserResponse.class);
    assertAll(
        ()-> assertTrue(thirdBody.success(),
            "Expected success=true, but was false"),

        ()-> assertEquals(HttpStatus.OK.code(), thirdBody.status(),
            "Incorrect status in response body"),

        ()-> assertEquals(USER_PROFILE_UPDATED_MESSAGE, thirdBody.message(),
            "Unexpected logout message"),

        ()-> assertEquals(id, thirdBody.data().id(),
            "Incorrect user Id in response body"),

        ()-> assertEquals(updateUserProfileDto.name(), thirdBody.data().name(),
            "Incorrect user name in response body"),


        ()-> assertEquals(apiUser.email().toLowerCase(),thirdBody.data().email(),
            "Incorrect user email in response body"),

        ()->assertEquals(updateUserProfileDto.phone(), thirdBody.data().phone(),
            "Incorrect user phone in response body"),

        ()->assertEquals(updateUserProfileDto.company(), thirdBody.data().company(),
            "Incorrect user company title in response body"));
  }

  @DisplayName("[API. User]. POST method. Change user's password")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Change user's password.
      5. Login by new user's password.
      6. Assert a response.
      """)
  @Test
  public void changeUsersPasswordTest() {
    ApiUser apiUser = new ApiUser(
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

    String id = body.data().id();

    LoginApiUser loginApiUser = new LoginApiUser(apiUser.email(), apiUser.password());

    Response loginUser = SimpleAction.userLogin(loginApiUser);
    assertResponseCode(HttpStatus.OK.code(), loginUser);

    LoginUserResponse secondBody = loginUser.as(LoginUserResponse.class);
    assertAll(
        () -> assertTrue(secondBody.success(),
            "Expected success=true, but was false"),

        () -> assertEquals(HttpStatus.OK.code(), secondBody.status(),
            "Incorrect status in response body"));

    ChangePasswordDto changePasswordDto = new ChangePasswordDto(
        apiUser.password(),
        new DataGenerator().generateRandomPassword(8, 30));

    String token = secondBody.data().token();

    Response changeUsersPassword = changeUsersPassword(token, changePasswordDto);
    assertResponseCode(HttpStatus.OK.code(), changeUsersPassword);

    BaseResponse thirdBody = changeUsersPassword.as(BaseResponse.class);

    assertAll(
        () -> assertTrue(thirdBody.success(),
            "Expected success=true, but was false"),

        () -> assertEquals(thirdBody.status(), HttpStatus.OK.code(),
            "Incorrect status in response body"),

        () -> assertEquals(thirdBody.message(), PASSWORD_CHANGED_MESSAGE,
            "Incorrect message in response body")
    );

    LoginApiUser newLoginApiUser = new LoginApiUser(apiUser.email(), changePasswordDto.newPassword());

    Response newLoginUser = SimpleAction.userLogin(newLoginApiUser);
    assertResponseCode(HttpStatus.OK.code(), newLoginUser);

    LoginUserResponse fourthBody = loginUser.as(LoginUserResponse.class);
    assertAll(
        ()-> assertTrue(fourthBody.success(),
            "Expected success=true, but was false"),

        ()-> assertEquals(HttpStatus.OK.code(), fourthBody.status(),
            "Incorrect status in response body"),

        ()-> assertEquals(OK_LOGIN_MESSAGE, fourthBody.message(),
            "Incorrect message in response body"),

        ()-> assertEquals(id, fourthBody.data().id(),
        "Incorrect user Id in response body"),

        ()-> assertEquals(apiUser.name(), fourthBody.data().name(),
            "Incorrect name Id in response body"),

        ()-> assertEquals(apiUser.email().toLowerCase(), fourthBody.data().email(),
            "Incorrect email Id in response body"));
  }

  @DisplayName("[API. User]. POST method. Change user's password by invalid data")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Try to change user's password by invalid data.
      5. Assert a response.
      """)
  @MethodSource("provideInvalidTestData")
  @ParameterizedTest
  public void changeUsersPasswordByInvalidDataTest(String testName, String newPassword) {
    ApiUser apiUser = new ApiUser(
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

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

    ChangePasswordDto changePasswordDto = new ChangePasswordDto(
        apiUser.password(),
        newPassword);

    Response changeUsersPassword = changeUsersPassword(token, changePasswordDto);
    assertResponseCode(HttpStatus.BAD_REQUEST.code(), changeUsersPassword);

    BaseResponse thirdBody = changeUsersPassword.as(BaseResponse.class);

    assertAll(
        () -> assertFalse(thirdBody.success(),
            "Expected success=false, but was true"),

        () -> assertEquals(thirdBody.status(), HttpStatus.BAD_REQUEST.code(),
            "Incorrect status in response body"),

        () -> assertEquals(thirdBody.message(), PASSWORD_LENGTH_MESSAGE,
            "Incorrect message in response body")
    );
  }

  @DisplayName("[API. User]. POST method. Change user's password by the same password")
  @Description("""
      1. Create a new apiUser as new instance of the class ApiUser.
      2. Create a new user.
      3. Login user.
      4. Try to change user's password by the same password.
      5. Assert a response.
      """)
  @Test
  public void changeUsersPasswordBySamePasswordTest() {
    ApiUser apiUser = new ApiUser(
        dataGenerator.generateRandomName(8, 30),
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomPassword(8, 30));

    Response createUser = SimpleAction.createUser(token(), apiUser);
    assertResponseCode(HttpStatus.CREATED.code(), createUser);

    UserResponse body = createUser.as(UserResponse.class);
    assertNotNull(body.data().id(), "User ID must not be null");

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

    ChangePasswordDto changePasswordDto = new ChangePasswordDto(
        apiUser.password(),
        apiUser.password());

    Response changeUsersPassword = changeUsersPassword(token, changePasswordDto);
    assertResponseCode(HttpStatus.BAD_REQUEST.code(), changeUsersPassword);

    BaseResponse thirdBody = changeUsersPassword.as(BaseResponse.class);

    assertAll(
        () -> assertFalse(thirdBody.success(),
            "Expected success=false, but was true"),

        () -> assertEquals(thirdBody.status(), HttpStatus.BAD_REQUEST.code(),
            "Incorrect status in response body"),

        () -> assertEquals(thirdBody.message(), SAME_PASSWORD_MESSAGE,
            "Incorrect message in response body"));
  }
}
