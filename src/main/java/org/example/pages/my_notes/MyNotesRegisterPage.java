package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.objects.User;
import org.example.pages.BasePage;

/**
 * Page object representing the Registration page of the My Notes application.
 * Provides methods for user sign-up, handling registration inputs, and navigating to login.
 */
public class MyNotesRegisterPage extends BasePage {
  private final Locator register = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Register"));

  private final Locator successMessage = page.getByText("User account created successfully");

  private final Locator loginLink = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Click here to Log In"));

  /**
   * Initializes the Register page.
   *
   * @param page the Playwright Page instance
   */
  public MyNotesRegisterPage(Page page) {
    super(page);
  }

  /**
   * Returns the relative URL path for the registration page.
   *
   * @return the registration route string
   */
  @Override
  protected String path() {
    return UIRotes.REGISTER;
  }

  /**
   * Fills the email input field.
   *
   * @param email the user's email address
   */
  @Step("Enter an email {email}")
  public void enterEmail(String email) {
    page.locator(String.format(INPUT, "email")).fill(email);
  }

  /**
   * Fills the name input field.
   *
   * @param name the user's full name
   */
  @Step("Enter a name {name}")
  public void enterName(String name) {
    page.locator(String.format(INPUT, "name")).fill(name);
  }

  /**
   * Fills the password input field.
   *
   * @param password the chosen password
   */
  @Step("Enter a password")
  public void enterPassword(String password) {
    page.locator(String.format(INPUT, "password")).fill(password);
  }

  /**
   * Fills the password confirmation input field.
   *
   * @param confirmPassword the password to confirm
   */
  @Step("Enter a confirm password")
  public void enterConfirmPassword(String confirmPassword) {
    page.locator(String.format(INPUT, "confirmPassword")).fill(confirmPassword);
  }

  /**
   * Clicks the Register button to submit the form.
   *
   * @return the current instance of MyNotesRegisterPage
   */
  @Step("Click a register button")
  public MyNotesRegisterPage clickRegister() {
    register.click();
    return this;
  }

  /**
   * Performs a full registration flow using a User object.
   *
   * @param user the User object containing registration data
   */
  @Step("Register a new user: {user.email}")
  public void registerNewUser(User user) {
    enterEmail(user.getEmail());
    enterName(user.getName());
    enterPassword(user.getPassword());
    enterConfirmPassword(user.getConfirmPassword());
    clickRegister();
  }

  /**
   * Waits for the success message to appear after registration.
   *
   * @return the current instance of MyNotesRegisterPage
   */
  @Step("Wait for success alert")
  public MyNotesRegisterPage waitForSuccess() {
    successMessage.waitFor();
    return this;
  }

  /**
   * Asserts that the link to the login page is visible on the screen.
   */
  @Step("Assert that 'Click here to log in' is visible")
  public void assertLoginLinkIsVisible() {
    loginLink.waitFor();
  }

  /**
   * Clicks the login link and waits for redirection to the login page.
   *
   * @return a new instance of MyNotesLoginPage
   */
  @Step("Click login link")
  public MyNotesLoginPage loginLinkClick() {
    loginLink.click();
    page.waitForURL("**" + UIRotes.LOGIN);
    return new MyNotesLoginPage(page);
  }
}