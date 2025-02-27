package com.musicovery.mail.service;

public interface MailService {
	public void sendVerificationEmail(String to, String token);
}
