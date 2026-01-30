package ui;

import io.qameta.allure.Description;
import org.example.pages.practice.HomePage;
import org.example.pages.practice.MyBrowserInfoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyBrowserInformationTests extends BaseTest {

  @DisplayName("[UI]. My Browser Information page. Validate possibility to review browser information")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'My Browser Information' page.
      3. Click 'Show Browser Information' button.
      4. Assert browser information.
      """)
  @Test
  public void showMyBrowserInformationTest() {
    HomePage homePage = new HomePage(page()).open();
    MyBrowserInfoPage myBrowserInformationPage = homePage.goToMyBrowserInformation();
    myBrowserInformationPage.toggleButtonClick();

    assertTrue(myBrowserInformationPage.isInfoVisible());
    assertFalse(myBrowserInformationPage.getValue("User Agent").isEmpty());
    assertFalse(myBrowserInformationPage.getValue("CodeName").isEmpty());
    assertFalse(myBrowserInformationPage.getValue("Name").isEmpty());
    assertFalse(myBrowserInformationPage.getValue("Version").isEmpty());
    assertFalse(myBrowserInformationPage.getValue("Platform").isEmpty());

    assertEquals("true", myBrowserInformationPage.getValue("Cookies Enabled"));
    assertEquals("Mozilla", myBrowserInformationPage.getValue("Name"));
    assertEquals("Mozilla", myBrowserInformationPage.getValue("CodeName"));
  }

  @DisplayName("[UI]. My Browser Information page. Validate possibility to hide browser information")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'My Browser Information' page.
      3. Click 'Show Browser Information' button.
      4. Assert browser information.
      5. Click 'Hide Browser Information' button.
      6. Assert that browser information is hidden.
      """)
  @Test
  public void showAndHideMyBrowserInformationTest() {
    HomePage homePage = new HomePage(page()).open();
    MyBrowserInfoPage myBrowserInformationPage = homePage.goToMyBrowserInformation();

    myBrowserInformationPage.toggleButtonClick();
    assertTrue(myBrowserInformationPage.isInfoVisible());

    myBrowserInformationPage.toggleButtonClick();
    assertFalse(myBrowserInformationPage.isInfoVisible());
  }
}

