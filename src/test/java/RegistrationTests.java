import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.example.helpers.DataGenerator;
import org.example.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.constants.Alerts.*;
import static org.example.constants.Constants.*;
import static org.example.constants.PageTitle.LOGIN_PAGE_TITLE;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTests extends BaseTest {

  private static Stream<Arguments> provideTestData() {
    return Stream.of(
        Arguments.of(new User(
                new DataGenerator().generateRandomName(4, 30),
                new DataGenerator().generateRandomPassword(8, 20),
                new DataGenerator().generateRandomPassword(8, 20)),
            PASSWORDS_NOT_MATCH_ALERT_TEXT),
        Arguments.of(new User(
                USERNAME,
                PASSWORD,
                PASSWORD),
            USER_TAKEN_ALERT_TEXT),
        Arguments.of(new User(
                "",
                "",
                ""),
            ALL_FIELDS_REQUIRED_ALERT_TEXT));
  }

  @BeforeEach
  public void beforeEachTest() {
    linkClick(String.format(LINK_LOCATOR, "Test Register Page"));
    page
        .locator(String.format(PAGE_TITLE_LOCATOR, "Test Register page for Automation Testing Practice"))
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  @Test
  public void userRegistrationTest() {
    String password = new DataGenerator().generateRandomPassword(8, 20);
    User user = new User(
        new DataGenerator().generateRandomName(4, 30),
        password,
        password);

    page.fill(String.format(INPUT_LOCATOR, "username"), user.getUsername());
    page.fill(String.format(INPUT_LOCATOR, "password"), user.getPassword());
    page.fill(String.format(INPUT_LOCATOR, "confirmPassword"), user.getConfirmPassword());
    page.click(REGISTRATION_BUTTON);

    page.waitForURL(BASE_URL + LOGIN_URL);
    assertEquals(BASE_URL + LOGIN_URL, page.url(), LOGIN_PAGE_TITLE);

    Boolean isLoginPageVisible = page
        .locator(String.format(PAGE_TITLE_LOCATOR, "Login page for Automation Testing Practice"))
        .isVisible();

    assertTrue(isLoginPageVisible, "Login page text is visible after registration");

    page.fill(String.format(INPUT_LOCATOR, "username"), user.getUsername());
    page.fill(String.format(INPUT_LOCATOR, "password"), user.getPassword());
    System.out.println("username: " + user.getUsername());
    System.out.println("password: " + user.getPassword());
    page.click(LOGIN_BUTTON);

    page.waitForURL(BASE_URL + SECURE_URL);
    assertEquals(BASE_URL + SECURE_URL, page.url(), "Login page is opened");

    page.locator(String.format(GREETING_MESSAGE, user.getUsername().toLowerCase())).waitFor(new Locator.WaitForOptions().setState(VISIBLE));
    assertTrue(page.locator(USERNAME_LOCATOR).isVisible());
    assertTrue(page.locator(LOGOUT_BUTTON).isVisible());

    page.click(LOGOUT_BUTTON);
    page.waitForURL(BASE_URL + LOGIN_URL);

    Boolean finalIsLoginPageVisible = page
        .locator(String.format(PAGE_TITLE_LOCATOR, LOGIN_PAGE_TITLE))
        .isVisible();
    assertAll("Assert URL and text message",
        ()->assertEquals(BASE_URL + LOGIN_URL, page.url(), "Login page is opened"),
        ()->assertTrue(finalIsLoginPageVisible, "Login page text is not visible after registration"));
  }

  @MethodSource("provideTestData")
  @ParameterizedTest(name = "{0}")
  public void registrateUserWithInvalidCredentialsTest(User user, String errorMessage) {
    page.fill(String.format(INPUT_LOCATOR, "username"), user.getUsername());
    page.fill(String.format(INPUT_LOCATOR, "password"), user.getPassword());
    page.fill(String.format(INPUT_LOCATOR, "confirmPassword"), user.getConfirmPassword());
    page.click(REGISTRATION_BUTTON);

    Locator alert = page.getByRole(AriaRole.ALERT);
    assertThat(alert).isVisible(); // дождётся появления автоматически
    assertThat(alert).containsText(errorMessage);

    Boolean isRegistrationPageVisible = page
        .locator(String.format(PAGE_TITLE_LOCATOR, LOGIN_PAGE_TITLE))
        .isVisible();

    assertAll("Assert URL and text message",
        ()-> assertEquals(BASE_URL + REGISTER_URL, page.url(), "Registration page is opened"),
        ()->assertTrue(isRegistrationPageVisible, "Registration page title is displayed"));
  }
}
