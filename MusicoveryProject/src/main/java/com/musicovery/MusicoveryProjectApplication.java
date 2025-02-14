package com.musicovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.musicovery", exclude = SecurityAutoConfiguration.class)
public class MusicoveryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicoveryProjectApplication.class, args);
	}
}