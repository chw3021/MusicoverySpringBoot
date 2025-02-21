package com.musicovery.chatroom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.musicovery.chatroom.dto.ChatMessageDTO;
import com.musicovery.chatroom.entity.ChatMessage;
import com.musicovery.chatroom.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

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
        ChatMessage chatMessage = ChatMessage.builder()
                .streamId(chatMessageDto.getStreamId())
                .sender(chatMessageDto.getSender())
                .content(chatMessageDto.getContent())
                .build();

        ChatMessage savedMessage = chatRepository.save(chatMessage);
        return ChatMessageDTO.fromEntity(savedMessage);
    }
}