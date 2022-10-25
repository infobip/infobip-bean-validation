package com.infobip.validation.mvc;

import static org.assertj.core.api.BDDAssertions.then;

import com.infobip.validation.TestBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class ValidatedRestControllerTest extends TestBase {

    @Test
    void shouldReturn400ForInvalidRequest() {
        // when
        var response = RestAssured.with()
                                  .queryParam("bar", 2)
                                  .get("/bar")
                                  .thenReturn();

        // then
        then(response.getStatusCode()).isEqualTo(400);
    }

    @Test
    void shouldReturn200ForValidRequest() {
        // when
        var response = RestAssured.with()
                                  .queryParam("bar", 1)
                                  .get("/bar")
                                  .thenReturn();

        // then
        then(response.getStatusCode()).isEqualTo(200);
    }
}
