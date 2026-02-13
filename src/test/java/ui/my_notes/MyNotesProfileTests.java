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
import testdata.TestUsers;
import ui.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class MyNotesProfileTests extends BaseTest {

  @DisplayName("[UI]. Notes App. Login by existing user")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Notes App | React' page.
      3. Click a 'Login' button.
      4. Enter a valid user's Email.
      5. Enter a valid user's password.
      6. Click 'Login' button.
      7. Assert visibility header component.
      8. Click 'Logout' button.
      9. Assert visibility of Brand header.
      10. Assert visibility of 'Logout' button.
      11. Click 'Logout' button.
      12. Assert visibility of Welcome title button.
      13. Assert visibility of 'Login' button.
      14. Assert visibility of 'Create account' button.
      """)
  @Test
  public void UpdateUserProfileAndDeleteTest() {
    String password = dataGenerator.generateRandomPassword(8,10);
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
    assertAll("",
        ()-> assertNotNull(myNotesProfilePage.getUserId()),
        ()-> assertEquals(user.getEmail().toLowerCase(), myNotesProfilePage.getEmailAddress(), ""),
        ()-> assertEquals(user.getName(), myNotesProfilePage.getFullName(), ""));

    user.setName(dataGenerator.generateRandomName(8,10));
    user.setPhoneNumber(dataGenerator.generateRandomPhoneNumber("48"));
    user.setCompanyName(dataGenerator.generateRandomCompanyName());
    user.setUserId(myNotesProfilePage.getUserId());

    myNotesProfilePage.clearFullName();
    myNotesProfilePage.fillFullName(user.getName());
    myNotesProfilePage.fillPhoneNumber(user.getPhoneNumber());
    myNotesProfilePage.fillCompanyName(user.getCompanyName());
    myNotesProfilePage.clickUpdatePrile();
    myNotesProfilePage.closeUpdateProfileAlert();

    assertAll("",
        ()-> assertEquals(user.getUserId(), myNotesProfilePage.getUserId(),""),
        ()-> assertEquals(user.getEmail().toLowerCase(), myNotesProfilePage.getEmailAddress(), ""),
        ()-> assertEquals(user.getName(), myNotesProfilePage.getFullName(), ""),
        ()-> assertEquals(user.getPhoneNumber(), myNotesProfilePage.getPhoneNumber(), ""),
        ()-> assertEquals(user.getCompanyName(), myNotesProfilePage.getCompanyName(), "")
    );

    myNotesProfilePage.clickDeleteAccount();
    myNotesProfilePage.assertDeleteAlertIsVisible();
    MyNotesLoginPage mySecondNotesLoginPage = myNotesProfilePage.ClickConfirmDelete();
    mySecondNotesLoginPage.assertDeleteAlertIsVisible();
  }
}
