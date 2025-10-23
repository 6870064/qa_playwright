package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class WebInputsPage extends BasePage {
  public final Locator inputNumberField = page.locator("//input[@id='input-number']");
  private final Locator inputTextField = page.locator("//input[@id='input-text']");
  private final Locator inputPasswordField = page.locator("//input[@id='input-password']");
  private final Locator inputDateField = page.locator("//input[@id='input-date']");
  private final Locator displayInputsButton = page.locator("//button[@id='btn-display-inputs']");
  private final Locator clearInputsButton = page.locator("//button[@id='btn-clear-inputs']");
  private final Locator outputNumber = page.locator("//*[@id='output-number']");
  private final Locator outputText = page.locator("//*[@id='output-text']");
  private final Locator outputPassword = page.locator("//*[@id='output-password']");
  private final Locator outputDate = page.locator("//*[@id='output-date']");

  public WebInputsPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return "/inputs";
  }

  public WebInputsPage fillInput(Locator locator, String value) {
    locator.fill(value);
    return this;
  }

  public WebInputsPage inputNumber(String value) {
    inputNumberField.fill(value);
    return this;
  }

  public WebInputsPage inputText(String value) {
    inputTextField.fill(value);
    return this;
  }

  public WebInputsPage inputPassword(String value) {
    inputPasswordField.fill(value);
    return this;
  }

  public WebInputsPage inputDate(String value) {
    inputDateField.fill(value);
    return this;
  }

  public WebInputsPage displayInputsClick() {
    displayInputsButton.click();
    return this;
  }

  public WebInputsPage clearInputsClick() {
    clearInputsButton.click();
    return this;
  }

  public WebInputsPage goToWebInputs() {
    page.getByRole(AriaRole.LINK,
        new Page.GetByRoleOptions().setName("Web inputs")).click();
    return new WebInputsPage(page);
  }

  public String getOutputNumber() {
    return outputNumber.textContent().trim();
  }

  public String getOutputText() {
    return outputText.textContent().trim();
  }

  public String getOutputPassword() {
    return outputPassword.textContent().trim();
  }

  public String getOutputDate() {
    return outputDate.textContent().trim();
  }
}
