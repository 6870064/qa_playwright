import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests extends BaseTest {
  public static final String BASE_URL = "https://practice.expandtesting.com/";
  private static final String AUTOMATION_PRACTICE_TEXT = "//h1[contains(normalize-space(.),'Automation Testing Practice WebSite')]";
  private static final String LOGIN_PAGE_LINK = "//a[normalize-space(text())='Test Login Page']";
  private static final String LOGIN_PAGE_TEXT = "//h1[contains(normalize-space(.), 'Login page for Automation Testing Practice')]";
  private static final String USERNAME_FIELD = "//input[@id='username']";
  private static final String PASSWORD_FIELD = "//input[@id='password']";
  private static final String USERNAME = "practice";
  private static final String PASSWORD = "SuperSecretPassword!";
  private static final String LOGIN_BUTTON = "//button[@id='submit-login']";
  private static final String GREETING_MESSAGE = "//h3[@id='username' and normalize-space()='Hi, %s!']";

  @BeforeEach
  public void beforeEachTest() {
    navigateToPageUrl(BASE_URL);
    page.locator(AUTOMATION_PRACTICE_TEXT).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }


  @Test
  public void userLoginTest() {
    linkClick(LOGIN_PAGE_LINK);
    page.locator(LOGIN_PAGE_TEXT).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
    page.fill(USERNAME_FIELD, USERNAME);
    page.fill(PASSWORD_FIELD, PASSWORD);
    page.click(LOGIN_BUTTON);

    page.waitForURL("https://practice.expandtesting.com/secure");

    page.locator(String.format(GREETING_MESSAGE, USERNAME)).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
    assertTrue(page.locator("#username").isVisible());
    assertTrue(page.locator("a:has-text('Logout')").isVisible());
  }

  @Test
  public void userLogoutTest() {
    linkClick(LOGIN_PAGE_LINK);
    page.locator(LOGIN_PAGE_TEXT).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
    page.fill(USERNAME_FIELD, USERNAME);
    page.fill(PASSWORD_FIELD, PASSWORD);
    page.click(LOGIN_BUTTON);

    page.waitForURL("https://practice.expandtesting.com/secure");

    page.locator(String.format(GREETING_MESSAGE, USERNAME)).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
    assertTrue(page.locator("#username").isVisible());
    assertTrue(page.locator("a:has-text('Logout')").isVisible());

    page.click("a:has-text('Logout')");
    page.locator(LOGIN_PAGE_TEXT).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }
}
