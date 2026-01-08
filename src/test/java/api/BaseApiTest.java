package api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.enums.HttpStatus;
import org.example.helpers.DataGenerator;
import org.example.requests.user.ApiUser;
import org.example.requests.user.LoginApiUser;
import org.example.responses.user_response.UserResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import requests.SimpleAction;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.http.ContentType.URLENC;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test_utils.TestUtils.assertResponseCode;

public abstract class BaseApiTest implements SimpleAction {
  public static final String BASE_API_URL = "https://practice.expandtesting.com/notes/api";
  protected static final ThreadLocal<Set<String>> createdNotes =
      ThreadLocal.withInitial(HashSet::new);

  protected static final ThreadLocal<Set<String>> createdUsers =
      ThreadLocal.withInitial(HashSet::new);

  private static final Logger log = LoggerFactory.getLogger(BaseApiTest.class);
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
  static void cleanUp() {
    cleanUpNotes();
    cleanUpUsers();

    Response deleteUser = SimpleAction.deleteUser(authToken);
    assertResponseCode(HttpStatus.OK.code(), deleteUser);
    log.info("[CLEANUP] Base User is deleted");
  }

  protected static String token() {
    return authToken;
  }

  @Step("[CLEANUP] Delete all notes created")
  public static void cleanUpNotes() {
    Set<String> notes = createdNotes.get();

    Allure.addAttachment("List of notes to delete", "application/json", notes.toString());

    if (notes.isEmpty()) {
      log.info("[CLEANUP] No notes to delete");
      return;
    }

    log.info("[CLEANUP] Deleting {} notes", notes.size());

    for (String noteId : createdNotes.get()) {
      try {
        Response response = SimpleAction.deleteNote(noteId, authToken);

        log.info(
            "[CLEANUP] Delete note id={} status={}",
            noteId,
            response.statusCode()
        );
      } catch (Exception e) {
        log.error("[CLEANUP] Failed to delete note id={}", noteId, e);
      }
    }
    createdNotes.get().clear();
  }

  protected void registeredNoteForCleanUp(String noteId) {
    createdNotes.get().add(noteId);
  }

  protected void registeredUserForCleanUp(String userToken) {
    createdUsers.get().add(userToken);
  }

  @Step("[CLEANUP] Delete all users created in tests")
  public static void cleanUpUsers() {
    Set<String> users = createdUsers.get();

    Allure.addAttachment("Users token to delete", "application/json", users.toString());

    if (users.isEmpty()) {
      log.info("[CLEANUP] No users to delete");
      return;
    }

    log.info("[CLEANUP] Deleting {} users", users.size());

    for (String userToken : users) {
      try {
        Response response = SimpleAction.deleteUser(userToken);

        log.info("[CLEANUP Delete user token={} status{}", userToken, response.statusCode());
      } catch (Exception e) {
        log.error("[CLEANUP] Failed to delete user token={}", userToken, e);
      }
    }
    createdUsers.get().clear();
  }
}