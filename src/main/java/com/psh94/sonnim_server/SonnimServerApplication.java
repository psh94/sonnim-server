package com.psh94.sonnim_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SonnimServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SonnimServerApplication.class, args);
	}

}
