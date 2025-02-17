package com.musicovery.playlist.service;

import java.util.List;
import java.util.Map;

import com.musicovery.playlist.entity.Playlist;

public interface PlaylistService {
    Playlist createPlaylist(String sessionId, String name, String description);
    Playlist updatePlaylist(String sessionId, String playlistId, String name, String description);
    void deletePlaylist(String sessionId, String playlistId);
    Playlist getPlaylist(String playlistId);
    List<String> getTracksInPlaylist(String playlistId);
    Map<String, Object> getPlaylistDetail(String sessionId, String playlistId);  // 상세 정보 추가
}
