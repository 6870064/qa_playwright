package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.example.components.FlashAlert;

public class ForgotPasswordPage extends BasePage {
  private final FlashAlert flashAlert;
  private final Locator email = page.locator("//input[@name='email']");
  private final Locator submit = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Retrieve password"));

  private final Locator invalidEmailMessage =
      page.locator("//input[@id='email']/following-sibling::div[contains(@class,'invalid-feedback')]");

  public ForgotPasswordPage(Page page) {
    super(page);
    this.flashAlert = new FlashAlert(page);
  }

  @Override
  protected String path() {
    return "/forgot-password";
  }

  @Step("Fill email: {mail}")
  public void fillEmail(String mail) {
    email.fill(mail);
  }

  @Step("Click on 'Retrieve password' button")
  public void retrievePasswordClick() {
    submit.click();
  }

  @Step("Validate that message 'Please enter a valid email address.' is visible")
  public boolean isErrorVisible() {
    return invalidEmailMessage.isVisible();
  }
}
