import com.microsoft.playwright.Locator;
import org.example.helpers.DataGenerator;
import org.example.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTests extends BaseTest {

  @BeforeEach
  public void beforeEachTest(){
    linkClick(String.format(LINK_LOCATOR, "Test Register Page"));
    page
        .locator(String.format(TEXT_LOCATOR, "Test Register page for Automation Testing Practice"))
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  @Test
  public void userRegistrationTest() {
    User user = new User(
        new DataGenerator().generateRandomName(4,30),
        new DataGenerator().generateRandomPassword(8,20));

    page.fill(String.format(INPUT_LOCATOR,"username"), user.getUsername());
    page.fill(String.format(INPUT_LOCATOR, "password"), user.getPassword());
    page.fill(String.format(INPUT_LOCATOR, "confirmPassword"), user.getConfirmPassword());
    page.click(REGISTRATION_BUTTON);

    page.waitForURL(BASE_URL + LOGIN);
    assertEquals(BASE_URL + LOGIN, page.url(), "Login page for Automation Testing Practise");

    Boolean isLoginPageVisible = page
        .locator(String.format(TEXT_LOCATOR, "Login page for Automation Testing Practice"))
        .isVisible();

    assertTrue(isLoginPageVisible, "Login page text is not visible after registration");






  }
}
