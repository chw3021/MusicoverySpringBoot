package com.musicovery.chatroom.chatwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.chatroom.entity.ChatMessage;

@RestController
public class ChatWebSocketMessageController {

    @MessageMapping("/chat/{streamId}")
    @SendTo("/topic/chat/{streamId}") // 클라이언트가 구독할 수 있는 경로
    public ChatMessage sendMessage(ChatMessage message) {
        return message; // 받은 메시지를 그대로 반환
    }
}
