package requests;
import io.restassured.response.Response;
import org.example.requests.user.UiUser;

import static org.example.constants.Constants.*;
import static requests.RequestLibrary.*;

public interface SimpleAction {

  static Response authUser(UiUser user) {
    return sendPostRequest(user, LOGIN_URL);
  }

  default Response createUser(Record record, String authContent) {
    return sendPostRequest(record, authContent, REGISTER_URL);
  }

  default Response createNote(Record record, String authContent) {
    return sendPostRequest(record, authContent, CREATE_A_NEW_NOTE);
  }

  default Response getNoteById(String id, String authContent) {
    return sendGetRequest(String.format(GET_NOTE_BY_ID, id), authContent);
  }

  default Response getNotes(String authContent) {
    return sendGetRequest(GET_NOTES, authContent);
  }

  default Response deleteNote(String id,String authContent) {
    return sendDeleteRequest(String.format(DELETE_NOTE, id), authContent);
  }

  default Response patchNote(Record record, String id, String authContent) {
    return sendPatchRequest(record, authContent, String.format(PATCH_NOTE, id));
  }

  default Response updateNote(Record record, String id, String authContent) {
    return sendPutRequest(record, authContent, String.format(PUT_NOTE, id));
  }

  default Response getHealthCheck() {
    return sendGetRequest(HEALTH_CHECK_URL);
  }
}
