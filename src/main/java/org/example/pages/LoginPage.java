package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.example.components.FlashAlert;

public class LoginPage extends BasePage{
  private final Locator username = page.locator("//input[@name='username']");
  private final Locator password = page.locator("//input[@name='password']");
  private final Locator submit = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Login"));

  public LoginPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return "/login";
  }

  public LoginPage fill(String u, String p) {
    username.fill(u);
    password.fill(p);
    return this;
  }

  public SecurePage login() {
    submit.click();
    return new SecurePage(page, new FlashAlert(page));
  }
}
