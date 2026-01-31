package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesHomePage extends BasePage {

  private final Locator brandHeader = page.getByText("MyNotes");

  private final Locator profileButton = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Profile"));

  private final Locator logoutButton = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Logout"));

  public MyNotesHomePage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
  }

  @Step("Validate that Brand title is visible")
  public boolean isHeaderBrandIsVisible() {
    return brandHeader.isVisible();
  }

  @Step("Validate that 'Logout' button is visible")
  public boolean isLogoutButtonIsVisible() {
    return logoutButton.isVisible();
  }

  @Step("Click 'Logout' button")
  public MyNotesWelcomePage logoutClick() {
    logoutButton.click();
    page.waitForURL("**" + UIRotes.HOME);
    return new MyNotesWelcomePage(page);
  }
}
