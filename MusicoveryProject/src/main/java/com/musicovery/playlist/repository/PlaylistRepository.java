package com.musicovery.playlist.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicovery.playlist.entity.Playlist;

//dao
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {

}
