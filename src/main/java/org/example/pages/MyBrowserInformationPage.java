package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class MyBrowserInformationPage extends BasePage {

  private final Locator toggleButton;
  private final Locator infoTable;

  public MyBrowserInformationPage(Page page) {
    super(page);
    this.toggleButton = page.locator("#browser-toggle");
    this.infoTable = page.locator("table");
  }

  @Override
  protected String path() {
    return "/my-browser";
  }

  @Step("Click 'Show Browser Information' button")
  public void toggleButtonClick() {
    toggleButton.click();
  }

  @Step("Get a value by {label}")
  public Locator valueByLabel(String label) {
    return page.locator("tr", new Page.LocatorOptions().setHasText(label))
        .locator("td").nth(1);
  }

  @Step("Get a value by {label}")
  public String getValue(String label) {
    return valueByLabel(label).textContent().trim();
  }

  @Step("Check that info table is visible")
  public boolean isInfoVisible() {
    return infoTable.isVisible();
  }
}
