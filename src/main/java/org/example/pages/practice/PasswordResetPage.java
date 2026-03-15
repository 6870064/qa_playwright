package org.example.pages.practice;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.pages.BasePage;

public class PasswordResetPage extends BasePage {
  private final Locator infoMessage;

  public PasswordResetPage(Page page) {
    super(page);
    this.infoMessage = page.locator("#confirmation-alert");
  }

  @Override
  protected String path() {
    return "/forgot-password";
  }

  @Step("Validate that 'PasswordResetPage' is opened")
  public boolean isOpened() {
    infoMessage.waitFor();
    return infoMessage.isVisible();
  }
}
