package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesWelcomePage extends BasePage {
  private final Locator welcomeTitle = page.getByRole(AriaRole.HEADING,
      new Page.GetByRoleOptions().setName("Welcome to Notes App"));

  private final Locator login = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Login"));

  private final Locator createAnAccount = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Create an account"));

  private final Locator forgotPassword = page.getByRole(AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Forgot your password?"));

  public MyNotesWelcomePage(Page page) {
    super(page);
    waitForOpen();
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
  }

  @Override
  protected void waitForOpen() {
    welcomeTitle.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
    );
  }

  public MyNotesLoginPage clickLogin(){
    login.click();
    page.waitForURL("**" + UIRotes.LOGIN);
    return new MyNotesLoginPage(page);
  }

  public MyNotesRegisterPage createNewAccount() {
    createAnAccount.click();
    page.waitForURL("**" + UIRotes.REGISTER);
    return new MyNotesRegisterPage(page);
  }

  public boolean isWelcomeTitleIsVisible() {
    return welcomeTitle.isVisible();
  }

  public boolean isLoginButtonIsVisible() {
    return login.isVisible();
  }

  public boolean isCreateAnAccountIsVisible() {
    return createAnAccount.isVisible();
  }



}
