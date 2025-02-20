package com.musicovery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
	        .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (필요에 따라 활성화)
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/**").permitAll() // 인증 없이 접근 가능
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form.disable()) // 폼 로그인 비활성화
	        .httpBasic(httpBasic -> httpBasic.disable()); // HTTP Basic 인증 비활성화


        return http.build();
    }
}