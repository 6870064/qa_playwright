package ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.example.helpers.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;
import static org.example.constants.Alerts.LOGIN_ALERT_TEXT;
import static org.example.constants.Constants.*;
import static org.example.constants.PageTitle.OTP_LOGIN_PAGE_TITLE;
import static org.example.constants.PageTitle.SECURE_AREA_PAGE_TITLE;
import static org.example.enums.PageInfo.*;
import static org.junit.jupiter.api.Assertions.*;

public class OTPOneTimePasswordTests extends BaseTest{

  @BeforeEach
  public void beforeEachTest() {
    linkClick(String.format(LINK_LOCATOR, OTP_LOGIN.linkTitle()));
    page
        .locator(String.format(PAGE_TITLE_LOCATOR, OTP_LOGIN_PAGE_TITLE))
        .waitFor(new Locator.WaitForOptions().setState(VISIBLE));
  }

  @Test
  public void sendOneTimePasswordTest() {
    page.fill(String.format(INPUT_LOCATOR, "email"), USER_EMAIL);
    page.click(String.format(BUTTON_LOCATOR, "btn-send-otp"));

    page.waitForURL(BASE_URL + OTP_LOGIN.path());
    assertEquals(OTP_LOGIN.url(), page.url(), "OTP Verification page is opened");
    Boolean isMessageIsDisplayed = page
        .locator(String.format(OTP_MESSAGE_LOCATOR, USER_EMAIL))
        .isVisible();

    assertTrue(isMessageIsDisplayed,"OTP Message is displayed");
    page.fill(String.format(INPUT_LOCATOR, "otp"), OTP_CODE);
    page.click(String.format(BUTTON_LOCATOR, "btn-send-verify"));

    page.waitForURL(BASE_URL + SECURE.path());

    Locator alert = page.getByRole(AriaRole.ALERT);
    assertThat(alert).isVisible(); // дождётся появления автоматически
    assertThat(alert).containsText(LOGIN_ALERT_TEXT);

    Boolean isSecurePageVisible = page
        .locator(String.format(PAGE_TITLE_LOCATOR, SECURE_AREA_PAGE_TITLE))
        .isVisible();
    assertAll("Assert URL, alert and text message",
        ()->assertEquals(SECURE.url(), page.url(), "User is logged in"),
        ()-> assertTrue(isSecurePageVisible, "Secure Area page is displayed"));
  }

  @Test
  public void sendInvalidOneTimePasswordTest() {
    page.fill(String.format(INPUT_LOCATOR, "email"), USER_EMAIL);
    page.click(String.format(BUTTON_LOCATOR, "btn-send-otp"));

    page.waitForURL(BASE_URL + OTP_LOGIN.path());
    assertEquals(OTP_LOGIN.url(), page.url(), "OTP Verification page is opened");
    Boolean isMessageIsDisplayed = page
        .locator(String.format(OTP_MESSAGE_LOCATOR, USER_EMAIL))
        .isVisible();

    assertTrue(isMessageIsDisplayed,"OTP Message is displayed");
    page.fill(String.format(INPUT_LOCATOR, "otp"), String.valueOf(new DataGenerator().generateRandomInt(6)));
    page.click(String.format(BUTTON_LOCATOR, "btn-send-verify"));

    page.waitForURL(OTP_VERIFICATION.url());

    Boolean isInvalidOTPErrorMessage = page
        .locator(OTP_ERROR_MESSAGE_LOCATOR)
        .isVisible();

    assertAll("Assert URL and error message",
        ()->assertEquals(OTP_VERIFICATION.url(), page.url(), "OTP Verification page is opened"),
        ()->assertTrue(isInvalidOTPErrorMessage, "Invalid OTP code is incorrect message is displayed"));
  }
}
