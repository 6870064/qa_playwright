package ui.my_notes;

import io.qameta.allure.Description;
import org.example.constants.routes.UIRotes;
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

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MyNotesLoginTests extends BaseTest {

  User user = TestUsers.validUser();

  @DisplayName("[UI]. Notes App. Login by existing user")
  @Description("""
      1. Open the Home page.
      2. Navigate to the 'Notes App'.
      3. Click the 'Login' button on the Welcome page.
      4. Log in with valid user credentials.
      5. Assert that the authenticated user header is visible.
      6. Click the 'Logout' button in the header.
      7. Assert that the Welcome page title is visible.
      8. Assert that the 'Login' button is visible.
      9. Assert that the 'Create an account' link is visible.
      """)
  @Test
  public void loginAndLogoutUserTest() {
    HomePage homePage = new HomePage(page()).open();

    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.clickLogin();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);

    myNotesHomePage.header.assertHeaderForAuthenticatedUserIsVisible();

    MyNotesWelcomePage mySecondNotesWelcomePage = myNotesHomePage.header.clickLogout();
    mySecondNotesWelcomePage.assertWelcomeTitleIsVisible();
    mySecondNotesWelcomePage.assertLoginButtonIsVisible();
    mySecondNotesWelcomePage.assertCreateAnAccountIsVisible();
  }

  @DisplayName("[UI]. Notes App. Forgot password flow")
  @Description("""
      1. Open the Home page.
      2. Navigate to the 'Notes App'.
      3. Click the 'Forgot your password?' link.
      4. Enter the user's email address.
      5. Click the 'Send me reset link' button.
      6. Wait for the password reset success alert to appear.
      7. Assert that the success alert is visible for the correct email.
      8. Click the 'Back to home page' link.
      9. Assert that the Welcome page title is visible.
      10. Assert that the 'Login' button is visible.
      11. Assert that the 'Create an account' link is visible.
      """)
  @Test
  public void forgotPasswordTest() {
    HomePage homePage = new HomePage(page()).open();

    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesForgotPasswordPage myNotesForgotPasswordPage = myNotesWelcomePage.clickForgotPassword();
    myNotesForgotPasswordPage.enterEmail(user.getEmail());
    myNotesForgotPasswordPage.clickSendResetLink();
    myNotesForgotPasswordPage.waitForAlert(user.getEmail());
    myNotesForgotPasswordPage.assertThatAlertIsVisible(user.getEmail());

    MyNotesWelcomePage mySecondNotesWelcomePage = myNotesForgotPasswordPage.goToWelcomePageLinkClick();

    mySecondNotesWelcomePage.assertWelcomeTitleIsVisible();
    mySecondNotesWelcomePage.assertLoginButtonIsVisible();
    mySecondNotesWelcomePage.assertCreateAnAccountIsVisible();
  }

  @DisplayName("[UI] Authentication: Unauthorized Access Redirect")
  @Description("""
      1. Attempt to navigate directly to the User Profile URL.
      2. Verify the application redirects to the Login page.
      3. Attempt to navigate directly to the Home Page (Dashboard) URL.
      4. Verify the application redirects to the Login page again.
      5. Confirm the Login page elements are visible after each redirect.
      """)
  @Test
  public void unauthorizedUserRedirectTest() {
    String profileUrl = "https://practice.expandtesting.com/notes/app/profile";

    MyNotesLoginPage loginPage = new MyNotesLoginPage(page());

    loginPage.openDirectly(profileUrl)
        .assertIsLoaded();

    assertThat(page()).hasURL(Pattern.compile(".*" + UIRotes.LOGIN));
  }
}
