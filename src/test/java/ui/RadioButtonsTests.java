package ui;

import io.qameta.allure.Description;
import org.example.pages.HomePage;
import org.example.pages.RadioButtonsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RadioButtonsTests extends BaseTest {

  @DisplayName("[UI]. Radio Buttons page. Validate possibility to check different color radio buttons")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Radio Buttons' page.
      3. Assert that 'blue' color radio button is chosen
      4. Select 'Red' radio button.
      5. Assert chosen radio button.
      6. Select 'Yellow' radio button.
      7. Assert chosen radio button.
      8. Select 'Black' radio button.
      9. Assert chosen radio button.
      10. Select 'Blue' radio button.
      11. Assert chosen radio button.
      12. Assert impossibility to chose 'Green' radio button.
      """)
  @Test
  public void checkColorRadioButtonsTest() {
    HomePage homePage = new HomePage(page()).open();
    RadioButtonsPage radioButtonsPage = homePage.goToRadioButtons();

    assertEquals("blue", radioButtonsPage.getSelectedColor());

    radioButtonsPage.selectColor("red");
    assertEquals("red", radioButtonsPage.getSelectedColor());

    radioButtonsPage.selectColor("yellow");
    assertEquals("yellow", radioButtonsPage.getSelectedColor());

    radioButtonsPage.selectColor("black");
    assertEquals("black", radioButtonsPage.getSelectedColor());

    radioButtonsPage.selectColor("blue");
    assertEquals("blue", radioButtonsPage.getSelectedColor());

    assertTrue(radioButtonsPage.isColorDisabled("green"));
  }

  @DisplayName("[UI]. Radio Buttons page. Validate possibility to check different sport radio buttons")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Radio Buttons' page.
      3. Assert that 'Tennis' sport radio button is chosen
      4. Select 'Basketball' radio button.
      5. Assert chosen radio button.
      6. Select 'Football' radio button.
      7. Assert chosen radio button.
      8. Select 'Tennis' radio button.
      9. Assert chosen radio button.
      """)
  @Test
  public void checkSportRadioButtonsTest() {
    HomePage homePage = new HomePage(page()).open();
    RadioButtonsPage radioButtonsPage = homePage.goToRadioButtons();

    assertEquals("tennis", radioButtonsPage.getSelectedSport());

    radioButtonsPage.selectSport("basketball");
    assertEquals("basketball", radioButtonsPage.getSelectedSport());

    radioButtonsPage.selectSport("football");
    assertEquals("football", radioButtonsPage.getSelectedSport());

    radioButtonsPage.selectSport("tennis");
    assertEquals("tennis", radioButtonsPage.getSelectedSport());
  }
}
