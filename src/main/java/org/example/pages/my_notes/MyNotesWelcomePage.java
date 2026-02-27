package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

/**
 * Page object representing the initial Welcome screen of the My Notes application.
 * This page serves as the entry point for unauthenticated users, providing navigation
 * to Login, Registration, and Password Recovery.
 */
public class MyNotesWelcomePage extends BasePage {
  private final Locator welcomeTitle = page.getByRole(AriaRole.HEADING,
      new Page.GetByRoleOptions().setName("Welcome to Notes App"));

  private final Locator login = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Login"));

  private final Locator createAnAccount = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Create an account"));

  private final Locator forgotPassword = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Forgot your password?"));

  /**
   * Initializes the Welcome page and waits for it to be fully loaded.
   *
   * @param page the Playwright Page instance
   */
  public MyNotesWelcomePage(Page page) {
    super(page);
    waitForOpen();
  }

  /**
   * Returns the relative URL path for the Welcome page.
   *
   * @return the home route string
   */
  @Override
  protected String path() {
    return UIRotes.HOME;
  }

  /**
   * Defensive wait to ensure the Welcome title is visible before interacting with the page.
   */
  @Override
  protected void waitForOpen() {
    welcomeTitle.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
    );
  }

  /**
   * Navigates to the Login page.
   *
   * @return a new instance of MyNotesLoginPage
   */
  @Step("Click 'Login' button")
  public MyNotesLoginPage clickLogin() {
    login.click();
    page.waitForURL("**" + UIRotes.LOGIN);
    return new MyNotesLoginPage(page);
  }

  /**
   * Navigates to the Registration page.
   *
   * @return a new instance of MyNotesRegisterPage
   */
  @Step("Click 'Create an account' button")
  public MyNotesRegisterPage createNewAccount() {
    createAnAccount.click();
    page.waitForURL("**" + UIRotes.REGISTER);
    return new MyNotesRegisterPage(page);
  }

  /**
   * Navigates to the Forgot Password page.
   *
   * @return a new instance of MyNotesForgotPasswordPage
   */
  @Step("Click 'Forgot your password' link")
  public MyNotesForgotPasswordPage clickForgotPassword() {
    forgotPassword.click();
    page.waitForURL("**" + UIRotes.FORGOT_PASSWORD);
    return new MyNotesForgotPasswordPage(page);
  }

  /**
   * Asserts that the Welcome heading is displayed.
   */
  @Step("Assert that Welcome title is visible")
  public void assertWelcomeTitleIsVisible() {
    welcomeTitle.waitFor();
    welcomeTitle.isVisible();
  }

  /**
   * Asserts that the Login navigation link is available.
   */
  @Step("Assert that 'Login' button is visible")
  public void assertLoginButtonIsVisible() {
    login.waitFor();
  }

  /**
   * Asserts that the Account Creation link is available.
   */
  @Step("Assert that 'Create an account' link is visible")
  public void assertCreateAnAccountIsVisible() {
    createAnAccount.waitFor();
  }
}