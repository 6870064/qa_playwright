package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;


public class MyBrowserInformationPage extends BasePage {

  public MyBrowserInformationPage(Page page) {
    super(page);
  }

  private final Locator showInfoButton = page.locator("//button[@id='browser-toggle']");

  @Override
  protected String path() {
    return "/my-browser";
  }

  @Step("Click 'Show Browser Information' button")
  public void showBrowserInfoButtonClick() {
    showInfoButton.click();
  }

  public Locator valueByLabel(String label) {
    return page.locator("tr", new Page.LocatorOptions().setHasText(label))
        .locator("td").nth(1);
  }

  public String getValue(String label) {
    return valueByLabel(label).textContent().trim();
  }
}
