package com.musicovery.playlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.playlist.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {

	List<Playlist> findAllByUserId(String userId);


}
