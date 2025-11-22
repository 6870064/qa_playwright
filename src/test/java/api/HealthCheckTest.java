package api;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.enums.HttpStatus;
import org.example.responses.BaseResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.example.constants.Constants.NOTES_API_RUNNING;
import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.assertResponseCode;

public class HealthCheckTest extends BaseApiTest {

  @DisplayName("[API. Health]. GET method. Check the health of the API Notes service")
  @Description("""
      1. Get health of the API Notes service.
      2. Assert response.
      """)
  @Test
  public void healthCheckTest() {
    Response response = getHealthCheck();
    assertResponseCode(HttpStatus.OK.code(), response);

    BaseResponse body = response.as(BaseResponse.class);

    assertAll("Validate note not found response",
        () -> assertTrue(body.success(), "Success is true"),
        () -> assertEquals(HttpStatus.OK.code(), body.status(), "Response status is 200 OK"),
        () -> assertEquals(NOTES_API_RUNNING, body.message())
    );

  }
}
