package com.infobip.validation.mvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MvcApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MvcApplication.class).run(args);
	}
}
