package ui.my_notes;

import io.qameta.allure.Description;
import org.example.components.NoteComponent;
import org.example.components.modals.DeleteNoteModal;
import org.example.components.modals.NoteModal;
import org.example.enums.NoteCategory;
import org.example.helpers.DataGenerator;
import org.example.objects.Note;
import org.example.objects.User;
import org.example.pages.my_notes.MyNoteSinglePage;
import org.example.pages.my_notes.MyNotesHomePage;
import org.example.pages.my_notes.MyNotesLoginPage;
import org.example.pages.my_notes.MyNotesWelcomePage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testdata.TestUsers;
import ui.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite for the My Notes application.
 * Contains end-to-end scenarios covering the full lifecycle of a note:
 * creation, verification, updates, and deletion.
 */
public class MyNotesTests extends BaseTest {
  User user = TestUsers.validUser();
  private MyNotesHomePage myNotesHomePage;
  private MyNotesWelcomePage mySecondNotesWelcomePage;

  @BeforeEach
  void userLogin() {
    HomePage homePage = new HomePage(page()).open();
    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.clickLogin();
    myNotesHomePage = myNotesLoginPage.loginUser(user);
    myNotesHomePage.header.assertHeaderForAuthenticatedUserIsVisible();
    myNotesHomePage.checkAndDeleteAllOldNotes();
  }

  @AfterEach
  void userLogout() {
    mySecondNotesWelcomePage = myNotesHomePage.header.clickLogout();
    mySecondNotesWelcomePage.assertWelcomeTitleIsVisible();
    mySecondNotesWelcomePage.assertLoginButtonIsVisible();
    mySecondNotesWelcomePage.assertCreateAnAccountIsVisible();
  }

  @DisplayName("[UI]. Notes App. Create a new note and delete as a card from Home Page")
  @Description("""
      1. Open the Home page.
      2. Navigate to the 'Notes App'.
      3. Click the 'Login' button.
      4. Log in with valid user credentials.
      5. Assert that the authenticated user header is visible.
      6. Open the 'Add Note' modal.
      7. Create a new note with generated data (isCompleted: false).
      8. Locate the created note card using the root locator.
      9. Verify that the note card content matches the initial data.
      10. Verify that the 'Completed' checkbox is initially unchecked.
      11. Click on the 'Completed' toggle/checkbox on the note card.
      12. Assert that the note status has changed to 'Completed'.
      13. Verify that the UI state is now inverted relative to the initial Note object.
      14. Wait for the loading spinner to disappear after the status update.
      15. Locate the 'Delete' button on the note card.
      16. Open the 'Delete Note' modal.
      17. Confirm the note deletion in the modal.
      18. Wait for the loading spinner to disappear.
      19. Verify that the note is no longer present in the list (Verify deleted).
      20. Wait for the loading spinner to disappear again to ensure stability.
      21. Locate the logout button in the header component.
      22. Click the 'Logout' button.
      23. Assert that the Welcome page title is visible.
      24. Assert that the 'Login' button is visible on the Welcome page.
      25. Assert that the 'Create an account' button is visible.
      26. Verify successful redirection to the landing state.
      27. Check that the user session is terminated.
      28. Ensure the page is ready for the next test execution.
      """)
  @Test
  public void createAndDeleteNoteFromHomePageTest() {
    Note newNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        false,
        25,
        100);

    NoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(newNote);
    NoteComponent noteCard = myNotesHomePage.getNoteComponent(newNote);

    noteCard.compareNote(newNote);
    noteCard.setStatus();
    assertTrue(noteCard.isCompleted() != newNote.isCompleted(),
        "The note completion status should have changed after toggle");

    DeleteNoteModal deleteNoteModal = noteCard.deleteNote();
    deleteNoteModal.deleteNote();
    myNotesHomePage.waitForLoaderToDisappear();
    myNotesHomePage.verifyNoteIsDeleted(newNote);
    myNotesHomePage.waitForLoaderToDisappear();
  }

  @DisplayName("[UI]. Notes App. Create a new note and delete from Note Single Page")
  @Description("""
      1. Open the Home page.
      2. Navigate to the 'Notes App'.
      3. Click the 'Login' button.
      4. Log in with valid user credentials.
      5. Assert that the authenticated user header is visible.
      6. Open the 'Add Note' modal.
      7. Create a new note with generated data (isCompleted: false).
      8. Locate the created note card on the Home Page.
      9. Compare the note card content (title, description, category) with the initial data.
      10. Click the 'View' button on the note card to navigate to the standalone page.
      11. Initialize 'MyNoteSinglePage' and wait for it to load.
      12. Verify the note title on the standalone page matches the generated data.
      13. Verify the note description on the standalone page matches the generated data.
      14. Check the initial completion status on the single note page.
      15. Call 'setStatus()' to toggle the note completion checkbox.
      16. Assert that the completion status is now inverted compared to the initial note state.
      17. Verify that the UI reflects the change in the completion toggle.
      18. Click the 'Delete' button located on the Single Note Page.
      19. Confirm the note deletion in the confirmation modal.
      20. Wait for the loading spinner to disappear after deletion.
      21. Verify that the user is redirected back to the My Notes Home Page.
      22. Assert that the deleted note is no longer present in the main notes list.
      23. Wait for the loading spinner to disappear to ensure the UI is stable.
      24. Click the 'Logout' button in the header component.
      25. Assert that the Welcome page title is visible.
      26. Assert that the 'Login' button is visible.
      27. Assert that the 'Create an account' button is visible.
      28. Verify that the session is terminated and the page is ready for the next run.
      """)
  @Test
  public void createAndDeleteNoteFromNoteSinglePageTest() {
    Note newNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        false,
        25,
        100);

    NoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(newNote);
    NoteComponent noteCard = myNotesHomePage.getNoteComponent(newNote);

    noteCard.compareNote(newNote);
    MyNoteSinglePage myNote = noteCard.viewNote();
    myNote.compareNote(newNote);
    myNote.setStatus();
    assertTrue(newNote.isCompleted() != myNote.isCompleted(),
        "The note completion status should have changed after toggle");

    DeleteNoteModal deleteNoteModal = myNote.deleteNote();
    deleteNoteModal.deleteNote();
    myNotesHomePage.waitForLoaderToDisappear();
    myNotesHomePage.verifyNoteIsDeleted(newNote);
    myNotesHomePage.waitForLoaderToDisappear();
  }

  /**
   * End-to-end test case for creating, updating from Home page, and deleting a note.
   * Validates UI transitions, modal interactions, and data consistency across updates.
   */
  @DisplayName("[UI]. Notes App. Create a new note, update it from Home page and delete it")
  @Description("""
      1. Open the Home page.
      2. Navigate to the 'Notes App'.
      3. Click the 'Login' button.
      4. Log in with valid user credentials.
      5. Assert that the authenticated user header is visible.
      6. Open the 'Add Note' modal.
      7. Create a new note with initial data.
      8. Locate the created note card.
      9. Open the 'Edit Note' modal for the initial note.
      10. Verify that the modal data matches the initial note.
      11. Update the note with new data.
      12. Wait for the loading spinner to disappear.
      13. Locate the updated note card.
      14. Open the 'Edit Note' modal for the updated note.
      15. Verify that the modal data matches the updated note.
      16. Cancel the edit action.
      17. Wait for the loading spinner to disappear.
      18. Open the 'Delete Note' modal for the updated note.
      19. Confirm the note deletion.
      20. Wait for the loading spinner to disappear.
      21. Verify that the note is no longer present in the list.
      22. Click the 'Logout' button.
      23. Assert that the Welcome page title is visible.
      24. Assert that the 'Login' and 'Create account' buttons are visible.
      """)
  @Test
  public void createUpdateDeleteNoteFromHomePageTest() {
    Note initialNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        false,
        25,
        100);

    Note updatedNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        true,
        30,
        120);

    NoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(initialNote);
    NoteComponent noteCard = myNotesHomePage.getNoteComponent(initialNote);
    NoteModal noteModal = noteCard.editNote();
    noteModal.compareNote(initialNote);
    noteModal.updateNote(updatedNote);
    myNotesHomePage.waitForLoaderToDisappear();

    NoteComponent secondNoteCard = myNotesHomePage.getNoteComponent(updatedNote);
    NoteModal secondNoteModal = secondNoteCard.editNote();
    secondNoteModal.compareNote(updatedNote);
    secondNoteModal.cancelEditNote();
    myNotesHomePage.waitForLoaderToDisappear();

    DeleteNoteModal deleteNoteModal = secondNoteCard.deleteNote();
    deleteNoteModal.deleteNote();
    myNotesHomePage.waitForLoaderToDisappear();
    myNotesHomePage.verifyNoteIsDeleted(updatedNote);
    myNotesHomePage.waitForLoaderToDisappear();
  }

  /**
   * End-to-end test case for creating, updating from Home page, and deleting a note.
   * Validates UI transitions, modal interactions, and data consistency across updates.
   */
  @DisplayName("[UI]. Notes App. Create a new note, update it from Single page and delete it")
  @Description("""
      1. Open the Home page.
      2. Navigate to the 'Notes App'.
      3. Click the 'Login' button.
      4. Log in with valid user credentials.
      5. Assert that the authenticated user header is visible.
      6. Open the 'Add Note' modal.
      7. Create a new note with initial data.
      8. Locate the created note card.
      9. Open the 'Edit Note' modal for the initial note.
      10. Verify that the modal data matches the initial note.
      11. Update the note with new data.
      12. Wait for the loading spinner to disappear.
      13. Locate the updated note card.
      14. Open the 'Edit Note' modal for the updated note.
      15. Verify that the modal data matches the updated note.
      16. Cancel the edit action.
      17. Wait for the loading spinner to disappear.
      18. Open the 'Delete Note' modal for the updated note.
      19. Confirm the note deletion.
      20. Wait for the loading spinner to disappear.
      21. Verify that the note is no longer present in the list.
      22. Click the 'Logout' button.
      23. Assert that the Welcome page title is visible.
      24. Assert that the 'Login' and 'Create account' buttons are visible.
      """)
  @Test
  public void createUpdateDeleteNoteFromMyNoteSinglePageTest() {
    Note newNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        false,
        25,
        100);

    Note updatedNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        true,
        30,
        120);

    NoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(newNote);
    NoteComponent noteCard = myNotesHomePage.getNoteComponent(newNote);

    noteCard.compareNote(newNote);
    MyNoteSinglePage myNote = noteCard.viewNote();
    myNote.compareNote(newNote);

    NoteModal noteModal = myNote.editNote();
    noteModal.updateNote(updatedNote);
    myNote.compareNote(updatedNote);
    DeleteNoteModal deleteNoteModal = myNote.deleteNote();
    deleteNoteModal.deleteNote();
    myNotesHomePage.waitForLoaderToDisappear();
    myNotesHomePage.verifyNoteIsDeleted(newNote);
    myNotesHomePage.waitForLoaderToDisappear();
  }

  @DisplayName("[UI]. Notes App. Mass creation and mass deletion of notes")
  @Description("""
      1. Log in to 'Notes App'.
      2. Create multiple notes (15) using mass creation helper.
      3. Verify that the actual notes count on the Home page matches the expected amount.
      4. Perform mass deletion of all notes.
      5. Verify that the list is empty and the 'No notes' message is displayed (inside deleteAllNotes).
      6. Log out and verify Welcome page redirection.
      """)
  @Test
  public void createAndDeleteNotesFromMyNoteHomePageTest() {
    int notesAmount = 7;

    myNotesHomePage.createMultipleNotes(notesAmount);
    assertEquals(notesAmount, myNotesHomePage.getNotesCount());
    myNotesHomePage.deleteAllNotes();
  }

  @DisplayName("[UI]. Notes App. Search and deletion of notes")
  @Description("""
      1. Log in to the 'Notes App'.
      2. Create a specific test note and verify its data.
      3. Add 3 additional notes to the list.
      4. Search for the specific note by its title.
      5. Verify that only 1 note is found and matches the expected data.
      6. Clear search input and delete all notes from the list.
      7. Verify empty state and log out.
      """)
  @Test
  public void searchNoteByTitleTest() {
    Note newNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        false,
        25,
        100);
    int notesAmount = 3;

    NoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(newNote);
    NoteComponent noteCard = myNotesHomePage.getNoteComponent(newNote);
    noteCard.compareNote(newNote);

    myNotesHomePage.createMultipleNotes(notesAmount);
    myNotesHomePage.waitForLoaderToDisappear();
    NoteComponent noteFoundCard = myNotesHomePage.searchNoteByTitle(newNote);
    noteFoundCard.compareNote(newNote);
    assertEquals(1, myNotesHomePage.getNotesCount());
    myNotesHomePage.clearSearchInput();

    myNotesHomePage.deleteAllNotes();
  }

  @DisplayName("[UI]. Notes App. Attempt to create note without title and description")
  @Description("""
      1. Log in to the 'Notes App'.
      2. Try to create a note without title and description.
      3. Verify error messages.
      4. Verify empty state and log out.
      """)
  @Test
  public void AttemptToCreateEmptyNoteTest() {
    Note newNote = new Note(
        NoteCategory.Home,
        false,
        "",
        "");

    NoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(newNote);
    addNoteModal.emptyTitleErrorIsDisplayed();
    addNoteModal.emptyDescriptionErrorIsDisplayed();
    addNoteModal.cancelCreateNote();
  }

  @DisplayName("[UI] Notes App: Verify note creation and filtering by category")
  @Description("""
      1. Log in to the 'Notes App' as an authenticated user.
      2. Create three separate notes in different categories: Home, Work, and Personal.
      3. Filter by 'Home' category and verify the note content matches.
      4. Filter by 'Work' category and verify the note content matches.
      5. Filter by 'Personal' category and verify the note content matches.
      6. Delete all created notes to clean up.
      7. Log out and verify the user is redirected to the Welcome page.
      """)
  @Test
  public void filterNotesTest() {
    Note homeNote = DataGenerator.generateNewNote(NoteCategory.Home,
        false,
        25,
        100);

    Note workNote = DataGenerator.generateNewNote(NoteCategory.Work,
        false,
        25,
        100);

    Note personalNote = DataGenerator.generateNewNote(NoteCategory.Personal,
        false,
        25,
        100);

    myNotesHomePage.createNewNote(homeNote);
    myNotesHomePage.createNewNote(workNote);
    myNotesHomePage.createNewNote(personalNote);

    myNotesHomePage.selectCategory("home");
    NoteComponent homeNoteCard = myNotesHomePage.getNoteComponent(homeNote);
    homeNoteCard.compareNote(homeNote);

    myNotesHomePage.selectCategory("work");
    NoteComponent workNoteCard = myNotesHomePage.getNoteComponent(workNote);
    workNoteCard.compareNote(workNote);

    myNotesHomePage.selectCategory("personal");
    NoteComponent personalNoteCard = myNotesHomePage.getNoteComponent(personalNote);
    personalNoteCard.compareNote(personalNote);

    myNotesHomePage.deleteAllNotes();
  }
}
