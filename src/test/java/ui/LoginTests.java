package ui;

import org.example.components.FlashAlert;
import org.example.helpers.DataGenerator;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.pages.SecurePage;
import org.junit.jupiter.api.Test;

import static org.example.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTests extends BaseTest {

  @Test
  public void userLoginTest() {
    SecurePage securePage =  new HomePage(page)
        .goToLogin()
        .loginAs(USERNAME, PASSWORD)
        .waitUntilLoaded(USERNAME);

    assertAll("User is successfully logged in",
        ()-> securePage.securePageShouldBeOpened(),
        ()->securePage.greetingMessageShouldBeDisplayed(USERNAME),
        ()->securePage.isLogoutButtonVisible(),
        ()->securePage.flashAlert().shouldContain("You logged into a secure area!"));
  }

  @Test
  public void attemptToLoginWithInvalidUserNameTest() {
    SecurePage securePage =  new HomePage(page)
        .goToLogin()
        .loginAs(new DataGenerator().generateRandomName(8, 10), PASSWORD);
    LoginPage loginPage = new LoginPage(page, new FlashAlert(page));

    assertAll("User is not logged in due to invalid username",
        ()->loginPage.loginPageShouldBeOpened(),
        ()->loginPage.flashAlert().shouldContain("Your username is invalid!"));
  }

  @Test
  public void attemptToLoginWithInvalidPasswordNameTest() {
    SecurePage securePage =  new HomePage(page)
        .goToLogin()
        .loginAs(USERNAME, new DataGenerator().generateRandomPassword(8, 10));
    LoginPage loginPage = new LoginPage(page, new FlashAlert(page));

    assertAll("User is not logged in due to invalid username",
        ()->loginPage.loginPageShouldBeOpened(),
        ()->loginPage.flashAlert().shouldContain("Your password is invalid!"));
  }

  @Test
  public void userLogoutTest() {
    SecurePage securePage = new HomePage(page)
        .goToLogin()
        .loginAs(USERNAME, PASSWORD)
        .waitUntilLoaded(USERNAME);
        LoginPage loginPage = securePage.logout();
        loginPage.loginPageShouldBeOpened();

        assertAll("User logged out successfully",
            ()-> loginPage.loginPageShouldBeOpened(),
            ()-> loginPage.flashAlert().shouldBeVisible(),
            ()-> loginPage.flashAlert().shouldContain("You logged out of the secure area!"),
            ()-> assertEquals(BASE_URL + loginPage.path(), page.url()));
  }
}
