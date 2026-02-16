package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

/**
 * Page object representing the "Forgot Password" page in the My Notes application.
 * Provides functionality to request a password reset link and navigate back to other pages.
 */
public class MyNotesForgotPasswordPage extends BasePage {
  private final Locator header = page.getByText("Reset your password");

  private final Locator emailInput = page.getByTestId("forgot-password-email");

  private final Locator sendResetLink = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Send me reset link"));

  private final Locator goToLogin = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Go back to login"));

  private final String MESSAGE_TEXT = "An e-mail with a link to reset the password has been sent to %s";

  private final Locator goToHomePage = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Click here to back to home page"));

  /**
   * Initializes the Forgot Password page and waits for it to be ready.
   *
   * @param page the Playwright Page instance
   */
  public MyNotesForgotPasswordPage(Page page) {
    super(page);
    waitForOpen();
  }

  /**
   * Returns the relative URL path for the Forgot Password page.
   *
   * @return the string path from UIRotes
   */
  @Override
  protected String path() {
    return UIRotes.FORGOT_PASSWORD;
  }

  /**
   * Waits for the page to load by verifying the visibility of the header.
   */
  @Override
  protected void waitForOpen() {
    header.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
    );
  }

  /**
   * Enters the user's email into the reset password input field.
   *
   * @param email the email address to receive the reset link
   */
  @Step("Enter email: {email}")
  public void enterEmail(String email) {
    emailInput.fill(email);
  }

  /**
   * Clicks the button to request a password reset link.
   *
   * @return the current instance of MyNotesForgotPasswordPage
   */
  @Step("Click 'Send me reset link' button")
  public MyNotesForgotPasswordPage clickSendResetLink() {
    sendResetLink.click();
    return this;
  }

  /**
   * Waits until the success alert message for the specific email is visible.
   *
   * @param email the email address that was used for the reset request
   */
  @Step("Wait until reset password success alert is visible for email: {email}")
  public void waitForAlert(String email) {
    page.getByText(String.format(MESSAGE_TEXT, email))
        .waitFor(new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));
  }

  /**
   * Verifies that the reset password success alert is visible.
   *
   * @param email the email address associated with the success message
   */
  @Step("Assert that reset password success alert is visible for email: {email}")
  public void assertThatAlertIsVisible(String email) {
    page.getByText(String.format(MESSAGE_TEXT, email)).waitFor();
  }

  /**
   * Navigates back to the Welcome page by clicking the home page link.
   *
   * @return a new instance of MyNotesWelcomePage
   */
  @Step("Click 'Click here to back to home page' link")
  public MyNotesWelcomePage goToWelcomePageLinkClick() {
    goToHomePage.click();
    return new MyNotesWelcomePage(page);
  }
}