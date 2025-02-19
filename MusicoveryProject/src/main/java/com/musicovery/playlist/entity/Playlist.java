package com.musicovery.playlist.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PLAYLIST")
@Getter
@Setter
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

    @Column(nullable = false)
    private String userId;  // 사용자 ID (이메일)

    
    private Date playlistDate;
}

