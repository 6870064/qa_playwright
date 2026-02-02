package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.example.components.HeaderComponent;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesProfilePage extends BasePage {
  public final HeaderComponent header;
  private final Locator profileHeader = page.getByText("Profile settings");
  private final Locator phoneNumberInput = page.getByTestId("user-company");
  private final Locator companyNameInput = page.getByTestId("user-company");
  private final Locator userIdInput = page.getByTestId("user-id");
  private final Locator emailInput = page.getByTestId("user-email");
  private final Locator fullNameInput = page.getByTestId("user-name");

  private final Locator updateProfile = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Update Profile"));

  private final Locator deleteAccount = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Delete Account"));

  private final Locator confirmDelete = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Delete"));

  private final Locator cancelDelete = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Cancel"));

  public MyNotesProfilePage(Page page) {
    super(page);
    this.header = new HeaderComponent(page);
  }

  @Override
  protected String path() {
    return UIRotes.PROFILE;
  }

  public void clickUpdatePrile() {
    updateProfile.click();
  }

  public void clickDeleteAccount() {
    deleteAccount.click();
  }

  public MyNotesLoginPage confirmDelete() {
    confirmDelete.click();
    page.waitForURL("**" + UIRotes.LOGIN);
    return new MyNotesLoginPage(page);
  }
}
