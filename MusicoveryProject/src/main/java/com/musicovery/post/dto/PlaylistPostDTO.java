package com.musicovery.post.dto;

import java.util.Date;

import com.musicovery.playlist.entity.Playlist;
import com.musicovery.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistPostDTO {
    private Long id;
    private String title;
    private String description;
    private Playlist playlist;
    private User user;
    private Date createdDate;
    private int likeCount;
    private int replyCount;
    private int viewCount;
    private Boolean isNotice;

    public PlaylistPostDTO(Long id, String title, String description, User user, Date createdDate, int likeCount, int replyCount, int viewCount, Boolean isNotice, Playlist playlist) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.createdDate = createdDate;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.viewCount = viewCount;
        this.isNotice = isNotice;
        this.playlist = playlist;
    }
}