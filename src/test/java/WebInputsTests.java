import org.example.helpers.DataGenerator;
import org.example.pages.HomePage;
import org.example.pages.WebInputsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebInputsTests extends BaseTest {
  @Test
  public void InputDataTest() {
    //String inputNumber = String.valueOf(new DataGenerator().generateRandomPassword(8, 10));
    String inputNumber = "6854944";
        String inputText = "Test text";
    String password = "qwerty87";
    String inputDate = "2025-10-23";

    HomePage homePage = new HomePage(page).open();
    WebInputsPage webInputsPage = new WebInputsPage(page).goToWebInputs();

    webInputsPage.inputNumber(inputNumber);
    webInputsPage.inputText(inputText);
    webInputsPage.inputPassword(password);
    webInputsPage.inputDate(inputDate);
    webInputsPage.displayInputsClick();

    assertAll("Check output value in the fields",
        ()-> assertEquals(inputNumber, webInputsPage.getOutputNumber()),
        ()-> assertEquals(inputText, webInputsPage.getOutputText()),
        ()-> assertEquals(password, webInputsPage.getOutputPassword()),
        ()-> assertEquals(inputDate, webInputsPage.getOutputDate()));

    //HomePage homePage = new HomePage(page).open();
    //    RegisterPage registerPage = homePage.goToRegister();
    //    LoginPage loginPage = registerPage
    //        .fill(u.getUsername(), u.getPassword(), u.getConfirmPassword())
    //        .submitSuccess();
    //
    //    //login
    //    SecurePage securePage = loginPage
    //        .fill(u.getUsername(), u.getPassword())
    //        .login();
    //
    //    //подождать появления приветствия
    //    securePage.waitUntilLoaded(u.getUsername());

  }
}
