import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests extends BaseTest {

  @BeforeEach
  public void beforeEachTest(){
    linkClick(String.format(LINK_LOCATOR, "Test Login Page"));
    page
        .locator(String.format(TEXT_LOCATOR, "Login page for Automation Testing Practice"))
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  @Test
  public void userLoginTest() {
    page.fill(String.format(INPUT_LOCATOR, "username"), USERNAME);
    page.fill(String.format(INPUT_LOCATOR, "password"), PASSWORD);
    page.click(LOGIN_BUTTON);

    page.waitForURL(BASE_URL + SECURE_URL);
    assertEquals(BASE_URL + SECURE_URL, page.url(), "Login page is opened");

    page.locator(String.format(GREETING_MESSAGE, USERNAME)).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
    assertTrue(page.locator(USERNAME_LOCATOR).isVisible());
    assertTrue(page.locator(LOGOUT_BUTTON).isVisible());
  }

  @Test
  public void userLogoutTest() {
    page.fill(String.format(INPUT_LOCATOR, "username"), USERNAME);
    page.fill(String.format(INPUT_LOCATOR, "password"), PASSWORD);
    page.click(LOGIN_BUTTON);

    page.waitForURL(BASE_URL + SECURE_URL);
    assertEquals(BASE_URL + SECURE_URL, page.url(), "Login page is opened");

    page.locator(String.format(GREETING_MESSAGE, USERNAME.toLowerCase())).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
    assertTrue(page.locator(USERNAME_LOCATOR).isVisible());
    assertTrue(page.locator(LOGOUT_BUTTON).isVisible());

    page.click(LOGOUT_BUTTON);
    page.waitForURL(BASE_URL + LOGIN_URL);
    assertEquals(BASE_URL + LOGIN_URL, page.url(), "Login page for Automation Testing Practise");


    Boolean isLoginPageVisible = page
        .locator(String.format(TEXT_LOCATOR, "Login page for Automation Testing Practice"))
        .isVisible();

    assertTrue(isLoginPageVisible, "Login page text is not visible after registration");
  }
}
