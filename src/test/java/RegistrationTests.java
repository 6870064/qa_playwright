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
import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTests extends BaseTest {

  public static String USER_TAKEN_MESSAGE = "Username is already taken.";
  public static String PASSWORDS_NOT_MATCH_MESSAGE = "Passwords do not match.";
  public static String ALL_FIELDS_REQUIRED_MESSAGE = "All fields are required.";

  private static Stream<Arguments> provideTestData() {
    return Stream.of(
        Arguments.of(new User(
                new DataGenerator().generateRandomName(4, 30),
                new DataGenerator().generateRandomPassword(8, 20),
                new DataGenerator().generateRandomPassword(8, 20)),
            PASSWORDS_NOT_MATCH_MESSAGE),
        Arguments.of(new User(
                USERNAME,
                PASSWORD,
                PASSWORD),
            USER_TAKEN_MESSAGE),
        Arguments.of(new User(
                "",
                "",
                ""),
            ALL_FIELDS_REQUIRED_MESSAGE));
  }

  @BeforeEach
  public void beforeEachTest() {
    linkClick(String.format(LINK_LOCATOR, "Test Register Page"));
    page
        .locator(String.format(TEXT_LOCATOR, "Test Register page for Automation Testing Practice"))
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
    assertEquals(BASE_URL + LOGIN_URL, page.url(), "Login page for Automation Testing Practise");

    Boolean isLoginPageVisible = page
        .locator(String.format(TEXT_LOCATOR, "Login page for Automation Testing Practice"))
        .isVisible();

    assertTrue(isLoginPageVisible, "Login page text is not visible after registration");

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
    assertEquals(BASE_URL + LOGIN_URL, page.url(), "Login page for Automation Testing Practise");

    isLoginPageVisible = page
        .locator(String.format(TEXT_LOCATOR, "Login page for Automation Testing Practice"))
        .isVisible();

    assertTrue(isLoginPageVisible, "Login page text is not visible after registration");
  }

  @MethodSource("provideTestData")
  @ParameterizedTest(name = "{0}")
  public void registrateUserWithInvalidCredentialsTest(User user, String errorMessage) {
    page.fill(String.format(INPUT_LOCATOR, "username"), user.getUsername());
    page.fill(String.format(INPUT_LOCATOR, "password"), user.getPassword());
    page.fill(String.format(INPUT_LOCATOR, "confirmPassword"), user.getConfirmPassword());
    page.click(REGISTRATION_BUTTON);

    assertEquals(BASE_URL + REGISTER_URL, page.url(), "Registration page is opened");

    Locator alert = page.getByRole(AriaRole.ALERT);
    assertThat(alert).isVisible(); // дождётся появления автоматически
    assertThat(alert).containsText(errorMessage);

    Boolean isRegistrationPageVisible = page
        .locator(String.format(TEXT_LOCATOR, "Test Register page for Automation Testing Practice"))
        .isVisible();
    assertTrue(isRegistrationPageVisible, "Registration page title is displayed");
  }
}
