package org.example.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FlashAlert {
  private final Locator root;

  public FlashAlert(Page page) {
    this.root = page.getByRole(AriaRole.ALERT);
  }

  public void shouldContain(String text) {
    assertThat(root).containsText(text);
  }

  public void shouldBeVisible() {
    assertThat(root).isVisible();
  }
}
