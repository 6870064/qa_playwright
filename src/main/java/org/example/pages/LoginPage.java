package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.components.FlashAlert;

public class LoginPage extends BasePage{
  private final Locator username = page.locator("//input[@name='username']");
  private final Locator password = page.locator("//input[@name='password']");
  private final Locator submit = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Login"));

  private final FlashAlert flashAlert;

  public LoginPage(Page page, FlashAlert flashAlert) {
    super(page);
    this.flashAlert = flashAlert;
  }

  @Override
  public String path() {
    return "/login";
  }

  private void fillUserName(String u) {
    username.fill(u);
  }

  private void fillUserPassword(String p) {
    password.fill(p);
  }

  private void loginClick() {
    submit.click();
  }

  public FlashAlert flashAlert() {
    return flashAlert;
  }

  public SecurePage loginAs(String u, String p) {
    fillUserName(u);
    fillUserPassword(p);
    loginClick();
    return new SecurePage(page);
  }

  public LoginPage loginPageShouldBeOpened() {
    page.waitForURL("**/login",
        new Page.WaitForURLOptions().setTimeout(7000)
    );

    page.waitForSelector("//h1[contains(.,'Test Login page')]",
        new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(5000)
    );
    return new LoginPage(page, new FlashAlert(page));
  }

}
