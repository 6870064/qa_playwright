package test_utils;

import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.BaseTest;

public class TestUtils {

  public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

  @Step("Validate response code is {expectedStatus}")
  public static void assertResponseCode(int expectedResponseCode, Response response) {
    Assertions.assertEquals(
        expectedResponseCode,
        response.getStatusCode(),
        "Unexpected response code: " + response.asPrettyString()
    );
  }

  @Step("Validation of response schema")
  public static boolean assertResponseSchema(String pathToSchema, Response response) {
    boolean result = false;
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