package org.example.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.enums.ColorRadioButton;
import org.example.enums.SportRadioButton;

public class RadioButtonsPage extends BasePage {
  private final String COLOR_RADIO_BUTTON = "input[name='color'][value='%s']";
  private final String SPORT_RADIO_BUTTON = "input[name='sport'][value='%s']";

  public RadioButtonsPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return "/radio-buttons";
  }

  @Step("Selecting a {color} radio button")
  public void selectColor(ColorRadioButton color) {
    page.locator(String.format(COLOR_RADIO_BUTTON, color)).check();
  }

  @Step("Getting a color of the chosen radio button")
  public ColorRadioButton getSelectedColor() {
    String value = page.locator("input[name='color']:checked").getAttribute("value");
    return ColorRadioButton.valueOf(value);
  }

  @Step("Selecting a {sport} radio button")
  public void selectSport(SportRadioButton sport) {
    page.locator(String.format(SPORT_RADIO_BUTTON, sport)).check();
  }

  @Step("Getting a sport title of the chosen radio button")
  public SportRadioButton getSelectedSport() {
    String value = page.locator("input[name='sport']:checked").getAttribute("value");
    return SportRadioButton.valueOf(value);
  }

  @Step("Check that a radio button with a {color} is disabled")
  public boolean isColorDisabled(ColorRadioButton color) {
    return page.locator(String.format(COLOR_RADIO_BUTTON, color)).isDisabled();
  }
}
