package com.musicovery.user.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserSignupDTO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserServiceTest {

	@Setter(onMethod_ = @Autowired)
	private UserService userService;

	@Test
	public void userInsertTest() {
	    // Given
	    UserSignupDTO userSignupDTO = new UserSignupDTO();
	    userSignupDTO.setEmail("musicovery@gmail.com");
	    userSignupDTO.setPasswd("1234");
	    userSignupDTO.setNickname("testUser3");
	    userSignupDTO.setPhone("010-1234-8765");
	    userSignupDTO.setAddress("Seoul");

	    // When
	    UserDTO savedUser = userService.signup(userSignupDTO);

	    // Then
	    assertNotNull(savedUser);
	    assertTrue(savedUser.isActive()); // isActive가 true인지 확인
	    log.info("User inserted with ID: {}", savedUser.getId());
	}

	
	// @Test
	public void loginTest() {
	    // Given
	    UserDTO userDTO = new UserDTO();
	    userDTO.setEmail("musicoveryUser2@gmail.com"); // 로그인할 이메일
	    userDTO.setPasswd("musicovery1234"); // 로그인할 비밀번호

	    // When
	    UserDTO loggedInUser = userService.login(userDTO);

	    // Then
	    assertNotNull(loggedInUser); // 로그인 성공 시 반환된 UserDTO가 null이 아님을 확인
	    log.info("User logged in with Email: {}", loggedInUser.getEmail());
	}
}
