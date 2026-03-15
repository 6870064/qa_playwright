package ui.my_notes;

import io.qameta.allure.Description;
import org.example.objects.User;
import org.example.pages.my_notes.MyNotesHomePage;
import org.example.pages.my_notes.MyNotesLoginPage;
import org.example.pages.my_notes.MyNotesProfilePage;
import org.example.pages.my_notes.MyNotesRegisterPage;
import org.example.pages.my_notes.MyNotesWelcomePage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class MyNotesProfileTests extends BaseTest {

  @DisplayName("[UI]. Notes App. Update user profile and delete account")
  @Description("""
      1. Open the Home page.
      2. Navigate to the 'Notes App'.
      3. Click the 'Create an account' button.
      4. Register a new user with random data.
      5. Click the 'Click here to Log In' link.
      6. Log in with the newly created user.
      7. Navigate to the 'Profile' page via the header.
      8. Verify initial profile data (User ID, Email, Name) matches registration data.
      9. Generate new profile data (Name, Phone, Company).
      10. Clear the full name field and fill in new profile information.
      11. Click the 'Update Profile' button and close the success alert.
      12. Verify that all profile fields are correctly updated in the UI.
      13. Click the 'Delete Account' button.
      14. Assert that the delete confirmation alert is visible.
      15. Confirm account deletion.
      16. Verify redirection to the Login page and visibility of the success alert.
      """)
  @Test
  public void UpdateUserProfileAndDeleteTest() {
    String password = dataGenerator.generateRandomPassword(8, 10);
    User user = new User(
        dataGenerator.generateRandomEmail(true),
        dataGenerator.generateRandomName(5, 10),
        password,
        password);
    HomePage homePage = new HomePage(page()).open();

    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.createNewAccount();
    myNotesRegisterPage.registerNewUser(user);
    myNotesRegisterPage.waitForSuccess();
    myNotesRegisterPage.assertLoginLinkIsVisible();

    MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.loginLinkClick();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);
    myNotesHomePage.header.assertHeaderForAuthenticatedUserIsVisible();

    MyNotesProfilePage myNotesProfilePage = myNotesHomePage.header.goToProfile();
    assertAll("Initial profile data verification",
        () -> assertNotNull(myNotesProfilePage.getUserId(), "User ID should not be null"),

        () -> assertEquals(user.getEmail().toLowerCase(),
            myNotesProfilePage.getEmailAddress(),
            "Email should match registration email"),

        () -> assertEquals(user.getName(),
            myNotesProfilePage.getFullName(),
            "Full name should match registration name"));

    user.setName(dataGenerator.generateRandomName(8, 10));
    user.setPhoneNumber(dataGenerator.generateRandomPhoneNumber("48"));
    user.setCompanyName(dataGenerator.generateRandomCompanyName());
    user.setUserId(myNotesProfilePage.getUserId());

    myNotesProfilePage.clearFullName();
    myNotesProfilePage.fillFullName(user.getName());
    myNotesProfilePage.fillPhoneNumber(user.getPhoneNumber());
    myNotesProfilePage.fillCompanyName(user.getCompanyName());
    myNotesProfilePage.clickUpdatePrile();
    myNotesProfilePage.closeUpdateProfileAlert();
    myNotesProfilePage.verifyUserIdIsStatic();

    assertAll("Updated profile data verification",
        () -> assertEquals(user.getUserId(),
            myNotesProfilePage.getUserId(),
            "User ID should remain unchanged"),

        () -> assertEquals(user.getEmail().toLowerCase(),
            myNotesProfilePage.getEmailAddress(),
            "Email should remain unchanged"),

        () -> assertEquals(user.getName(),
            myNotesProfilePage.getFullName(),
            "Full name should be updated correctly"),

        () -> assertEquals(user.getPhoneNumber(),
            myNotesProfilePage.getPhoneNumber(),
            "Phone number should be updated correctly"),

        () -> assertEquals(user.getCompanyName(),
            myNotesProfilePage.getCompanyName(),
            "Company name should be updated correctly")
    );

    myNotesProfilePage.clickDeleteAccount();
    myNotesProfilePage.assertDeleteAlertIsVisible();
    MyNotesLoginPage mySecondNotesLoginPage = myNotesProfilePage.ClickConfirmDelete();
    mySecondNotesLoginPage.assertDeleteAlertIsVisible();
  }
}
