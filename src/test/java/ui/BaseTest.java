package ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.example.helpers.AdBlocker;
import org.example.helpers.DataGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.constants.Constants.AUTOMATION_PRACTICE_TEXT;
import static org.example.constants.Constants.BASE_URL;

/**
 * Base test class for UI automation using Playwright.
 * Manages the lifecycle of the browser, context, and page instances using ThreadLocal
 * to ensure thread safety during parallel execution.
 */
public abstract class BaseTest {
  private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
  private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
  private static final ThreadLocal<Page> page = new ThreadLocal<>();
  private final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
  protected DataGenerator dataGenerator = new DataGenerator();

  /**
   * Sets up the browser environment before each test.
   * Configures headless mode, viewport size, video recording, and network routing to block ads.
   */
  @BeforeEach
  @Step("Set up browser environment and navigate to base URL")
  public void beforeEach() {
    Boolean isHeadless = Boolean.parseBoolean(
        System.getenv().getOrDefault("HEADLESS", "false")
    );

    Playwright pw = Playwright.create();
    Browser br = pw
        .chromium()
        .launch(new BrowserType.LaunchOptions()
            .setHeadless(isHeadless));

    BrowserContext ctx = br.newContext(new Browser.NewContextOptions()
        .setViewportSize(2560, 1440)
        .setRecordVideoDir(Paths.get("target/video"))
        .setRecordVideoSize(2560, 1440));

    ctx.route("**/*", route -> {
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

    Page pg = ctx.newPage();
    playwright.set(pw);
    browser.set(br);
    context.set(ctx);
    page.set(pg);
    AdBlocker.killInterstitialAds(pg);

    pg.navigate(BASE_URL);
    pg.locator(AUTOMATION_PRACTICE_TEXT)
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  /**
   * Cleans up browser resources after each test.
   * Captures the execution video, attaches it to the Allure report, and closes the browser context.
   */
  @AfterEach
  @Step("Tear down browser environment and attach video")
  public void afterEach() {
    // 1. Capture the path before closing the context
    Path videoPath = null;
    try {
      if (page.get() != null && page.get().video() != null) {
        videoPath = page.get().video().path();
      }
    } catch (Exception e) {
      System.err.println(">>> Failed to get video path: " + e.getMessage());
    }

    // 2. Close resources safely in reverse order
    try {
      if (context.get() != null) {
        context.get().close();
      }
      if (browser.get() != null) {
        browser.get().close();
      }
      if (playwright.get() != null) {
        playwright.get().close();
      }
    } catch (Exception e) {
      // Catching "connection closed" errors during cleanup to prevent test failure logs
      System.err.println(">>> Error during resource cleanup: " + e.getMessage());
    }

    // 3. Attach video to Allure if file exists after context is closed
    if (videoPath != null && Files.exists(videoPath)) {
      try (InputStream is = Files.newInputStream(videoPath)) {
        Allure.addAttachment("Execution Video", "video/webm", is, ".webm");
      } catch (IOException e) {
        System.err.println(">>> FAILED TO ATTACH VIDEO: " + e.getMessage());
      }
    }

    // 4. Always clear ThreadLocal variables
    page.remove();
    context.remove();
    browser.remove();
    playwright.remove();
  }

  /**
   * Provides access to the current thread's Page instance.
   *
   * @return the Playwright Page instance
   */
  protected Page page() {
    return page.get();
  }
}