package test_utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.BaseTest;

/**
 * Utility class for common test assertions and validations.
 * Provides helper methods for API response status code checks and JSON schema validation.
 */
public class TestUtils {

  public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

  /**
   * Asserts that the API response status code matches the expected value.
   * Prints the response body in a pretty format if the assertion fails.
   *
   * @param expectedResponseCode the expected HTTP status code
   * @param response             the RestAssured response object
   */
  @Step("Validate response code is {expectedResponseCode}")
  public static void assertResponseCode(int expectedResponseCode, Response response) {
    Assertions.assertEquals(
        expectedResponseCode,
        response.getStatusCode(),
        "Unexpected response code: " + response.asPrettyString()
    );
  }

  /**
   * Validates the API response body against a JSON schema file from the classpath.
   * Automatically attaches the response body to the Allure report and skips
   * validation if the response is an empty array.
   *
   * @param pathToSchema path to the JSON schema file in the classpath
   * @param response     the RestAssured response object
   * @return true if validation passes or is skipped due to an empty array; false otherwise
   * @throws RuntimeException if an unexpected error occurs during validation
   */
  @Step("Validation of response schema against: {pathToSchema}")
  public static boolean assertResponseSchema(String pathToSchema, Response response) {
    boolean result = false;

    Allure.addAttachment("Response Body", "application/json", response.getBody().asPrettyString());

    try {
      if (response.getBody().asString().equals("[]")) {
        logger.info("Response for {} has empty array. Skipping schema validation.", pathToSchema);
      } else {
        response.then()
            .assertThat()
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(pathToSchema));
        logger.info("Schema validation {} PASSED", pathToSchema);
        result = true;
      }
      return result;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (!result) {
        logger.info("Schema validation {} FAILED", pathToSchema);
      }
    }
  }
}