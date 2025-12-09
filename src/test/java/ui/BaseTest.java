package ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Video;
import org.example.helpers.AdBlocker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Paths;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.constants.Constants.AUTOMATION_PRACTICE_TEXT;
import static org.example.constants.Constants.BASE_URL;

public abstract class BaseTest {
  private final Playwright playwright = Playwright.create();
  Browser browser;
  Page page;
  BrowserContext context;

  @BeforeEach
  public void beforeEach() {
    String isHeadlessString = System.getenv("HEADLESS");
    Boolean isHeadless = Boolean.parseBoolean(isHeadlessString);
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
    System.out.println(browser.version());
    context = browser.newContext(new Browser.NewContextOptions()
        .setViewportSize(1920, 1080)
        .setRecordVideoDir(Paths.get("./target/video")));

    context.route("**/*", route -> {
      String url = route.request().url();

      if (url.contains("doubleclick") ||
          url.contains("googlesyndication") ||
          url.contains("adservice") ||
          url.contains("googleads") ||
          url.contains("g.doubleclick.net")) {

        System.out.println("🚫 Prevented AD request: " + url);
        route.abort();
      } else {
        route.resume();
      }
    });

    page = context.newPage();
    AdBlocker.killInterstitialAds(page);

    navigateToPageUrl(BASE_URL);
    page.locator(AUTOMATION_PRACTICE_TEXT).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  @AfterEach
  public void afterEach() {
    Video video = page.video();
    context.close();
    browser.close();
    playwright.close();
  }

  public void navigateToPageUrl(String url) {
    page.navigate(url);
  }

  public void linkClick(String linkTitle) {
    page.locator(linkTitle).click();
  }
}
