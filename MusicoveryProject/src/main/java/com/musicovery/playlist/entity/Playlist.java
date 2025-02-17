package com.musicovery.playlist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

//vo

@Data
@Entity
@Table
@SequenceGenerator(
		name = "playlist_generator",			//네임명
		sequenceName = "playlist_seq",		//시퀀스명
		initialValue = 1,						//증가치
		allocationSize = 1						//초기값
		)
public class Playlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_generator")
	private Long playlistId;			//플리 기본키
	
	@Column(length = 25, nullable = false)
	private String playlistTitle;		//플리 제목
	
	@Lob		//->더 큰 자료형 @Lob 으로 고쳐야하는지
	private String playlistComment;		//플리 설명
	
	@Lob
	private String playlistPhoto;		//대표사진
	
	@Lob
	private String userId;				//사용자 이메일
	
	
}
