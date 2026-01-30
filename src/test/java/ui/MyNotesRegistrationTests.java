package ui;

import io.qameta.allure.Description;
import org.example.objects.User;
import org.example.pages.my_notes.MyNotesHomePage;
import org.example.pages.my_notes.MyNotesLoginPage;
import org.example.pages.my_notes.MyNotesRegisterPage;
import org.example.pages.my_notes.MyNotesWelcomePage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyNotesRegistrationTests extends BaseTest {

  @DisplayName("[UI]. Drag and drop circles page. Drop a circle with a color ")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Drag and Drop Circles' page.
      3. Drag a 'red' circle to the drop zone.
      4. Assert amount of circles dropped to a drop zone.
      5. Drag a 'green' circle to the drop zone.
      6. Assert amount of circles dropped to a drop zone.
      7. Drag a 'blue' circle to the drop zone.
      8. Assert amount of circles dropped to a drop zone.
      """)
  @Test
  public void registerNewUser() {
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
    assertTrue(myNotesRegisterPage.isLoginLinkIsVisible());
    MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.loginLinkClick();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);
    assertTrue(myNotesHomePage.isHeaderBrandIsVisible());
    assertTrue(myNotesHomePage.isLogoutButtonIsVisible());
    MyNotesWelcomePage secondMyNotesWelcomePage = myNotesHomePage.logoutClick();
    assertTrue(secondMyNotesWelcomePage.isWelcomeTitleIsVisible(), "");
    assertTrue(secondMyNotesWelcomePage.isLoginButtonIsVisible(), "");
    assertTrue(secondMyNotesWelcomePage.isCreateAnAccountIsVisible(), "");
  }
}
