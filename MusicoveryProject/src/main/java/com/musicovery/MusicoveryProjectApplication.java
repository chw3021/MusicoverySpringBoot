package com.musicovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.musicovery")
public class MusicoveryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicoveryProjectApplication.class, args);
	}
}