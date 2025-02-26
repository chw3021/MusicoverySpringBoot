package com.musicovery.playlist.entity;

import java.util.Date;
import java.util.List;

import com.musicovery.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "playlist")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    @Id
    private String playlistId;  // Spotify에서 제공하는 ID

    @Column(length = 25, nullable = false)
    private String playlistTitle;  // 플레이리스트 제목

    @Lob
    private String playlistComment;  // 플레이리스트 설명

    @Lob
    private String playlistPhoto;  // 대표 사진 (URL 저장 가능)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // 사용자 ID (이메일)

    
    @Temporal(TemporalType.TIMESTAMP)
    private Date playlistDate;  //생성일자
    
    private Boolean isPublic;
    
    @Transient // DB에 저장하지 않음
    private List<String> trackIds;
}

