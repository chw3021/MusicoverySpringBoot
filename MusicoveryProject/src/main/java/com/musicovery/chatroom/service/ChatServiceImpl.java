package com.musicovery.chatroom.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicovery.chatroom.dto.ChatMessageDTO;
import com.musicovery.chatroom.entity.ChatMessage;
import com.musicovery.chatroom.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final List<WebSocketSession> sessions = new ArrayList<>(); // WebSocket 세션 목록
    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 인스턴스 생성

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public List<ChatMessageDTO> getMessagesByStreamId(Long streamId) {
        return chatRepository.findByStreamId(streamId)
                .stream()
                .map(ChatMessageDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageDTO saveMessage(ChatMessageDTO chatMessageDto) {
        // 메시지 저장 로직
        ChatMessage chatMessage = ChatMessage.builder()
                .streamId(chatMessageDto.getStreamId())
                .sender(chatMessageDto.getSender())
                .content(chatMessageDto.getContent())
                .build();

        ChatMessage savedMessage = chatRepository.save(chatMessage);
        ChatMessageDTO savedMessageDto = ChatMessageDTO.fromEntity(savedMessage);

        // 새로운 메시지를 모든 클라이언트에 전송
        sendMessageToAll(savedMessageDto); 
        return savedMessageDto; // 저장된 메시지 반환
    }

    @Override
    public void addSession(WebSocketSession session) {
        sessions.add(session); // WebSocket 세션 추가
    }

    @Override
    public void removeSession(WebSocketSession session) {
        sessions.remove(session); // WebSocket 세션 제거
    }

    @Override
    public void sendMessageToAll(ChatMessageDTO message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message))); // 메시지 전송
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}