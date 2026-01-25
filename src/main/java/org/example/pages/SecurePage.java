package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.example.components.FlashAlert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.constants.Constants.BASE_URL;
import static org.example.constants.Constants.SECURE_URL;

public class SecurePage extends BasePage {
  public static final String GREETING_MESSAGE = "//h3[@id='username' and normalize-space()='Hi, %s!']";
  private final FlashAlert flashAlert;
  private final Locator logout = page.getByRole(
      AriaRole.LINK, new Page.GetByRoleOptions().setName("Logout"));

  public SecurePage(Page page) {
    super(page);
    this.flashAlert = new FlashAlert(page);
  }

  @Override
  protected String path() {
    return "/secure";
  }

  public SecurePage waitUntilLoaded(String username) {
    page.locator(String.format(GREETING_MESSAGE, username.toLowerCase()))
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
    return this;
  }

  public FlashAlert flashAlert() {
    return flashAlert;
  }

  @Step("Check that Secure Page should be opened")
  public void securePageShouldBeOpened() {
    page.waitForURL(BASE_URL + SECURE_URL);
    assertThat(page).hasURL(BASE_URL + path());
  }

  @Step("Check that Greeting message should be opened")
  public void greetingMessageShouldBeDisplayed(String userName) {
    Locator greeting = page.locator(String.format(GREETING_MESSAGE, userName.toLowerCase()));
    assertThat(greeting).isVisible();
  }

  @Step("Check that Logout button is visible")
  public void isLogoutButtonVisible() {
    assertThat(logout).isVisible();
  }

  @Step("Click Logout button")
  public LoginPage logout() {
    logout.click();
    return new LoginPage(page, new FlashAlert(page));
  }
}
