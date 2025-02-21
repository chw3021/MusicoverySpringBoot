package com.musicovery.chatroom.service;

import java.util.List;

import com.musicovery.chatroom.dto.ChatMessageDTO;

public interface ChatService {
    List<ChatMessageDTO> getMessagesByStreamId(Long streamId); // 특정 스트리밍 ID의 채팅 메시지 가져오기
    ChatMessageDTO saveMessage(ChatMessageDTO chatMessageDto); // 채팅 메시지 저장하기
}