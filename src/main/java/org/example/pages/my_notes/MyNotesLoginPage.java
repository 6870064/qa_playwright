package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.objects.User;
import org.example.pages.BasePage;

public class MyNotesLoginPage extends BasePage {
  public final Locator login = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Login"));

  public final Locator forgotPassword = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Forgot password"));

  public final Locator createAccount = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Create a free account!"));

  public final Locator profileDeleteAlert = page.getByTestId("alert-message");


  public MyNotesLoginPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return UIRotes.LOGIN;
  }

  @Step("Enter user's email: {email}")
  public void enterEmail(String email) {
    page.locator(String.format(INPUT, "email")).fill(email);
  }

  @Step("Enter user's password")
  public void enterPassword(String password) {
    page.locator(String.format(INPUT, "password")).fill(password);
  }

  @Step("Click 'Login' button")
  public MyNotesHomePage clickLogin() {
    login.click();
    page.waitForURL("**" + UIRotes.HOME);
    return new MyNotesHomePage(page);
  }

  public MyNotesHomePage loginUser(User user) {
    enterEmail(user.getEmail());
    enterPassword(user.getPassword());
    clickLogin();
    MyNotesHomePage myNotesHomePage = new MyNotesHomePage(page);
    return myNotesHomePage.waitForPageToLoad();
  }

  //TODO доделать восзрат новой страницы Forgot Password page
  @Step("Click 'Forgot Password' link")
  public void forgotPasswordLinkClick() {
    forgotPassword.click();
  }

  @Step("Click 'Create a free account' link")
  public MyNotesRegisterPage createAccountLinkClick() {
    createAccount.click();
    page.waitForURL("**" + UIRotes.REGISTER);
    return new MyNotesRegisterPage(page);
  }

  public void assertDeleteAlertIsVisible() {
    profileDeleteAlert.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));
  }
}
