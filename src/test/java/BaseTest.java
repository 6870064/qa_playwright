import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Video;
import org.example.utils.PropertyReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseTest {
  private final Playwright playwright = Playwright.create();
  protected PropertyReader propertyReader = new PropertyReader("src/main/resources/configuration.properties");
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
    page = context.newPage();

  }

  @AfterEach
  public void afterEach() {
    Video video = page.video();
    context.close();
  }

  public void navigateToPageUrl(String url) {
    page.navigate(url);
  }

  public void linkClick(String linkTitle) {
    page.locator(linkTitle).click();
  }
}
