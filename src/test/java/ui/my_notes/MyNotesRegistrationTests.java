package ui.my_notes;

import io.qameta.allure.Description;
import org.example.objects.User;
import org.example.pages.my_notes.MyNotesHomePage;
import org.example.pages.my_notes.MyNotesLoginPage;
import org.example.pages.my_notes.MyNotesRegisterPage;
import org.example.pages.my_notes.MyNotesWelcomePage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseTest;

public class MyNotesRegistrationTests extends BaseTest {

  @DisplayName("[UI]. Notes App. Register a new user")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Notes App | React' page.
      3. Click a 'Create an account' button.
      4. Click 'Click here to Log in' link.
      5. Enter user's email.
      6. Enter user's password.
      7. Click 'Login' button.
      8. Assert visibility header component.
      9. Click 'Logout' button.
      10. Assert visibility of Welcome title button.
      11. Assert visibility of 'Login' button.
      12. Assert visibility of 'Create account' button.
      """)
  @Test
  public void registerNewUserTest() {
    String password = dataGenerator.generateRandomPassword(8, 10);
    User user = new User(
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomName(5, 10),
        password,
        password);

    HomePage homePage = new HomePage(page()).open();
    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.createNewAccount();
    myNotesRegisterPage.registerNewUser(user);
    myNotesRegisterPage.waitForSuccess();
    myNotesRegisterPage.assertLoginLinkIsVisible();

    MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.loginLinkClick();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);
    myNotesHomePage.header.assertHeaderForAuthenticatedUserIsVisible();

    MyNotesWelcomePage secondMyNotesWelcomePage = myNotesHomePage.header.clickLogout();
    secondMyNotesWelcomePage.assertWelcomeTitleIsVisible();
    secondMyNotesWelcomePage.assertLoginButtonIsVisible();
    secondMyNotesWelcomePage.assertCreateAnAccountIsVisible();
  }
}
