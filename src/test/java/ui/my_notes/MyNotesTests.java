package ui.my_notes;

import io.qameta.allure.Description;
import org.example.components.modals.AddNoteModal;
import org.example.enums.NoteCategory;
import org.example.objects.Note;
import org.example.objects.User;
import org.example.pages.my_notes.MyNotesHomePage;
import org.example.pages.my_notes.MyNotesLoginPage;
import org.example.pages.my_notes.MyNotesWelcomePage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testdata.TestUsers;
import ui.BaseTest;

public class MyNotesTests extends BaseTest {

  User user = TestUsers.validUser();

  @DisplayName("[UI]. Notes App. Login by existing user")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Notes App | React' page.
      3. Click a 'Login' button.
      4. Enter a valid user's Email.
      5. Enter a valid user's password.
      6. Click 'Login' button.
      7. Assert visibility header component.
      8. Click 'Logout' button.
      9. Assert visibility of Brand header.
      10. Assert visibility of 'Logout' button.
      11. Click 'Logout' button.
      12. Assert visibility of Welcome title button.
      13. Assert visibility of 'Login' button.
      14. Assert visibility of 'Create account' button.
      """)
  @Test
  public void createNoteTest() {
    Note note = dataGenerator.generateNewNote(
        NoteCategory.Home,
        false,
        25,
        100);
    HomePage homePage = new HomePage(page()).open();
    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.clickLogin();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);

    myNotesHomePage.header.assertHeaderForAuthenticatedUserIsVisible();
    AddNoteModal addNoteModal = myNotesHomePage.openAddNoteModal();
    addNoteModal.createNewNote(note);



  }
}
