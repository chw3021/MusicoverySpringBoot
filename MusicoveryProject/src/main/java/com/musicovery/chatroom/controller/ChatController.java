package com.musicovery.chatroom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.chatroom.dto.ChatMessageDTO;
import com.musicovery.chatroom.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{streamId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(@PathVariable Long streamId) {
        List<ChatMessageDTO> messages = chatService.getMessagesByStreamId(streamId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/send")
    public ResponseEntity<ChatMessageDTO> sendMessage(@RequestBody ChatMessageDTO chatMessageDto) {
        ChatMessageDTO savedMessage = chatService.saveMessage(chatMessageDto);
        return ResponseEntity.ok(savedMessage);
    }
}