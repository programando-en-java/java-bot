package com.programandoenjava.java_bot;

import org.springframework.boot.SpringApplication;

public class TestJavaBotApplication {

	public static void main(String[] args) {
		SpringApplication.from(JavaBotApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
