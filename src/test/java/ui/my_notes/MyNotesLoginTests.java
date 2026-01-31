package ui.my_notes;

import io.qameta.allure.Description;
import org.example.objects.User;
import org.example.pages.my_notes.MyNotesHomePage;
import org.example.pages.my_notes.MyNotesLoginPage;
import org.example.pages.my_notes.MyNotesWelcomePage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyNotesLoginTests extends BaseTest {

  @DisplayName("[UI]. Notes App. Login by existing user")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Notes App | React' page.
      3. Click a 'Create an account' button.
      4. Assert amount of circles dropped to a drop zone.
      5. Drag a 'green' circle to the drop zone.
      6. Assert amount of circles dropped to a drop zone.
      7. Drag a 'blue' circle to the drop zone.
      8. Assert amount of circles dropped to a drop zone.
      """)
  @Test
  public void loginUserTest() {
    User user = new User("testUser24@mail.com",
        "testUser24",
        "qwerty_98",
        "qwerty_98");
    HomePage homePage = new HomePage(page()).open();
    MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesApp();
    MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.clickLogin();
    MyNotesHomePage myNotesHomePage = myNotesLoginPage.loginUser(user);
    assertTrue(myNotesHomePage.isHeaderBrandIsVisible());
    assertTrue(myNotesHomePage.isLogoutButtonIsVisible());
    MyNotesWelcomePage secondMyNotesWelcomePage = myNotesHomePage.logoutClick();
    assertTrue(secondMyNotesWelcomePage.isWelcomeTitleIsVisible(), "");
    assertTrue(secondMyNotesWelcomePage.isLoginButtonIsVisible(), "");
    assertTrue(secondMyNotesWelcomePage.isCreateAnAccountIsVisible(), "");
  }
}
