package testdata;

import io.qameta.allure.Step;
import org.example.objects.User;

/**
 * Utility class containing predefined user data for testing purposes.
 * Provides static methods to retrieve common user profiles used across different test suites.
 */
public class TestUsers {

  /**
   * Returns a User object populated with valid credentials for standard authentication tests.
   *
   * @return a User instance with predefined email, name, and password
   */
  @Step("Get valid test user credentials")
  public static User validUser() {
    return new User(
        "testUser24@mail.com",
        "testUser24",
        "qwerty_98",
        "qwerty_98"
    );
  }
}