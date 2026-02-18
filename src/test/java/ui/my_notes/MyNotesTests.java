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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testdata.TestUsers;
import ui.BaseTest;

/**
 * Test suite for the My Notes application.
 * Contains end-to-end scenarios covering the full lifecycle of a note:
 * creation, verification, updates, and deletion.
 */
public class MyNotesTests extends BaseTest {

  User user = TestUsers.validUser();

  @DisplayName("[UI]. Notes App. Create a new note and delete as a card from Home Page")
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
  public void createAndDeleteNoteFromHomePageTest() {
    Note newNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        false,
        25,
        100);

    HomePage homePage = new HomePage(page()).open();
    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.clickLogin();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);
    myNotesHomePage.header.assertHeaderForAuthenticatedUserIsVisible();

    NoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(newNote);
    NoteComponent noteCard = myNotesHomePage.getNoteComponent(newNote);

    noteCard.compareNote(newNote);
    DeleteNoteModal deleteNoteModal = noteCard.deleteNote();
    deleteNoteModal.deleteNote();
    myNotesHomePage.waitForLoaderToDisappear();
    myNotesHomePage.verifyNoteIsDeleted(newNote);
    myNotesHomePage.waitForLoaderToDisappear();

    MyNotesWelcomePage mySecondNotesWelcomePage = myNotesHomePage.header.clickLogout();
    mySecondNotesWelcomePage.assertWelcomeTitleIsVisible();
    mySecondNotesWelcomePage.assertLoginButtonIsVisible();
    mySecondNotesWelcomePage.assertCreateAnAccountIsVisible();
  }

  @DisplayName("[UI]. Notes App. Create a new note and delete from Note Single Page")
  @Description("""
1. Open the Home page.
2. Navigate to the 'Notes App'.
3. Click the 'Login' button.
4. Log in with valid user credentials.
5. Assert that the authenticated user header is visible.
6. Open the 'Add Note' modal.
7. Create a new note with generated data.
8. Locate the created note card on the Home Page.
9. Compare the note card content with the initial data.
10. Click the 'View' button to navigate to the 'MyNoteSinglePage'.
11. Verify the note title and description on the standalone page.
12. Open the 'Delete Note' modal from the Single Note Page.
13. Confirm the note deletion.
14. Wait for the loading spinner to disappear.
15. Verify that the user is redirected to the Home Page and the note is no longer present.
16. Click the 'Logout' button.
17. Assert that the Welcome page title is visible.
18. Assert that the 'Login' and 'Create account' buttons are visible.
""")
  @Test
  public void createAndDeleteNoteFromNoteSinglePageTest() {
    Note newNote = DataGenerator.generateNewNote(
        NoteCategory.Home,
        false,
        25,
        100);

    HomePage homePage = new HomePage(page()).open();
    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.clickLogin();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);
    myNotesHomePage.header.assertHeaderForAuthenticatedUserIsVisible();

    NoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(newNote);
    NoteComponent noteCard = myNotesHomePage.getNoteComponent(newNote);

    noteCard.compareNote(newNote);
    MyNoteSinglePage myNote = noteCard.viewNote();
    //TODO Добавить проверку контента заметки на отдельноой странице
    DeleteNoteModal deleteNoteModal = myNote.deleteNote();

    deleteNoteModal.deleteNote();
    myNotesHomePage.waitForLoaderToDisappear();
    myNotesHomePage.verifyNoteIsDeleted(newNote);
    myNotesHomePage.waitForLoaderToDisappear();

    MyNotesWelcomePage mySecondNotesWelcomePage = myNotesHomePage.header.clickLogout();
    mySecondNotesWelcomePage.assertWelcomeTitleIsVisible();
    mySecondNotesWelcomePage.assertLoginButtonIsVisible();
    mySecondNotesWelcomePage.assertCreateAnAccountIsVisible();
  }

  /**
   * End-to-end test case for creating, updating, and deleting a note.
   * Validates UI transitions, modal interactions, and data consistency across updates.
   */
  @DisplayName("[UI]. Notes App. Create a new note, update and delete it")
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
  public void createUpdateDeleteNoteTest() {
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
    HomePage homePage = new HomePage(page()).open();
    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.clickLogin();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);
    myNotesHomePage.header.assertHeaderForAuthenticatedUserIsVisible();

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

    MyNotesWelcomePage mySecondNotesWelcomePage = myNotesHomePage.header.clickLogout();
    mySecondNotesWelcomePage.assertWelcomeTitleIsVisible();
    mySecondNotesWelcomePage.assertLoginButtonIsVisible();
    mySecondNotesWelcomePage.assertCreateAnAccountIsVisible();
  }


}
