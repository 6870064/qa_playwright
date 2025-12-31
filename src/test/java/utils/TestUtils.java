package utils;

import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
  public static final Logger logger = LogManager.getLogger(BaseTest.class);

  @Step("Validate response code is {expectedStatus}")
  public static void assertResponseCode(int expectedResponseCode, Response response) {
    assertEquals(expectedResponseCode, response.getStatusCode(),
        "Unexpected response code: " + response.asPrettyString());
  }

  /**
   * Validates the schema of a response against a given JSON schema file.
   *
   * @param pathToSchema The relative path to the schema file.
   * @param response     The API response.
   * @return true if schema validation passed, false otherwise.
   */
  @Step("Validation of response schema")
  public static boolean assertResponseSchema(String pathToSchema, Response response) {
    boolean result = false;
    try {
      if (response.getBody().asString().equals("[]")) {
        logger.info("Response for - "
            + pathToSchema
            + "has an empty array. Skipping schema validation.");
      } else {
        response
            .then()
            .assertThat()
            .body(JsonSchemaValidator
                .matchesJsonSchemaInClasspath(pathToSchema));
        logger.info("Schema validation - " + pathToSchema + " - PASSED");
        result = true;
      }
      return result;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (!result) {
        logger.info("Schema validation - " + pathToSchema + " - FAILED");
      }
    }
  }
}
