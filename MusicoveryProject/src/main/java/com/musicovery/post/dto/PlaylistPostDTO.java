package com.musicovery.post.dto;

import java.util.Date;

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
    private User user;
    private Date createdDate;
    private int likeCount;
    private int replyCount;
    private int viewCount;
}