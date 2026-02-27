package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.utils.AppConfig;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;

/**
 * Abstract base class for all Page Objects.
 * Provides core Playwright Page instance management and shared UI interaction methods.
 */
public abstract class BasePage {
  protected final Page page;
  protected final String INPUT = "//input[@id='%s']";

  /**
   * Initializes the BasePage with the Playwright Page instance.
   *
   * @param page the Playwright Page instance to be used by all extending pages
   */
  public BasePage(Page page) {
    this.page = page;
  }

  /**
   * Defines the relative URL path for the specific page.
   *
   * @return the relative path string
   */
  protected abstract String path();

  /**
   * Optional hook to wait for specific page elements to be ready.
   * Can be overridden by child classes to implement page-load synchronization.
   */
  protected void waitForOpen() {
    //nothing by default
  }

  /**
   * Navigates to the page using the base URL and the defined path.
   *
   * @param <T> the type of the page extending BasePage
   * @return the current page instance
   */
  @Step("Open the page")
  public <T extends BasePage> T open() {
    page.navigate(AppConfig.baseUrl() + path());
    return (T) this;
  }

  /* ====== BASE UI ACTIONS ====== */

  /**
   * Performs a click action on a given locator.
   *
   * @param locator the string selector for the element
   */
  @Step("Click on element: {locator}")
  public void click(String locator) {
    page.locator(locator).click();
  }

  /**
   * Performs a click action specifically on a link or navigation element.
   *
   * @param locator the string selector for the link
   */
  @Step("Click on link: {locator}")
  public void linkClick(String locator) {
    page.locator(locator).click();
  }

  /**
   * Fills an input field with the specified value.
   *
   * @param locator the string selector for the input field
   * @param value   the text to enter
   */
  @Step("Fill field '{locator}' with value: {value}")
  public void fill(String locator, String value) {
    page.locator(locator).fill(value);
  }

  /**
   * Waits until the element specified by the locator becomes visible in the DOM.
   *
   * @param locator the string selector for the element
   */
  @Step("Wait for element to be visible: {locator}")
  public void waitVisible(String locator) {
    page.locator(locator)
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }
}