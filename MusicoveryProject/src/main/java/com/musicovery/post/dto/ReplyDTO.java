package com.musicovery.post.dto;

import java.time.LocalDateTime;

import com.musicovery.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {

    private Long id;
    private String content;
    private User user;
    private Long playlistPostId;
    private LocalDateTime createdDate;
}
