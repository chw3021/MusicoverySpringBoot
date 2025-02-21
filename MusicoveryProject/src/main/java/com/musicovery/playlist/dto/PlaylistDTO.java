package com.musicovery.playlist.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
    private String playlistId;
    private String playlistTitle;
    private String playlistComment;
    private String playlistPhoto;
    private String userId;
    private Date playlistDate;
    private Boolean isPublic;
    private List<String> tracks;
}