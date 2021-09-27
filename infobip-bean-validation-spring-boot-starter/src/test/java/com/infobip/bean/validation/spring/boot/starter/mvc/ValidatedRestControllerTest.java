package com.infobip.bean.validation.spring.boot.starter.mvc;

import com.infobip.bean.validation.spring.boot.starter.TestBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

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
