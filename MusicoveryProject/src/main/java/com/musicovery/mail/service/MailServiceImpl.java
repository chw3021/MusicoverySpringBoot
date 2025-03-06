package com.musicovery.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${react.base_url}")
	private String reactUrl;
	
	@Override
	public void sendVerificationEmail(String to, String token) {
		String subject = "MusiCovery 회원가입 이메일 인증";
		String verificationLink = reactUrl+ "/verify?token=" + token;
		String message = "<p>이메일 인증을 위해 아래 링크를 클릭하세요:</p>" + "<a href=\"" + verificationLink + "\">이메일 인증하기</a>";

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(message, true);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
