package requests;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.requests.user.LoginApiUser;

import static org.example.constants.Constants.*;
import static requests.RequestLibrary.*;

public interface SimpleAction {

  @Step("Authenticate with {user}")
  static Response userLogin(LoginApiUser user) {
    return sendPostRequest(user, LOGIN_URL);
  }

  @Step("Create a user with record: {record}")
  default Response createUser(String authContent, Record record) {
    return sendPostRequest(authContent, record, REGISTER_URL);
  }

  @Step("Get a user profile")
  default Response getUserProfile(String authContent) {
    return sendGetRequest(authContent, GET_PROFILE_URL);
  }

  @Step("Create a note with record: {record}")
  default Response createNote(Record record, String authContent) {
    return sendPostRequest(authContent, record, CREATE_A_NEW_NOTE);
  }

  @Step("Get a note with id: {id}")
  default Response getNoteById(String authContent, String id) {
    return sendGetRequest(authContent, String.format(GET_NOTE_BY_ID, id));
  }

  @Step("Get all notes")
  default Response getNotes(String authContent) {
    return sendGetRequest(authContent, GET_NOTES);
  }

  @Step("Delete a note with id: {id}")
  default Response deleteNote(String id,String authContent) {
    return sendDeleteRequest(authContent, String.format(DELETE_NOTE, id));
  }

  @Step("Patch a note with id: {id} and {record}")
  default Response patchNote(String authContent, Record record, String id) {
    return sendPatchRequest(authContent, record, String.format(PATCH_NOTE, id));
  }

  @Step("Update a note with id: {id} and {record}")
  default Response updateNote(String authContent, Record record, String id) {
    return sendPutRequest(authContent, record, String.format(PUT_NOTE, id));
  }

  @Step("Get health check")
  default Response getHealthCheck() {
    return sendGetRequest(HEALTH_CHECK_URL);
  }
}
