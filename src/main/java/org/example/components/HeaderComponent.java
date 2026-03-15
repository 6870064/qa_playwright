package org.example.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.constants.routes.UIRotes;
import org.example.pages.my_notes.MyNotesProfilePage;
import org.example.pages.my_notes.MyNotesWelcomePage;

public class HeaderComponent {
  private final Page page;
  private final Locator brandHeader;
  private final Locator profileButton;
  private final Locator logoutButton;

  public HeaderComponent(Page page) {
    this.page = page;
    this.brandHeader = page.getByText("MyNotes");

    this.profileButton = page.getByTestId("profile");

    this.logoutButton = page.getByTestId("logout");
  }

  @Step("Click 'Profile' button")
  public MyNotesProfilePage goToProfile() {
    profileButton.click();
    page.waitForURL("**" + UIRotes.PROFILE);
    return new MyNotesProfilePage(page);
  }

  @Step("Click 'Logout' button")
  public MyNotesWelcomePage clickLogout() {
    logoutButton.click();
    page.waitForURL("**" + UIRotes.HOME);
    return new MyNotesWelcomePage(page);
  }

  @Step("Assert that header is visible for authenticated user")
  public void assertHeaderForAuthenticatedUserIsVisible() {
    brandHeader.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));

    profileButton.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));

    logoutButton.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));
  }
}
