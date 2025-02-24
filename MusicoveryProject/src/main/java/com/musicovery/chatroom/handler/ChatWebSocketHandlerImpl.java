package com.musicovery.chatroom.handler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicovery.chatroom.dto.ChatMessageDTO;
import com.musicovery.chatroom.service.ChatService;

@Component
public class ChatWebSocketHandlerImpl extends TextWebSocketHandler {
    @Autowired
    private ChatService chatService; // ChatService를 주입받음

    private Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 인스턴스 생성

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        chatService.addSession(session); // 세션 추가
        System.out.println("WebSocket 연결 성공: " + session.getId());
        System.out.println("현재 세션 수: " + sessions.size()); // 현재 세션 수 출력
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("수신된 메시지: " + message.getPayload());
        ChatMessageDTO chatMessageDto = parseMessage(message.getPayload());
        if (chatMessageDto != null) {
            chatMessageDto = chatService.saveMessage(chatMessageDto); // 메시지 저장
            sendMessageToAllSessions(chatMessageDto); // 모든 클라이언트에 메시지 전송
        }
    }

    private void sendMessageToAllSessions(ChatMessageDTO chatMessageDto) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(convertToJson(chatMessageDto)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket 연결 종료: " + session.getId());
        chatService.removeSession(session); // 세션 제거
        sessions.remove(session);
    }

    private String convertToJson(ChatMessageDTO chatMessageDto) {
        try {
            return objectMapper.writeValueAsString(chatMessageDto); // 객체를 JSON 문자열로 변환
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // 변환 실패 시 빈 JSON 객체 반환
        }
    }

    private ChatMessageDTO parseMessage(String message) {
        try {
            return objectMapper.readValue(message, ChatMessageDTO.class); // JSON 문자열을 객체로 변환
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // 변환 실패 시 null 반환
        }
    }
}
