package com.musicovery.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.playlist.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {


}
