package org.example.pages;

import com.microsoft.playwright.Page;
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

  public void selectColor(ColorRadioButton color) {
    page.locator(String.format(COLOR_RADIO_BUTTON, color)).check();
  }

  public ColorRadioButton getSelectedColor() {
    String value = page.locator("input[name='color']:checked").getAttribute("value");

    return ColorRadioButton.valueOf(value);
  }

  public void selectSport(SportRadioButton sport) {
    page.locator(String.format(SPORT_RADIO_BUTTON, sport)).check();
  }

  public SportRadioButton getSelectedSport() {
    String value = page.locator("input[name='sport']:checked").getAttribute("value");

    return SportRadioButton.valueOf(value);
  }

  public boolean isColorDisabled(ColorRadioButton color) {
    return page.locator(String.format(COLOR_RADIO_BUTTON, color)).isDisabled();
  }
}
