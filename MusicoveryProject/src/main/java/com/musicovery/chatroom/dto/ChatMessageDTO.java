package com.musicovery.chatroom.dto;

import com.musicovery.chatroom.entity.ChatMessage;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ChatMessageDTO {
    private Long streamId;
    private String sender;
    private String content;
    private String receiver;
    private Long roomId;

    public static ChatMessageDTO fromEntity(ChatMessage chatMessage) {
        return ChatMessageDTO.builder()
                .streamId(chatMessage.getStreamId())
                .sender(chatMessage.getSender())
                .content(chatMessage.getContent())
                .receiver(chatMessage.getReceiver()) // receiver 추가
                .roomId(chatMessage.getRoomId()) // room_id 추가
                .build();
    }
}