package com.musicovery.mail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.mail.entity.EmailVerificationToken;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long>{
	 Optional<EmailVerificationToken> findByToken(String token);
}
