package org.example.helpers;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

/**
 * Helper class designed to manage and remove unwanted advertisements during test execution.
 * Specifically targets intrusive interstitial ads that can obstruct UI elements.
 */
public class AdBlocker {

  /**
   * Injects a JavaScript MutationObserver into the page to detect and remove
   * interstitial ads (like Google Vignettes) as soon as they appear in the DOM.
   *
   * @param page the Playwright Page instance where the script will be injected
   */
  @Step("Inject ad-blocking script to remove interstitial ads")
  public static void killInterstitialAds(Page page) {
    page.addInitScript("""
        const observer = new MutationObserver(() => {
          const ad = document.querySelector("#google_vignette, #adtech_redirect");
          if (ad) ad.remove();
        });
        observer.observe(document.documentElement, { childList: true, subtree: true });
    """);
  }
}