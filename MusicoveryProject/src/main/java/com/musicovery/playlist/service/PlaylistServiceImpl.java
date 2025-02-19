package com.musicovery.playlist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicovery.playlist.domain.Playlist;
import com.musicovery.playlist.repository.PlaylistRepository;

import lombok.Setter;


@Service
public class PlaylistServiceImpl implements PlaylistService {

	@Setter(onMethod_= @Autowired)
	private PlaylistRepository playlistRepository;
	
	
	@Override
	public List<Playlist> playlistList(Playlist playlist) {
		List<Playlist> playlistList = null;
		playlistList = (List<Playlist>) playlistRepository.findAll();
		return playlistList;
	}

	@Override
	public void playlistInsert(Playlist playlist) {
		playlistRepository.save(playlist);
	}


	@Override 
	public Playlist playlistDetail(Playlist playlist) {
		return null; 
		}



	@Override
	public Playlist getPlaylist(Long playlistId) {
		Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
		Playlist updateData = playlistOptional.orElseThrow();
		
		return updateData;
	}

	
	@Override
	public void playlistUpdate(Playlist playlist) {
		Optional<Playlist> playlistOptional = playlistRepository.findById(playlist.getPlaylistId());
		
		Playlist updatePlaylist = playlistOptional.get();
		
		updatePlaylist.setPlaylistTitle(playlist.getPlaylistTitle());
		updatePlaylist.setPlaylistComment(playlist.getPlaylistComment());
		updatePlaylist.setUserId(playlist.getUserId());
		updatePlaylist.setBpmCheckbox(playlist.getBpmCheckbox());
		updatePlaylist.setConceptCheckbox(playlist.getConceptCheckbox());
		updatePlaylist.setMoodCheckbox(playlist.getMoodCheckbox());
		updatePlaylist.setMusicCheckbox(playlist.getMusicCheckbox());
		updatePlaylist.setPlaylistBPM(playlist.getPlaylistBPM());
		updatePlaylist.setPlaylistConcept(playlist.getPlaylistConcept());
		updatePlaylist.setPlaylistDate(playlist.getPlaylistDate());
		updatePlaylist.setPlaylistId(playlist.getPlaylistId());
		updatePlaylist.setPlaylistMOOD(playlist.getPlaylistMOOD());
		updatePlaylist.setPlaylistPhoto(playlist.getPlaylistPhoto());
		updatePlaylist.setPlaylistSearch(playlist.getPlaylistSearch());
		
		
		if(!playlist.getPlaylistPhoto().isEmpty()) {
			updatePlaylist.setPlaylistPhoto(playlist.getPlaylistPhoto());
		}
		playlistRepository.save(updatePlaylist);
	}	
	@Override
	public void playlistDelete(Playlist playlist) {
		playlistRepository.deleteById(playlist.getPlaylistId());
	}

	
	

}
