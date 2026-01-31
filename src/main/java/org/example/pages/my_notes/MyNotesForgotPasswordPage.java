package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

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

  public MyNotesForgotPasswordPage(Page page) {
    super(page);
    waitForOpen();
  }

  @Override
  protected String path() {
    return UIRotes.FORGOT_PASSWORD;
  }

  @Override
  protected void waitForOpen() {
    header.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
    );
  }

  @Step("Enter email: {email}")
  public void enterEmail(String email) {
    emailInput.fill(email);
  }

  @Step("Click 'Send me reset link' link")
  public MyNotesForgotPasswordPage clickSendResetLink() {
    sendResetLink.click();
    return this;
  }

  @Step("Wait until reset password success alert is visible")
  public void waitForAlert(String email) {
    page.getByText(String.format(MESSAGE_TEXT, email))
        .waitFor(new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));
  }

  @Step("Check reset password success alert is visible")
  public boolean isAlertVisible(String email) {
    return page.getByText(String.format(MESSAGE_TEXT, email)).isVisible();
  }

  @Step("Click 'Click here to back to home page' link")
  public MyNotesWelcomePage goToWelcomePageLinkClick() {
    goToHomePage.click();
    return new MyNotesWelcomePage(page);
  }
}
