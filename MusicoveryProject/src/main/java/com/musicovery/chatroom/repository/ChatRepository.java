package com.musicovery.chatroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.chatroom.entity.ChatMessage;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByStreamId(Long streamId);
}