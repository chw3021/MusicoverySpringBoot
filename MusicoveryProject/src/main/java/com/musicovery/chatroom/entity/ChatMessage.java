package com.musicovery.chatroom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*	roomId	같은 채팅방 내 사용자들만 채팅할 수 있도록 식별하는 ID
	sender	메시지를 보낸 사용자 닉네임
	receiver	메시지를 받을 사용자 닉네임 (1:1 채팅 가능)
	content	채팅 내용
	timestamp	채팅이 전송된 시간
*/




@Entity
@Table(name = "chat_message")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = true) // receiver 필드가 선택적임을 설정
    private String receiver;
    @Column(nullable = true) // room_id 필드가 선택적임을 설정
    private Long roomId;
    
    
    private Long streamId;
    private String sender;
    private String content;
}