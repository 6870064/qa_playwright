package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesWelcomePage extends BasePage {
  private final Locator welcomeTitle = page.getByRole(AriaRole.HEADING,
      new Page.GetByRoleOptions().setName("Welcome to Notes App"));

  private final Locator login = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Login"));

  private final Locator createAnAccount = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Create an account"));

  private final Locator forgotPassword = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Forgot your password?"));

  public MyNotesWelcomePage(Page page) {
    super(page);
    waitForOpen();
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
  }

  @Override
  protected void waitForOpen() {
    welcomeTitle.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
    );
  }

  @Step("Click 'Login' button")
  public MyNotesLoginPage clickLogin(){
    login.click();
    page.waitForURL("**" + UIRotes.LOGIN);
    return new MyNotesLoginPage(page);
  }

  @Step("Click 'Create an account' button")
  public MyNotesRegisterPage createNewAccount() {
    createAnAccount.click();
    page.waitForURL("**" + UIRotes.REGISTER);
    return new MyNotesRegisterPage(page);
  }

  @Step("Click 'Forgot your password' link")
  public MyNotesForgotPasswordPage clickForgotPassword() {
    forgotPassword.click();
    page.waitForURL("**" + UIRotes.FORGOT_PASSWORD);
    return new MyNotesForgotPasswordPage(page);
  }

  @Step("Assert that Welcome title is visible")
  public void assertWelcomeTitleIsVisible() {
    welcomeTitle.waitFor();
    welcomeTitle.isVisible();
  }

  @Step("Assert that 'Login' button is visible")
  public void assertLoginButtonIsVisible() {
    login.waitFor();
  }

  @Step("Assert that 'Create an account' link is visible")
  public void assertCreateAnAccountIsVisible() {
    createAnAccount.waitFor();
  }
}
