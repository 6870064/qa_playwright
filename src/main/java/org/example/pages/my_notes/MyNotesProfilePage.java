package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.components.HeaderComponent;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesProfilePage extends BasePage {
  public final HeaderComponent header;
  private final Locator profileHeader = page.getByText("Profile settings");
  private final Locator phoneNumberInput = page.getByTestId("user-phone");
  private final Locator companyNameInput = page.getByTestId("user-company");
  private final Locator userIdInput = page.getByTestId("user-id");
  private final Locator emailInput = page.getByTestId("user-email");
  private final Locator fullNameInput = page.getByTestId("user-name");
  private final Locator deleteAlert = page.getByText("Do you really want to delete your account?");

  private final Locator updateProfile = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Update Profile"));

  private final Locator deleteAccount = page.getByTestId("delete-account");

  private final Locator confirmDelete = page.getByTestId("note-delete-confirm");

  private final Locator cancelDelete = page.getByTestId("note-delete-cancel-2");

  private final Locator updateProfileAlert = page.getByTestId("alert-message");
  private final Locator closeUpdateProfileAlert = page.getByTestId("alert-close");

  public MyNotesProfilePage(Page page) {
    super(page);
    this.header = new HeaderComponent(page);
    waitForOpen();
  }

  @Override
  protected String path() {
    return UIRotes.PROFILE;
  }

  @Override
  protected void waitForOpen() {
    super.waitForOpen();
    profileHeader.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
    );
  }

  public void clickUpdatePrile() {
    updateProfile.click();
  }

  public void closeUpdateProfileAlert(){
    updateProfileAlert.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
    );
    closeUpdateProfileAlert.click();
  }

  public void clickDeleteAccount() {
    deleteAccount.click();
  }

  public boolean isDeleteAlertVisible() {
    deleteAlert.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));
    return deleteAlert.isVisible();
  }

  public MyNotesLoginPage ClickConfirmDelete() {
    confirmDelete.click();
    page.waitForURL("**" + UIRotes.LOGIN);
    return new MyNotesLoginPage(page);
  }

  public String getUserId() {
    return userIdInput.inputValue();
  }

  public String getFullName() {
    return fullNameInput.inputValue();
  }

  public String getEmailAddress() {
    return emailInput.inputValue();
  }

  public void clearFullName() {
    fullNameInput.clear();
  }

  public void fillFullName(String fullName) {
    fullNameInput.fill(fullName);
  }

  public void fillPhoneNumber(String phoneNumber) {
    phoneNumberInput.fill(phoneNumber);
  }

  public String getPhoneNumber() {
    return phoneNumberInput.inputValue();
  }

  public void fillCompanyName(String companyName) {
    companyNameInput.fill(companyName);
  }

  public String getCompanyName() {
    return companyNameInput.inputValue();
  }
}
