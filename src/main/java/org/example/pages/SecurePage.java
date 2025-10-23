package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.example.components.FlashAlert;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;

public class SecurePage extends BasePage{
  private final FlashAlert flashAlert;
  public static final String GREETING_MESSAGE = "//h3[@id='username' and normalize-space()='Hi, %s!']";
  private final Locator logout = page.getByRole(
      AriaRole.LINK, new Page.GetByRoleOptions().setName("Logout"));

  public SecurePage(Page page, FlashAlert flashAlert) {
    super(page);
    this.flashAlert = flashAlert;
  }

  @Override
  protected String path() {
    return "/secure";
  }

  public void waitUntilLoaded(String username) {
    page.locator(String.format(GREETING_MESSAGE, username.toLowerCase()))
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  public Locator greeting(String username) {
    return page.locator(String.format(GREETING_MESSAGE, username.toLowerCase()));
  }

  public FlashAlert flashAlert() {
    return flashAlert;
  }


  public LoginPage logout() {
    logout.click();
    return new LoginPage(page);
  }
}
