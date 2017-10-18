package com.infobip.validation.mvc;

import com.infobip.validation.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class ValidatedRestControllerTest extends TestBase {

    @Test
    public void shouldReturn400ForInvalidRequest() {
        // when
        Response response = RestAssured.with()
                                       .queryParam("bar", 2)
                                       .get("/bar")
                                       .thenReturn();

        // then
        then(response.getStatusCode()).isEqualTo(400);
    }

    @Test
    public void shouldReturn200ForValidRequest() {
        // when
        Response response = RestAssured.with()
                                       .queryParam("bar", 1)
                                       .get("/bar")
                                       .thenReturn();;

        // then
        then(response.getStatusCode()).isEqualTo(200);
    }
}
