package ui.my_notes;

import io.qameta.allure.Description;
import org.example.objects.User;
import org.example.pages.my_notes.MyNotesForgotPasswordPage;
import org.example.pages.my_notes.MyNotesHomePage;
import org.example.pages.my_notes.MyNotesLoginPage;
import org.example.pages.my_notes.MyNotesWelcomePage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testdata.TestUsers;
import ui.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyNotesLoginTests extends BaseTest {

  User user = TestUsers.validUser();

  @DisplayName("[UI]. Notes App. Login by existing user")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Notes App | React' page.
      3. Click a 'Login' button.
      4. Enter a valid user's Email.
      5. Enter a valid user's password.
      6. Click 'Login' button.
      7. Assert visibility of 'MyNotes' charter.
      8. Assert visibility of 'Logout' button.
      9. Click 'Logout' button.
      10. Assert visibility of Brand header.
      11. Assert visibility of 'Logout' button.
      12. Click 'Logout' button.
      13. Assert visibility of Welcome title button.
      14. Assert visibility of 'Login' button.
      15. Assert visibility of 'Create account' button.
      """)
  @Test
  public void loginUserTest() {
    HomePage homePage = new HomePage(page()).open();

    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.clickLogin();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);

    assertTrue(myNotesHomePage.header.isHeaderVisibleForAuthenticatedUser());

    MyNotesWelcomePage mySecondNotesWelcomePage = myNotesHomePage.header.clickLogout();
    assertTrue(mySecondNotesWelcomePage.isWelcomeTitleVisible(), "Welcome title is not visible");
    assertTrue(mySecondNotesWelcomePage.isLoginButtonVisible(), "'Login' button is not visible");
    assertTrue(mySecondNotesWelcomePage.isCreateAnAccountVisible(), "'Create account' button is not visible");
  }

  @DisplayName("[UI]. Notes App. Login by existing user")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Notes App | React' page.
      3. Click a 'Login' button.
      4. Enter a valid user's Email.
      5. Enter a valid user's password.
      6. Click 'Login' button.
      7. Assert visibility of 'MyNotes' charter.
      8. Assert visibility of 'Logout' button.
      9. Click 'Logout' button.
      10. Assert visibility of Brand header.
      11. Assert visibility of 'Logout' button.
      12. Click 'Logout' button.
      13. Assert visibility of Welcome title button.
      14. Assert visibility of 'Login' button.
      15. Assert visibility of 'Create account' button.
      """)
  @Test
  public void forgotPasswordTest() {
    HomePage homePage = new HomePage(page()).open();

    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesForgotPasswordPage myNotesForgotPasswordPage = myNotesWelcomePage.clickForgotPassword();
    myNotesForgotPasswordPage.enterEmail(user.getEmail());
    myNotesForgotPasswordPage.clickSendResetLink();
    myNotesForgotPasswordPage.waitForAlert(user.getEmail());
    assertTrue(myNotesForgotPasswordPage.isAlertVisible(user.getEmail()));

    MyNotesWelcomePage mySecondNotesWelcomePage = myNotesForgotPasswordPage.goToWelcomePageLinkClick();

    assertTrue(mySecondNotesWelcomePage.isWelcomeTitleVisible(), "Welcome title is not visible");
    assertTrue(mySecondNotesWelcomePage.isLoginButtonVisible(), "'Login' button is not visible");
    assertTrue(mySecondNotesWelcomePage.isCreateAnAccountVisible(), "'Create account' button is not visible");
  }
}
