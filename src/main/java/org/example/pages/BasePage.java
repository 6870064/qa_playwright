package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.utils.AppConfig;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;

public abstract class BasePage {
  protected final Page page;
  protected final String INPUT = "//input[@id='%s']";

  public BasePage(Page page) {
    this.page = page;
  }

  /**
   * Каждая страница указывает свой относительный путь
   */
  protected abstract String path();

  protected void waitForOpen(){
    //nothing by default
  }

  public <T extends BasePage> T open() {
    page.navigate(AppConfig.baseUrl() + path());
    return (T) this;
  }

  /* ====== БАЗОВЫЕ UI-ДЕЙСТВИЯ ====== */
  public void click(String locator) {
    page.locator(locator).click();
  }

  public void linkClick(String locator) {
    page.locator(locator).click();
  }

  public void fill(String locator, String value) {
    page.locator(locator).fill(value);
  }

  public void waitVisible(String locator) {
    page.locator(locator)
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }
}
