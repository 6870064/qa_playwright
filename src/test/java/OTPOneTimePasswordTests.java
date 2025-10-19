import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OTPOneTimePasswordTests extends BaseTest{

  @BeforeEach
  public void beforeEachTest() {
    linkClick(String.format(LINK_LOCATOR, "OTP: One Time Password"));
    page
        .locator(String.format(TEXT_LOCATOR, "OTP Login page for Automation Testing Practice"))
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  @Test
  public void sendOneTimePasswordTest() {
    page.fill(String.format(INPUT_LOCATOR, "email"), USER_EMAIL);
    page.click(String.format(BUTTON_LOCATOR, "btn-send-otp"));

    page.waitForURL(BASE_URL + OTP_LOGIN_URL);
    assertEquals(BASE_URL + OTP_LOGIN_URL, page.url(), "OTP Verification page is opened");
    Boolean isMessageIsDisplayed = page
        .locator(String.format(OTP_MESSAGE_LOCATOR, USER_EMAIL))
        .isVisible();

    assertTrue(isMessageIsDisplayed,"OTP Message is displayed");
    page.fill(String.format(INPUT_LOCATOR, "otp"), OTP_CODE);
    page.click(String.format(BUTTON_LOCATOR, "btn-send-verify"));

  }
}
