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

	Playlist getPlaylist(String accessToken, String playlistId);

	Map<String, Object> getPlaylistDetail(String accessToken, String playlistId); // 상세 정보 추가

	void updatePlaylistPublicStatus(String playlistId, Boolean isPublic);

	/**
	 * 🔍 플레이리스트 조회
	 */
	String getTracksInPlaylist(String accessToken, String playlistId);

	// 🔹 총 플레이리스트 수 조회
	long getTotalPlaylists();

	// 🔹 최근 7일간 생성된 플레이리스트 수 조회
	List<Long> getWeeklyPlaylists();

}
