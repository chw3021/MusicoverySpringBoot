package com.musicovery.playlist.service;

import java.util.List;

import com.musicovery.playlist.entity.Playlist;

public interface PlaylistService {

	public List<Playlist> playlistList(Playlist playlist);
	public void playlistInsert(Playlist playlist);
	public void playlistUpdate(Playlist playlist);
	public void playlistDelete(Playlist playlist);
	//public Playlist playlistDetail(Playlist playlist);	//
	public Playlist getPlaylist(Long playlistId);
	
	
}
