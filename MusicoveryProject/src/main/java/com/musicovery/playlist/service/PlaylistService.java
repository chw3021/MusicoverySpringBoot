package com.musicovery.playlist.service;

import java.util.List;
import java.util.Map;

import com.musicovery.playlist.domain.PlaylistDTO;
import com.musicovery.playlist.entity.Playlist;

public interface PlaylistService {

    // 추가할 메서드 시그니처
    List<Playlist> getAllPlaylists();    
    List<Playlist> getAllPlaylistsByUserId(String userId);
    Playlist createPlaylist(String accessToken, String name, String description);
    Playlist updatePlaylist(String accessToken, PlaylistDTO playlistDTO);
    void deletePlaylist(String accessToken, String playlistId);
    List<String> getTracksInPlaylist(String playlistId);
    Map<String, Object> getPlaylistDetail(String accessToken, String playlistId);  // 상세 정보 추가
	/**
	 * 🔍 플레이리스트 조회
	 */
	Playlist getPlaylist(String playlistId);
}
