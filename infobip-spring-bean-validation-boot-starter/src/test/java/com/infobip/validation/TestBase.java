package com.infobip.validation;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class TestBase {

    @Value("${local.server.port}")
    private Integer port;

    @Before
    public void setUpRestAssured() throws Exception {
        RestAssured.port = port;
    }
}
