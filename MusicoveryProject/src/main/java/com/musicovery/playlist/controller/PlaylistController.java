package com.musicovery.playlist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.musicovery.playlist.domain.Playlist;
import com.musicovery.playlist.service.PlaylistService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/playlist/*")
@RequiredArgsConstructor
public class PlaylistController {

	
	private final PlaylistService playlistService;
	
	
	@GetMapping("/playlistList")
	public String playlistList(Playlist playlist, Model model) {
		List<Playlist> playlistList = playlistService.playlistList(playlist);
		model.addAttribute("playlistList", playlistList);
		
		return "musicovery/playlist/playlistList";
	}

	
	@GetMapping("/insertForm")
	public String insertForm(Playlist playlist) {
		return "musicovery/playlist/insertForm";
	}
	
	
	@PostMapping("/playlistInsert")
	public String playlistInsert(Playlist playlist) {
		playlistService.playlistInsert(playlist);
		return "redirect:/playlist/playlistList";
	}

	
	

	@PostMapping("/updateForm")
	public String updateForm(Playlist playlist, Model model) {
		Playlist updateData = playlistService.getPlaylist(playlist.getPlaylistId());
		model.addAttribute("updateData", updateData);
		return "musicovery/playlist/updateForm";
	}


	
	@PostMapping("/playlistUpdate")
	public String playlistUpdate(Playlist playlist) {
		playlistService.playlistUpdate(playlist);
		
		return "redirect:/playlist/"+ playlist.getPlaylistId();
	}
	
	
	@PostMapping("/playlistDelete")
	public String playlistDelete(Playlist playlist) {
		playlistService.playlistDelete(playlist);
		
		return "redirect:/playlist/playlistList";
	}

}
