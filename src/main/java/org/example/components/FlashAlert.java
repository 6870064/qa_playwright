package org.example.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Component representing the Flash Alert notification on the page.
 * Used for verifying system messages such as success or error alerts.
 */
public class FlashAlert {
  private final Locator root;

  /**
   * Initializes the FlashAlert component.
   *
   * @param page the Playwright Page instance to locate the element
   */
  public FlashAlert(Page page) {
    this.root = page.locator("#flash");
  }

  /**
   * Asserts that the flash alert is visible on the page.
   */
  @Step("Verify that flash alert is visible")
  public void shouldBeVisible() {
    assertThat(root).isVisible();
  }

  /**
   * Asserts that the flash alert contains the specified text.
   *
   * @param text the expected text to be found within the alert
   */
  @Step("Verify that flash alert contains text: '{text}'")
  public void shouldContain(String text) {
    assertThat(root).containsText(text);
  }

  /**
   * Retrieves the inner text of the flash alert.
   *
   * @return the text content of the notification
   */
  @Step("Get flash alert text")
  public String text() {
    return root.innerText();
  }
}