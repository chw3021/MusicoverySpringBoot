package com.musicovery.post.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.musicovery.playlist.entity.Playlist;
import com.musicovery.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class PlaylistPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist; // Spotify 플레이리스트 ID
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 플레이리스트 작성자


    @OneToMany(mappedBy = "playlistPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replys = new ArrayList<>();

    @OneToMany(mappedBy = "playlistPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();


    @Column(name = "like_count")  // 실제 컬럼명 매핑
    private int likeCount;

    @Column(name = "reply_count")  // 실제 컬럼명 매핑
    private int replyCount;

    @Column(name = "view_count")  // 실제 컬럼명 매핑
    private int viewCount;
    
    @Column(name = "created_date")  // 실제 컬럼명 매핑
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    private Boolean isNotice;
}
