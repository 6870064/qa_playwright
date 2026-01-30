package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesHomePage extends BasePage {

  private final Locator brandHeader = page.locator("//a[@data-testid='home']");
  private final Locator profileButton = page.locator("//a[@data-testid='profile']");
  private final Locator logoutButton = page.locator("//button[@data-testid='logout']");

  public MyNotesHomePage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
  }

  public boolean isHeaderBrandIsVisible() {
    return brandHeader.isVisible();
  }

  public boolean isLogoutButtonIsVisible() {
    return logoutButton.isVisible();
  }

  public MyNotesWelcomePage logoutClick() {
    logoutButton.click();
    page.waitForURL("**" + UIRotes.HOME);
    return new MyNotesWelcomePage(page);
  }
}
