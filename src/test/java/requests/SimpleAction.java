package requests;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.requests.user.UiUser;

import static org.example.constants.Constants.*;
import static requests.RequestLibrary.*;

public interface SimpleAction {

  @Step("Authenticate with {user}")
  static Response authUser(UiUser user) {
    return sendPostRequest(user, LOGIN_URL);
  }

  @Step("Create a user with record: {record}")
  default Response createUser(Record record, String authContent) {
    return sendPostRequest(record, authContent, REGISTER_URL);
  }

  @Step("Create a note with record: {record}")
  default Response createNote(Record record, String authContent) {
    return sendPostRequest(record, authContent, CREATE_A_NEW_NOTE);
  }

  @Step("Get a note with id: {id}")
  default Response getNoteById(String id, String authContent) {
    return sendGetRequest(String.format(GET_NOTE_BY_ID, id), authContent);
  }

  @Step("Get all notes")
  default Response getNotes(String authContent) {
    return sendGetRequest(GET_NOTES, authContent);
  }

  @Step("Delete a note with id: {id}")
  default Response deleteNote(String id,String authContent) {
    return sendDeleteRequest(String.format(DELETE_NOTE, id), authContent);
  }

  @Step("Patch a note with id: {id} and {record}")
  default Response patchNote(Record record, String id, String authContent) {
    return sendPatchRequest(record, authContent, String.format(PATCH_NOTE, id));
  }

  @Step("Update a note with id: {id} and {record}")
  default Response updateNote(Record record, String id, String authContent) {
    return sendPutRequest(record, authContent, String.format(PUT_NOTE, id));
  }

  @Step("Get health check")
  default Response getHealthCheck() {
    return sendGetRequest(HEALTH_CHECK_URL);
  }
}
