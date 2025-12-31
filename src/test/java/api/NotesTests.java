package api;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.enums.HttpStatus;
import org.example.requests.ApiNote;
import org.example.requests.UpdateApiNote;
import org.example.requests.user.PatchNote;
import org.example.responses.BaseResponse;
import org.example.responses.note_response.ApiNoteResponse;
import org.example.responses.note_response.ApiNotesResponse;
import org.example.responses.note_response.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.constants.Alerts.*;
import static org.example.constants.Constants.CREATE_NOTE_SCHEMA;
import static org.example.helpers.DataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.assertResponseCode;
import static utils.TestUtils.assertResponseSchema;

public class NotesTests extends BaseApiTest {

  @DisplayName("[API. Notes]. POST method. Create a new note")
  @Description("""
      1. Create a new note as new instance of the class ApiUser.
      2. Create a new note.
      3. Assert a response.
      """)
  @Test
  public void createNewNoteTest() {
    ApiNote note = generateNewRandomApiNote(20, 20);

    Response createApiNote = createNote(note, authToken);
    assertResponseCode(HttpStatus.OK.code(), createApiNote);
    assertResponseSchema(CREATE_NOTE_SCHEMA, createApiNote);

    ApiNoteResponse body = createApiNote.as(ApiNoteResponse.class);

    assertAll("Validate response of created note",
        () -> assertTrue(body.success(), "Success is true"),
        () -> assertEquals(HttpStatus.OK.code(), body.status(), "Response status is 200 OK"),
        () -> assertEquals(NOTE_CREATED, body.message(), "Message is correct"),
        () -> assertNotNull(body.data(), "Data is not null"),
        () -> assertNotNull(body.data().id(), "Id of the note is not null"),
        () -> assertEquals(note.title(), body.data().title(), "Title of the note is correct"),
        () -> assertEquals(note.description(), body.data().description(), "Description of the note is correct"),
        () -> assertEquals(note.category().toString(), body.data().category(), "Category of the note is correct"),
        () -> assertFalse(body.data().completed(), "Status of the note is not completed"),
        () -> assertNotNull(body.data().user_id(), "User_id is not null")
    );
  }

  @DisplayName("[API. Notes]. GET method. Get a note by its Id")
  @Description("""
      1. Create a new note as new instance of the class ApiUser.
      2. Create a new note.
      3. Get a note by its Id
      4. Assert a response.
      """)
  @Test
  public void getNoteByIdTest() {
    ApiNote note = generateNewRandomApiNote(20, 20);

    Response createApiNote = createNote(note, authToken);
    assertResponseCode(HttpStatus.OK.code(), createApiNote);
    assertResponseSchema(CREATE_NOTE_SCHEMA, createApiNote);

    ApiNoteResponse zeroBody = createApiNote.as(ApiNoteResponse.class);
    String noteId = zeroBody.data().id();

    Response getApiNote = getNoteById(authToken, noteId);
    assertResponseCode(HttpStatus.OK.code(), getApiNote);

    ApiNoteResponse body = getApiNote.as(ApiNoteResponse.class);

    assertAll("Validate a response of a note",
        () -> assertTrue(body.success(), "Success is true"),
        () -> assertEquals(HttpStatus.OK.code(), body.status(), "Response status is 200 OK"),
        () -> assertEquals(NOTE_RETRIEVED, body.message(), "Message is correct"),
        () -> assertNotNull(body.data(), "Data is not null"),
        () -> assertEquals(noteId, body.data().id(), "Id is equal"),
        () -> assertEquals(note.title(), body.data().title(), "Title of the note is correct"),
        () -> assertEquals(note.description(), body.data().description(), "Description of the note is correct"),
        () -> assertEquals(note.category().toString(), body.data().category(), "Category of the note is correct"),
        () -> assertFalse(body.data().completed(), "Status of the note is not completed"),
        () -> assertEquals(zeroBody.data().created_at(), body.data().created_at(), "created_at is correct"),
        () -> assertEquals(zeroBody.data().updated_at(), body.data().updated_at(), "updated_at is correct"),
        () -> assertNotNull(body.data().user_id(), "User_id is not null")
    );
  }

  @DisplayName("[API. Notes]. GET method. Get a note by invalid Id")
  @Description("""
      1. Create a new note as new instance of the class ApiUser.
      2. Create a new note.
      3. Get a note by its Id
      4. Assert a response.
      """)
  @Test
  public void getNoteByInvalidIdTest() {
    String noteId = generateRandomId();

    Response getApiNote = getNoteById(authToken, noteId);
    assertResponseCode(HttpStatus.NOT_FOUND.code(), getApiNote);

    BaseResponse body = getApiNote.as(BaseResponse.class);

    assertAll("Validate note not found response",
        () -> assertFalse(body.success(), "Success is false"),
        () -> assertEquals(HttpStatus.NOT_FOUND.code(), body.status(), "Response status is 404 Not found"),
        () -> assertEquals(NOTE_NOT_FOUND, body.message())
    );
  }

  @DisplayName("[API. Notes]. GET method. Get notes")
  @Description("""
      1. Create a new note as new instance of the class ApiUser.
      2. Create a new note.
      3. Get all notes.
      4. Assert a response.
      """)
  @Test
  public void getNotesTest() {
    ApiNote note = generateNewRandomApiNote(20, 20);

    Response createApiNote = createNote(note, authToken);
    assertResponseCode(HttpStatus.OK.code(), createApiNote);
    assertResponseSchema(CREATE_NOTE_SCHEMA, createApiNote);

    Response getApiNotes = getNotes(authToken);
    assertResponseCode(HttpStatus.OK.code(), getApiNotes);

    ApiNotesResponse body = getApiNotes.as(ApiNotesResponse.class);
    List<Data> notes = body.data();

    assertAll("Validate notes response",
        () -> assertTrue(body.success(), "Success is true"),
        () -> assertEquals(HttpStatus.OK.code(), body.status(), "Response status is 200 OK"),
        () -> assertEquals(NOTES_RETRIEVED, body.message()),
        () -> assertNotNull(notes.size())
    );
  }

  @DisplayName("[API. Notes]. DELETE method. Delete a note")
  @Description("""
      1. Create a new note as new instance of the class ApiUser.
      2. Create a new note.
      3. Delete a note.
      4. Try to get a note by its Id.
      5. Assert a response.
      """)
  @Test
  public void deleteNoteTest() {
    ApiNote note = generateNewRandomApiNote(20, 20);

    Response createApiNote = createNote(note, authToken);
    assertResponseCode(HttpStatus.OK.code(), createApiNote);
    assertResponseSchema(CREATE_NOTE_SCHEMA, createApiNote);

    ApiNoteResponse zeroBody = createApiNote.as(ApiNoteResponse.class);
    String noteId = zeroBody.data().id();

    Response deleteApiNote = deleteNote(noteId, authToken);
    assertResponseCode(HttpStatus.OK.code(), deleteApiNote);

    BaseResponse body = deleteApiNote.as(BaseResponse.class);
    assertAll("Validate a note deleted",
        () -> assertTrue(body.success(), "Success is true"),
        () -> assertEquals(HttpStatus.OK.code(), body.status(), "Response status is 200 OK"),
        () -> assertEquals("Note successfully deleted", body.message())
    );

    Response getApiNote = getNoteById(authToken, noteId);
    assertResponseCode(HttpStatus.NOT_FOUND.code(), getApiNote);

    BaseResponse getBody = getApiNote.as(BaseResponse.class);

    assertAll("Validate note not found response",
        () -> assertFalse(getBody.success(), "Success is false"),
        () -> assertEquals(HttpStatus.NOT_FOUND.code(), getBody.status(), "Response status is 404 Not found"),
        () -> assertEquals(NOTE_NOT_FOUND, getBody.message())
    );
  }

  @DisplayName("[API. Notes]. PUT method. Update an existing note")
  @Description("""
      1. Create a new note as new instance of the class ApiUser.
      2. Create a new note.
      3. Update an existing note.
      4. Assert a response.
      """)
  @Test
  public void putNoteTest() {
    ApiNote note = generateNewRandomApiNote(20, 20);

    Response createApiNote = createNote(note, authToken);
    assertResponseCode(HttpStatus.OK.code(), createApiNote);
    assertResponseSchema(CREATE_NOTE_SCHEMA, createApiNote);

    ApiNoteResponse zeroBody = createApiNote.as(ApiNoteResponse.class);
    String noteId = zeroBody.data().id();

    UpdateApiNote updateApiNote = generateUpdateApiNote(
        20,
        20,
        true);

    Response updateNote = updateNote(authToken, updateApiNote, noteId);
    assertResponseCode(HttpStatus.OK.code(), updateNote);

    ApiNoteResponse body = updateNote.as(ApiNoteResponse.class);
    assertAll("Validate update the completed status of a note",
        () -> assertTrue(body.success(), "Success is true"),
        () -> assertEquals(HttpStatus.OK.code(), body.status(), "Response status is 200 OK"),
        () -> assertEquals("Note successfully Updated", body.message()),
        () -> assertNotNull(body.data(), "Data is not null"),
        () -> assertEquals(noteId, body.data().id(), "Note Id is correct"),
        () -> assertEquals(updateApiNote.title(), body.data().title(), "Title is correct"),
        () -> assertEquals(updateApiNote.description(), body.data().description(), "Description is correct"),
        () -> assertEquals(updateApiNote.category().toString(), body.data().category(), "Category is correct"),
        () -> assertEquals(updateApiNote.completed(), body.data().completed(), "Completed status is correct"),
        () -> assertEquals(zeroBody.data().created_at(), body.data().created_at(), "Created_at is correct"),
        () -> assertNotEquals(body.data().created_at(),
            body.data().updated_at(), "created_at and updated_at are not equal")
    );
  }

  @DisplayName("[API. Notes]. PATCH method. Update the completed status of a note")
  @Description("""
      1. Create a new note as new instance of the class ApiUser.
      2. Create a new note.
      3. Update the completed status of a note.
      4. Assert a response.
      """)
  @Test
  public void patchNoteTest() {
    boolean completed = true;
    ApiNote note = generateNewRandomApiNote(20, 20);

    Response createApiNote = createNote(note, authToken);
    assertResponseCode(HttpStatus.OK.code(), createApiNote);
    assertResponseSchema(CREATE_NOTE_SCHEMA, createApiNote);

    ApiNoteResponse zeroBody = createApiNote.as(ApiNoteResponse.class);
    String noteId = zeroBody.data().id();

    PatchNote patchNote = new PatchNote(completed);

    Response patchApiNote = patchNote(authToken, patchNote, noteId);
    assertResponseCode(HttpStatus.OK.code(), patchApiNote);

    ApiNoteResponse body = patchApiNote.as(ApiNoteResponse.class);
    assertAll("Validate update the completed status of a note",
        () -> assertTrue(body.success(), "Success is true"),
        () -> assertEquals(HttpStatus.OK.code(), body.status(), "Response status is 200 OK"),
        () -> assertEquals("Note successfully Updated", body.message()),
        () -> assertTrue(body.data().completed())
    );
  }
}