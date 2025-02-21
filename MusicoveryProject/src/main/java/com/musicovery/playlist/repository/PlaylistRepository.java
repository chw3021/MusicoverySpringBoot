package com.musicovery.playlist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.playlist.entity.Playlist;
import com.musicovery.user.entity.User;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {

    List<Playlist> findAllByUser(User user);

	Optional<Playlist> findByPlaylistId(String playlistId);

}
