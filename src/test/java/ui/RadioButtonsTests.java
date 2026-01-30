package ui;

import io.qameta.allure.Description;
import org.example.enums.ColorRadioButton;
import org.example.enums.SportRadioButton;
import org.example.pages.practice.HomePage;
import org.example.pages.practice.RadioButtonsPage;
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

    assertEquals(ColorRadioButton.blue, radioButtonsPage.getSelectedColor());

    radioButtonsPage.selectColor(ColorRadioButton.red);
    assertEquals(ColorRadioButton.red, radioButtonsPage.getSelectedColor());

    radioButtonsPage.selectColor(ColorRadioButton.yellow);
    assertEquals(ColorRadioButton.yellow, radioButtonsPage.getSelectedColor());

    radioButtonsPage.selectColor(ColorRadioButton.black);
    assertEquals(ColorRadioButton.black, radioButtonsPage.getSelectedColor());

    radioButtonsPage.selectColor(ColorRadioButton.blue);
    assertEquals(ColorRadioButton.blue, radioButtonsPage.getSelectedColor());

    assertTrue(radioButtonsPage.isColorDisabled(ColorRadioButton.green));
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

    assertEquals(SportRadioButton.tennis, radioButtonsPage.getSelectedSport());

    radioButtonsPage.selectSport(SportRadioButton.basketball);
    assertEquals(SportRadioButton.basketball, radioButtonsPage.getSelectedSport());

    radioButtonsPage.selectSport(SportRadioButton.football);
    assertEquals(SportRadioButton.football, radioButtonsPage.getSelectedSport());

    radioButtonsPage.selectSport(SportRadioButton.tennis);
    assertEquals(SportRadioButton.tennis, radioButtonsPage.getSelectedSport());
  }
}
