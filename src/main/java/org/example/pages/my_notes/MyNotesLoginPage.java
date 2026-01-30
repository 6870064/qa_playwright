package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.objects.User;
import org.example.pages.BasePage;

public class MyNotesLoginPage extends BasePage {
  public final Locator login = page.locator("//button[@data-testid='login-submit']");
  public final Locator forgotPassword = page.locator("//a[@id='forgotPasswordLink']");
  public final Locator createAccount = page.locator("//a[@data-testid='register-view']");

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
  public MyNotesHomePage clickLogin(){
    login.click();
    page.waitForURL("**" + UIRotes.HOME);
    return new MyNotesHomePage(page);
  }

  public MyNotesHomePage loginUser(User user) {
    enterEmail(user.getEmail());
    enterPassword(user.getPassword());
    return clickLogin();
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



}
