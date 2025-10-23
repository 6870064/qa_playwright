package org.example.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage extends BasePage {
  public HomePage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return "/";
  }

  public RegisterPage goToRegister() {
    page.getByRole(AriaRole.LINK,
        new Page.GetByRoleOptions().setName("Test Register page")).click();
    return new RegisterPage(page);
  }
 }
