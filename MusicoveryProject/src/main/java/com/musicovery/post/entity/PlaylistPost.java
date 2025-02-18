package com.musicovery.post.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private String playlistId; // Spotify 플레이리스트 ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 플레이리스트 작성자

    @OneToMany(mappedBy = "playlistPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replys = new ArrayList<>();

    @OneToMany(mappedBy = "playlistPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    private int likeCount;
    private int replyCount;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate; // 게시글 작성 날짜
}
