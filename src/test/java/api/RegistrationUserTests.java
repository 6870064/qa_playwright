package api;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.enums.HttpStatus;
import org.example.helpers.DataGenerator;
import org.example.requests.user.ApiUser;
import org.example.responses.api_user_response.ApiUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    ApiUserResponse body = createUser.as(ApiUserResponse.class);
    Assertions.assertAll(
        ()-> assertTrue(body.success()),
        ()-> assertEquals(HttpStatus.CREATED.code(), body.status()),
        ()-> assertEquals("User account created successfully", body.message()),
        ()-> assertNotNull(body.data().id()),
        ()-> assertEquals(apiUser.name(), body.data().name()),
        ()-> assertEquals(apiUser.email().toLowerCase(), body.data().email())
    );







  }
}
