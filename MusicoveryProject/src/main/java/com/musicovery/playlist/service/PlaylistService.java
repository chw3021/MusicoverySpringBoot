package com.musicovery.playlist.service;

import java.util.List;
import java.util.Map;

import com.musicovery.playlist.dto.PlaylistDTO;
import com.musicovery.playlist.entity.Playlist;
import com.musicovery.user.entity.User;

public interface PlaylistService {


    // 추가할 메서드 시그니처
    List<Playlist> getAllPlaylists();    
    List<Playlist> getAllPlaylistsByUser(User user);
	Playlist createPlaylist(String accessToken, PlaylistDTO playlistDTO);
    Playlist updatePlaylist(String accessToken, PlaylistDTO playlistDTO);
    void deletePlaylist(String accessToken, String playlistId);
    List<String> getTracksInPlaylist(String playlistId);
    Map<String, Object> getPlaylistDetail(String accessToken, String playlistId);  // 상세 정보 추가

	Playlist getPlaylist(String playlistId);
	
    void updatePlaylistPublicStatus(String playlistId, boolean isPublic);

}
