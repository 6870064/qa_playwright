package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.objects.User;
import org.example.pages.BasePage;

public class MyNotesRegisterPage extends BasePage {
  private final Locator register = page.locator("//button[@data-testid='register-submit']");
  private final Locator successMessage = page
      .locator("//div[@class= 'alert alert-success']//b['User account created successfully']");
  private final Locator loginLink = page.locator("//a[@data-testid='login-view']");

  public MyNotesRegisterPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return UIRotes.REGISTER;
  }

  @Step("Enter an email {email}")
  public void enterEmail(String email) {
    page.locator(String.format(INPUT, "email")).fill(email);
  }

  @Step("Enter a name {name")
  public void enterName(String name) {
    page.locator(String.format(INPUT, "name")).fill(name);
  }

  @Step("Enter a password")
  public void enterPassword(String password) {
    page.locator(String.format(INPUT, "password")).fill(password);
  }

  @Step("Enter a confirm password")
  public void enterConfirmPassword(String confirmPassword) {
    page.locator(String.format(INPUT, "confirmPassword")).fill(confirmPassword);
  }

  @Step("Click a register button")
  public MyNotesRegisterPage clickRegister() {
    register.click();
    return this;
  }

  public void registerNewUser(User user) {
    enterEmail(user.getEmail());
    enterName(user.getName());
    enterPassword(user.getPassword());
    enterConfirmPassword(user.getConfirmPassword());
    clickRegister();
  }

  @Step("Wait for success alert")
  public MyNotesRegisterPage waitForSuccess() {
    successMessage.waitFor();
    return this;
  }

  @Step("Assert that 'Click here to log in' is visible")
  public boolean isLoginLinkIsVisible() {
    return loginLink.isVisible();
  }

  @Step("Click login link")
  public MyNotesLoginPage loginLinkClick() {
    loginLink.click();
    page.waitForURL("**" + UIRotes.LOGIN);
    return new MyNotesLoginPage(page);
  }


}
