package com.musicovery.playlist.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//vo

@Setter
@Getter
@ToString
@Entity
@Table(name = "playlist")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "playlist_generator",
		sequenceName="playlist_seq",
		initialValue = 1,
		allocationSize = 1 )
public class Playlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "playlist_generator")
	private Long playlistId;			//플리 기본키
	
	@CreationTimestamp
	@ColumnDefault(value="sysdate")
	private String playlistDate;		//플리 생성일자
	

	@Lob
	private String musicCheckbox;		//플리 곡검색 체크박스
	
	@Column(length = 50, nullable = false)
	private String playlistSearch;		//플리 곡검색
	
	@Lob
	private String conceptCheckbox;		//플리 장르 체크박스
	
	@Lob
	private String playlistConcept;		//플리 장르
	
	@Lob
	private String bpmCheckbox;		//플리 BPM 체크박스
	
	@Lob
	private String playlistBPM;		//플리 BPM
	
	@Lob
	private String moodCheckbox;		//플리 분위기 체크박스
	
	@Column(length = 25, nullable = false)
	private String playlistMOOD;		//플리 분위기
	
	@Lob
	private String playlistPhoto;		//대표사진
	
	@Column(length = 25, nullable = false)
	private String playlistTitle;		//플리 제목
	
	@Lob		
	private String playlistComment;		//플리 설명
	
	@Lob
	private String userId;				//사용자 이메일
	
	
}
