package com.infobip.validation.mvc;

import static org.assertj.core.api.BDDAssertions.then;

import com.infobip.validation.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

public class ValidatedRestControllerTest extends TestBase {

    @Test
    void shouldReturn400ForInvalidRequest() {
        // when
        HttpStatusCode statusCode = restClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bar")
                        .queryParam("bar", 2)
                        .build())
                .exchange((request, response) -> response.getStatusCode());

        // then
        then(statusCode.value()).isEqualTo(400);
    }

    @Test
    void shouldReturn200ForValidRequest() {
        // when
        HttpStatusCode statusCode = restClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bar")
                        .queryParam("bar", 1)
                        .build())
                .exchange((request, response) -> response.getStatusCode());

        // then
        then(statusCode.value()).isEqualTo(200);
    }
}
