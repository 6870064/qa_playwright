package org.example.pages.practice;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.pages.BasePage;

public class WebInputsPage extends BasePage {
  private final Locator inputTextField = page.locator("//input[@id='input-text']");
  private final Locator inputPasswordField = page.locator("//input[@id='input-password']");
  private final Locator inputDateField = page.locator("//input[@id='input-date']");
  private final Locator displayInputsButton = page.locator("//button[@id='btn-display-inputs']");
  private final Locator clearInputsButton = page.locator("//button[@id='btn-clear-inputs']");
  private final Locator outputNumber = page.locator("//*[@id='output-number']");
  private final Locator outputText = page.locator("//*[@id='output-text']");
  private final Locator outputPassword = page.locator("//*[@id='output-password']");
  private final Locator outputDate = page.locator("//*[@id='output-date']");
  public Locator inputNumberField = page.locator("//input[@id='input-number']");

  public WebInputsPage(Page page) {
    super(page);
    this.inputNumberField = page.locator("//input[@id='input-number']");
  }

  @Override
  protected String path() {
    return "/inputs";
  }

  @Step("Fill input with {value}")
  public WebInputsPage fillInput(Locator locator, String value) {
    locator.fill(value);
    return this;
  }

  @Step("Fill input with with number {value}")
  public WebInputsPage inputNumber(String value) {
    inputNumberField.waitFor(new Locator.WaitForOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(5000));
    inputNumberField.fill(value);
    return this;
  }

  @Step("Fill input with {value}")
  public WebInputsPage inputText(String value) {
    inputTextField.fill(value);
    return this;
  }

  @Step("Fill input with password")
  public WebInputsPage inputPassword(String value) {
    inputPasswordField.fill(value);
    return this;
  }

  @Step("Fill input with {value}")
  public WebInputsPage inputDate(String value) {
    inputDateField.fill(value);
    return this;
  }

  @Step("Click 'Display input' button")
  public WebInputsPage displayInputsClick() {
    displayInputsButton.click();
    return this;
  }

  @Step("Click 'Clear input' button")
  public WebInputsPage clearInputsClick() {
    clearInputsButton.click();
    return this;
  }

  @Step("Get 'Output number'")
  public String getOutputNumber() {
    return outputNumber.textContent().trim();
  }

  @Step("Get 'Output text'")
  public String getOutputText() {
    return outputText.textContent().trim();
  }

  @Step("Get 'Output password'")
  public String getOutputPassword() {
    return outputPassword.textContent().trim();
  }

  @Step("Get 'Output date'")
  public String getOutputDate() {
    return outputDate.textContent().trim();
  }
}
