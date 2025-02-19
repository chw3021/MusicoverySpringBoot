package com.musicovery.playlist.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.playlist.entity.Playlist;
import com.musicovery.playlist.service.PlaylistService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    /**
     * 📂 플레이리스트 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Playlist> createPlaylist(
    		HttpSession session,
            @RequestParam String name,
            @RequestParam String description) {
        Playlist playlist = playlistService.createPlaylist(session.getId(), name, description);
        return ResponseEntity.ok(playlist);
    }

    /**
     * 📝 플레이리스트 수정
     */
    @PutMapping("/update")
    public ResponseEntity<Playlist> updatePlaylist(
    		HttpSession session,
            @RequestParam String playlistId,
            @RequestParam String name,
            @RequestParam String description) {
        Playlist updatedPlaylist = playlistService.updatePlaylist(session.getId(), playlistId, name, description);
        return ResponseEntity.ok(updatedPlaylist);
    }

    /**
     * 🗑 플레이리스트 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlaylist(
    		HttpSession session,
            @RequestParam String playlistId) {
        playlistService.deletePlaylist(session.getId(), playlistId);
        return ResponseEntity.ok("삭제 완료");
    }

    /**
     * 🔍 플레이리스트 상세 조회
     */
    @GetMapping("/{playlistId}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable String playlistId) {
        return ResponseEntity.ok(playlistService.getPlaylist(playlistId));
    }

    /**
     * 🎵 플레이리스트의 트랙 ID 목록 조회
     */
    @GetMapping("/{playlistId}/tracks")
    public ResponseEntity<List<String>> getTracksInPlaylist(@PathVariable String playlistId) {
        return ResponseEntity.ok(playlistService.getTracksInPlaylist(playlistId));
    }
    
    /**
     * 📜 플레이리스트 상세 정보 조회 (곡 리스트 포함)
     */
    @GetMapping("/{playlistId}/detail")
    public ResponseEntity<Map<String, Object>> getPlaylistDetail(
            @PathVariable String playlistId,
            HttpSession session) {
        return ResponseEntity.ok(playlistService.getPlaylistDetail(session.getId(), playlistId));
    }
}

