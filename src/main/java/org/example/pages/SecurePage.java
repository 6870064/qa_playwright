package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
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

  public Locator greeting(String username) {
    return page.locator(String.format(GREETING_MESSAGE, username.toLowerCase()));
  }

  public FlashAlert flashAlert() {
    return flashAlert;
  }

  public void securePageShouldBeOpened() {
    page.waitForURL(BASE_URL + SECURE_URL);
    assertThat(page).hasURL(BASE_URL + path());
  }

  public void greetingMessageShouldBeDisplayed(String userName) {
    Locator greeting = page.locator(String.format(GREETING_MESSAGE, userName.toLowerCase()));
    assertThat(greeting).isVisible();
  }

  public void isLogoutButtonVisible() {
    assertThat(logout).isVisible();
  }

  public LoginPage logout() {
    logout.click();
    return new LoginPage(page, new FlashAlert(page));
  }
}
