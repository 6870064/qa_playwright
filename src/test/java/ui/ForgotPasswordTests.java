package ui;

import io.qameta.allure.Description;
import org.example.pages.practice.ForgotPasswordPage;
import org.example.pages.practice.HomePage;
import org.example.pages.practice.PasswordResetPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForgotPasswordTests extends BaseTest {

  @DisplayName("[UI]. Forgot Password Form. Validate possibility to retrieve password")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Forgot Password Form'.
      3. Enter a valid email to retrieve.
      4. Click 'Retrieve password' button.
      5. Assert visibility of the message 'An e-mail has been sent to you which explains how to reset your password.'
      """)
  @Test
  public void forgotPasswordTest() {
    HomePage homePage = new HomePage(page()).open();
    ForgotPasswordPage forgotPasswordPage = homePage.goToForgotPassword();

    forgotPasswordPage.fillEmail(dataGenerator.generateRandomEmail(true));
    forgotPasswordPage.retrievePasswordClick();
    assertTrue(new PasswordResetPage(page()).isOpened());
  }

  @DisplayName("[UI]. Forgot Password Form. Validate possibility to retrieve password by invalid Email")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Forgot Password Form'.
      3. Enter an invalid email to retrieve.
      4. Click 'Retrieve password' button.
      5. Assert visibility of the message 'Please enter a valid email address.'
      """)
  @Test
  public void forgotPasswordByInvalidEmailTest() {
    HomePage homePage = new HomePage(page()).open();
    ForgotPasswordPage forgotPasswordPage = homePage.goToForgotPassword();

    forgotPasswordPage.fillEmail(dataGenerator.generateRandomEmail(false));
    forgotPasswordPage.retrievePasswordClick();
    assertTrue(forgotPasswordPage.isErrorVisible());
  }

  @DisplayName("[UI]. Forgot Password Form. Validate possibility to retrieve password without Email")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Forgot Password Form'.
      3. Click 'Retrieve password' button.
      4. Assert visibility of the message 'Please enter a valid email address.'
      """)
  @Test
  public void forgotPasswordWithoutEmailTest() {
    HomePage homePage = new HomePage(page()).open();
    ForgotPasswordPage forgotPasswordPage = homePage.goToForgotPassword();

    forgotPasswordPage.retrievePasswordClick();
    assertTrue(forgotPasswordPage.isErrorVisible());
  }
}
