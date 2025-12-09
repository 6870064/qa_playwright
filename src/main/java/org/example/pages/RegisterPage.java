package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.example.components.FlashAlert;
import org.example.requests.user.UiUser;

public class RegisterPage extends BasePage {
  private final FlashAlert flashAlert;
  private final Locator username = page.locator("//input[@name='username']");
  private final Locator password = page.locator("//input[@name='password']");
  private final Locator confirmPassword = page.locator("//input[@name='confirmPassword']");
  private final Locator submit = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Register"));

  public RegisterPage(Page page) {
    super(page);
    this.flashAlert = new FlashAlert(page);
  }

  @Override
  protected String path() {
    return "/register";
  }

  private void fillUserName(String u) {
    username.fill(u);
  }

  private void fillUserPassword(String p) {
    password.fill(p);
  }

  private void fillUserConfirmPassword(String p) {
    confirmPassword.fill(p);
  }

  private void registerClick() {
    submit.click();
  }

  public LoginPage registerNewUser(UiUser user) {
    fillUserName(user.getUsername());
    fillUserPassword(user.getPassword());
    fillUserConfirmPassword(user.getConfirmPassword());
    registerClick();
    return new LoginPage(page, new FlashAlert(page));
  }

  // негативный сценарий
  public RegisterPage tryToRegisterInvalidUser(UiUser user) {
    fillUserName(user.getUsername());
    fillUserPassword(user.getPassword());
    fillUserConfirmPassword(user.getConfirmPassword());
    registerClick();
    return this;
  }

  public FlashAlert flashAlert() {
    return flashAlert;
  }

  public FlashAlert submitExpectingError() {
    submit.click();
    return new FlashAlert(page);
  }
}
