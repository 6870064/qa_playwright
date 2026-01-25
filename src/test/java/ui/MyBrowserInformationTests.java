package ui;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import org.example.pages.ForgotPasswordPage;
import org.example.pages.HomePage;
import org.example.pages.MyBrowserInformationPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MyBrowserInformationTests extends BaseTest {

  String[] labels = {"User Agent", "CodeName", "Name", "Version", "Cookies Enabled", "Platform"};

  @DisplayName("[UI]. Forgot Password Form. Validate possibility to retrieve password")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Forgot Password Form'.
      3. Enter a valid email to retrieve.
      4. Click 'Retrieve password' button.
      5. Assert visibility of the message 'An e-mail has been sent to you which explains how to reset your password.'
      """)
  @Test
  public void myBrowserInformationTest() {
    HomePage homePage = new HomePage(page()).open();
    MyBrowserInformationPage myBrowserInformationPage = homePage.goMyBrowserInformation();
    myBrowserInformationPage.showBrowserInfoButtonClick();

    assertFalse(myBrowserInformationPage.getValue("User Agent").isEmpty());
    assertFalse(myBrowserInformationPage.getValue("CodeName").isEmpty());
    assertFalse(myBrowserInformationPage.getValue("Name").isEmpty());
    assertFalse(myBrowserInformationPage.getValue("Version").isEmpty());
    assertFalse(myBrowserInformationPage.getValue("Platform").isEmpty());

    assertEquals("true", myBrowserInformationPage.getValue("Cookies Enabled"));
    assertEquals("Mozilla", myBrowserInformationPage.getValue("Name"));
    assertEquals("Mozilla", myBrowserInformationPage.getValue("CodeName"));
    }
  }

