package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.example.components.FlashAlert;

public class RegisterPage extends BasePage{
  private final Locator username = page.locator("//input[@name='username']");
  private final Locator password = page.locator("//input[@name='password']");
  private final Locator confirmPassword = page.locator("//input[@name='confirmPassword']");
  private final Locator submit = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Register"));

  public RegisterPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return "/register";
  }

  public RegisterPage fill(String u, String p, String c) {
    username.fill(u);
    password.fill(p);
    confirmPassword.fill(c);
    return this;
  }

  public LoginPage submitSuccess() {
    submit.click();
    return new LoginPage(page);
  }

  public FlashAlert submitExpectingError() {
    submit.click();
    return new FlashAlert(page);
  }
}
