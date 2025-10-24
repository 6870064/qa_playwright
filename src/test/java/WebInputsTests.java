import org.example.helpers.DataGenerator;
import org.example.pages.HomePage;
import org.example.pages.WebInputsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebInputsTests extends BaseTest {
  public static final String START_DATE = "1990-01-01";
  public static final String END_DATE = "2020-01-01";

  @Test
  public void InputDataTest() {
    String inputNumber = String.valueOf(new DataGenerator().generateRandomInt(8, 10));
    String inputText = new DataGenerator().randomString(10);
    String password = new DataGenerator().generateRandomPassword(6, 10);
    String inputDate = new DataGenerator().generateRandomDate(START_DATE, END_DATE);

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
