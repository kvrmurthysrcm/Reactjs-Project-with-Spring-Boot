package com.reactjs;

import com.reactjs.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;


@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
@EnableCaching
public class ReactjsProjectWithSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactjsProjectWithSpringBootApplication.class, args);
	}
}
