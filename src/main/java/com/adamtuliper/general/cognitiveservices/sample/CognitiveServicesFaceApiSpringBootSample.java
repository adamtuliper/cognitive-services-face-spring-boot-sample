package com.adamtuliper.general.cognitiveservices.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration
@ComponentScan(basePackages="com.adamtuliper.general.cognitiveservices.sample.controllers")
public class CognitiveServicesFaceApiSpringBootSample {

	public static void main(String[] args) {
		SpringApplication.run(CognitiveServicesFaceApiSpringBootSample.class, args);
	}
}
