import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.constants.Constants.*;
import static org.example.enums.PageInfo.LOGIN;
import static org.example.enums.PageInfo.WEB_INPUT;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTests extends BaseTest {

  @BeforeEach
  public void beforeEachTest(){
    linkClick(String.format(LINK_LOCATOR, LOGIN.linkTitle()));
    page
        .locator(String.format(PAGE_TITLE_LOCATOR, LOGIN.title() + PAGE_COMMON_TITLE))
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  @Test
  public void userLoginTest() {
    page.fill(String.format(INPUT_LOCATOR, "username"), USERNAME);
    page.fill(String.format(INPUT_LOCATOR, "password"), PASSWORD);
    page.click(LOGIN_BUTTON);

    page.waitForURL(BASE_URL + SECURE_URL);
    page.locator(String.format(GREETING_MESSAGE, USERNAME)).waitFor(new Locator.WaitForOptions().setState(VISIBLE));

    assertAll("Assert URL and UI elements",
        ()->assertEquals(BASE_URL + SECURE_URL, page.url(), "Login page is opened"),
        ()->assertTrue(page.locator(USERNAME_LOCATOR).isVisible()),
        ()->assertTrue(page.locator(LOGOUT_BUTTON).isVisible()));
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

    Boolean isLoginPageVisible = page
        .locator(String.format(PAGE_TITLE_LOCATOR, LOGIN.title() + PAGE_COMMON_TITLE))
        .isVisible();

    assertAll("Assert URL and text messages",
        ()->assertEquals(BASE_URL + LOGIN_URL, page.url(), "Login page is opened"),
        ()->assertTrue(isLoginPageVisible, "Login page text is opened"));
  }
}
