package com.musicovery.chatroom.chatwebsocket;

import lombok.Data;

@Data
public class ChatMessage {
    private String streamId;
    private String sender;
    private String content;
    private String receiver;
}