package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.components.HeaderComponent;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Page object representing the User Profile page in the My Notes application.
 * Provides methods for managing profile information and account deletion.
 */
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

  /**
   * Initializes the Profile page and waits for its specific elements to load.
   *
   * @param page the Playwright Page instance
   */
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

  /**
   * Clicks the button to update profile information.
   */
  @Step("Click 'Update Profile' button")
  public void clickUpdatePrile() {
    updateProfile.click();
  }

  /**
   * Waits for the update success alert and closes it.
   */
  @Step("Close update profile alert message")
  public void closeUpdateProfileAlert() {
    updateProfileAlert.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
    );
    closeUpdateProfileAlert.click();
  }

  /**
   * Clicks the button to initiate account deletion.
   */
  @Step("Click 'Delete Account' button")
  public void clickDeleteAccount() {
    deleteAccount.click();
  }

  /**
   * Verifies that the account deletion confirmation alert is visible.
   */
  @Step("Assert that delete account confirmation alert is visible")
  public void assertDeleteAlertIsVisible() {
    deleteAlert.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));
  }

  /**
   * Confirms account deletion and waits for redirection to the login page.
   *
   * @return a new instance of MyNotesLoginPage
   */
  @Step("Confirm account deletion")
  public MyNotesLoginPage ClickConfirmDelete() {
    confirmDelete.click();
    page.waitForURL("**" + UIRotes.LOGIN);
    return new MyNotesLoginPage(page);
  }

  /**
   * Retrieves the current user ID from the input field.
   *
   * @return the user ID string
   */
  @Step("Get User ID from profile")
  public String getUserId() {
    return userIdInput.inputValue();
  }

  /**
   * Retrieves the current full name from the input field.
   *
   * @return the full name string
   */
  @Step("Get Full Name from profile")
  public String getFullName() {
    return fullNameInput.inputValue();
  }

  /**
   * Retrieves the current email address from the input field.
   *
   * @return the email address string
   */
  @Step("Get Email Address from profile")
  public String getEmailAddress() {
    return emailInput.inputValue();
  }

  /**
   * Clears the text from the full name input field.
   */
  @Step("Clear Full Name input field")
  public void clearFullName() {
    fullNameInput.clear();
  }

  /**
   * Fills the full name input field with the provided text.
   *
   * @param fullName the new full name to enter
   */
  @Step("Fill Full Name: {fullName}")
  public void fillFullName(String fullName) {
    fullNameInput.fill(fullName);
  }

  /**
   * Fills the phone number input field with the provided text.
   *
   * @param phoneNumber the new phone number to enter
   */
  @Step("Fill Phone Number: {phoneNumber}")
  public void fillPhoneNumber(String phoneNumber) {
    phoneNumberInput.fill(phoneNumber);
  }

  /**
   * Retrieves the current phone number from the input field.
   *
   * @return the phone number string
   */
  @Step("Get Phone Number from profile")
  public String getPhoneNumber() {
    return phoneNumberInput.inputValue();
  }

  /**
   * Fills the company name input field with the provided text.
   *
   * @param companyName the new company name to enter
   */
  @Step("Fill Company Name: {companyName}")
  public void fillCompanyName(String companyName) {
    companyNameInput.fill(companyName);
  }

  /**
   * Retrieves the current company name from the input field.
   *
   * @return the company name string
   */
  @Step("Get Company Name from profile")
  public String getCompanyName() {
    return companyNameInput.inputValue();
  }

  /**
   * Validates that the User ID field is populated and cannot be edited by the user.
   */
  @Step("Verify that User ID is not empty and is read-only")
  public void verifyUserIdIsStatic() {
    assertThat(userIdInput).hasValue(Pattern.compile(".+"));
    assertThat(userIdInput).not().isEditable();
  }
}