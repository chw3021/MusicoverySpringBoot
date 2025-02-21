package com.musicovery.playlist.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.playlist.dto.PlaylistDTO;
import com.musicovery.playlist.entity.Playlist;
import com.musicovery.playlist.service.PlaylistService;
import com.musicovery.user.entity.User;
import com.musicovery.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService; 

    /**
     * 📂 플레이리스트 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Playlist> createPlaylist(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody PlaylistDTO playlistDTO) {
        String accessToken = bearerToken.replace("Bearer ", "");
        Playlist playlist = playlistService.createPlaylist(accessToken, playlistDTO);
        return ResponseEntity.ok(playlist);
    }
    /**
     * 📝 플레이리스트 수정
     */
    @PostMapping("/update")
    public ResponseEntity<Playlist> updatePlaylist(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody PlaylistDTO playlistDTO) {
        Playlist updatedPlaylist = playlistService.updatePlaylist(bearerToken, playlistDTO);
        return ResponseEntity.ok(updatedPlaylist);
    }

    /**
     * 🗑 플레이리스트 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlaylist(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam String playlistId) {
        playlistService.deletePlaylist(bearerToken, playlistId);
        return ResponseEntity.ok("삭제 완료");
    }

    /**
     * 🔍 플레이리스트 상세 조회
     */
    @GetMapping("/detail/{playlistId}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable String playlistId) {
        return ResponseEntity.ok(playlistService.getPlaylist(playlistId));
    }

    /**
     * 🎵 플레이리스트의 트랙 ID 목록 조회
     */
    @GetMapping("/tracks/{playlistId}")
    public ResponseEntity<List<String>> getTracksInPlaylist(@PathVariable String playlistId) {
        return ResponseEntity.ok(playlistService.getTracksInPlaylist(playlistId));
    }

    /**
     * 📜 플레이리스트 상세 정보 조회 (곡 리스트 포함)
     */
    @GetMapping("/detail/{playlistId}/full")
    public ResponseEntity<Map<String, Object>> getPlaylistDetail(
            @PathVariable String playlistId,
            HttpSession session) {
        return ResponseEntity.ok(playlistService.getPlaylistDetail(session.getId(), playlistId));
    }

    /**
     * 사용자별 플레이리스트 조회
     */
    @GetMapping("/user/{userId}")
    public List<Playlist> getAllPlaylistsByUser(@PathVariable String userId) {
        User user = userService.findByUserId(userId); // userId를 통해 User 객체 조회
        return playlistService.getAllPlaylistsByUser(user);
    }
}
