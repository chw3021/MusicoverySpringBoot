package com.musicovery.chatroom.service;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.musicovery.chatroom.dto.ChatMessageDTO;

public interface ChatService {
    List<ChatMessageDTO> getMessagesByStreamId(Long streamId); // 특정 스트리밍 ID의 채팅 메시지 가져오기
    ChatMessageDTO saveMessage(ChatMessageDTO chatMessageDto); // 채팅 메시지 저장하기
    
    void addSession(WebSocketSession session); // WebSocket 세션 추가
    void removeSession(WebSocketSession session); // WebSocket 세션 제거
    void sendMessageToAll(ChatMessageDTO message); // 모든 클라이언트에 메시지 전송
}