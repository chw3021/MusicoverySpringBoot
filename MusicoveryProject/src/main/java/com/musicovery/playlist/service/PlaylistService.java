package com.musicovery.playlist.service;

import java.util.List;
import java.util.Map;

import com.musicovery.playlist.entity.Playlist;

public interface PlaylistService {
<<<<<<< HEAD

	public List<Playlist> playlistList(Playlist playlist);
	public void playlistInsert(Playlist playlist);
	public void playlistUpdate(Playlist playlist);
	public void playlistDelete(Playlist playlist);
	public Playlist playlistDetail(Playlist playlist);	
	public Playlist getPlaylist(Long playlistId);
	
	
=======
    Playlist createPlaylist(String accessToken, String name, String description);
    Playlist updatePlaylist(String accessToken, String playlistId, String name, String description);
    void deletePlaylist(String accessToken, String playlistId);
    Playlist getPlaylist(String playlistId);
    List<String> getTracksInPlaylist(String playlistId);
    Map<String, Object> getPlaylistDetail(String accessToken, String playlistId);  // 상세 정보 추가
>>>>>>> refs/remotes/origin/main
}
