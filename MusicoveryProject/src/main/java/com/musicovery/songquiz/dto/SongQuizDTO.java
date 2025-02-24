package com.musicovery.songquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongQuizDTO {
    private String artist;
    private String title;
    private String lyrics;
}
