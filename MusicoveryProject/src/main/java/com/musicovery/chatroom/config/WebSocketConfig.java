package com.musicovery.chatroom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Value("${react.base_url}")
	private String reactUrl;
	
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat") // WebSocket 엔드포인트 설정
                .setAllowedOrigins(reactUrl) // 프론트엔드의 주소 허용
        		//.setAllowedOrigins("http://192.168.130.10:3000") // 프론트엔드의 주소 허용
                .withSockJS(); // SockJS 지원
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // 구독 prefix
        registry.setApplicationDestinationPrefixes("/app"); // 메시지 보내는 prefix
    }

}