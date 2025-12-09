package ui;

import org.example.helpers.DataGenerator;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.pages.RegisterPage;
import org.example.pages.SecurePage;
import org.example.requests.user.UiUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.constants.Alerts.*;
import static org.example.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTests extends BaseTest {

  private static Stream<Arguments> provideTestData() {
    return Stream.of(
        Arguments.of(new UiUser(
                new DataGenerator().generateRandomName(4, 30),
                new DataGenerator().generateRandomPassword(8, 20),
                new DataGenerator().generateRandomPassword(8, 20)),
            PASSWORDS_NOT_MATCH_ALERT_TEXT),
        Arguments.of(new UiUser(
                USERNAME,
                PASSWORD,
                PASSWORD),
            USER_TAKEN_ALERT_TEXT),
        Arguments.of(new UiUser(
                "",
                "",
                ""),
            ALL_FIELDS_REQUIRED_ALERT_TEXT));
  }

  @Test
  public void userRegistrationAndLoginTest() {
    String pwd = new DataGenerator().generateRandomPassword(8, 20);
    UiUser user = new UiUser(new DataGenerator().generateRandomName(4, 30), pwd, pwd);

    HomePage homePage = new HomePage(page).open();
    RegisterPage registerPage = homePage.goToRegister();
    LoginPage loginPage = registerPage.registerNewUser(user);

    loginPage.loginPageShouldBeOpened();
    loginPage.flashAlert().shouldBeVisible();
    loginPage.flashAlert().shouldContain("Successfully registered, you can log in now.");

    loginPage
        .loginAs(user.getUsername(), user.getPassword())
        .waitUntilLoaded(user.getUsername());

    SecurePage securePage = new SecurePage(page);

    assertAll(
        "User is successfully logged in",
        () -> securePage.securePageShouldBeOpened(),
        () -> securePage.greetingMessageShouldBeDisplayed(user.getUsername()),
        () -> securePage.isLogoutButtonVisible(),
        () -> securePage.flashAlert().shouldContain("You logged into a secure area!"));
  }

  @MethodSource("provideTestData")
  @ParameterizedTest(name = "{0}")
  public void registrationUserWithInvalidCredentialsTest(UiUser user, String errorMessage) {
    HomePage homePage = new HomePage(page).open();
    RegisterPage registerPage = homePage.goToRegister();
    registerPage = registerPage.tryToRegisterInvalidUser(user);

    RegisterPage finalRegisterPage = registerPage;
    assertAll(
        "User is not registered",
        () -> finalRegisterPage.flashAlert().shouldContain(errorMessage),
        () -> assertEquals(BASE_URL + "/register", page.url()));
  }
}
