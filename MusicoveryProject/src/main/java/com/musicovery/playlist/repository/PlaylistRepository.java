package com.musicovery.playlist.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.musicovery.playlist.entity.Playlist;
import com.musicovery.user.entity.User;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {

	List<Playlist> findAllByUser(User user);

	Optional<Playlist> findByPlaylistId(String playlistId);

	// 🔹 총 플레이리스트 수 조회
	@Query("SELECT COUNT(p) FROM Playlist p")
	long countAllPlaylists();

	// 🔹 최근 7일간 생성된 플레이리스트 수 조회
	@Query("SELECT COUNT(p) FROM Playlist p WHERE p.playlistDate >= :startDate")
	long countPlaylistsSince(Date startDate);

	// 🔹 최근 7일간 매일 생성된 플레이리스트 수
	@Query("SELECT COUNT(p) FROM Playlist p WHERE FUNCTION('DATE', p.playlistDate) = FUNCTION('DATE', :date)")
	long countPlaylistsByDate(Date date);
}
