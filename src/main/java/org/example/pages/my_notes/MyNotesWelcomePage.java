package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesWelcomePage extends BasePage {
  private final Locator login = page.locator("//a[@href='/notes/app/login']");
  private final Locator createAnAccount = page.locator("//*[@data-testid='open-register-view']");
  private final Locator forgotPassword = page.locator("//*[@data-testid='open-register-view']");

  public MyNotesWelcomePage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
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


}
