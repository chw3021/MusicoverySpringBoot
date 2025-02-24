package com.musicovery.playlist.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.musicovery.file.service.FileStorageService;
import com.musicovery.playlist.dto.PlaylistDTO;
import com.musicovery.playlist.entity.Playlist;
import com.musicovery.playlist.service.PlaylistService;
import com.musicovery.user.entity.User;
import com.musicovery.user.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService; 
    private final FileStorageService fileStorageService;

    /**
     * 📂 플레이리스트 생성
     */


    @PostMapping("/create")
    public ResponseEntity<Playlist> createPlaylist(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("playlistTitle") String playlistTitle,
            @RequestParam("playlistComment") String playlistComment,
            @RequestParam("playlistDate") String playlistDate,
            @RequestParam("isPublic") boolean isPublic,
            @RequestParam("userId") String userId,
            @RequestParam("tracks") List<String> tracks,
            @RequestParam(value = "playlistPhoto", required = false) MultipartFile playlistPhoto) {

        String accessToken = bearerToken.replace("Bearer ", "");
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setPlaylistTitle(playlistTitle);
        playlistDTO.setPlaylistComment(playlistComment);
        playlistDTO.setPlaylistDate(Date.valueOf(playlistDate));
        playlistDTO.setIsPublic(isPublic);
        playlistDTO.setUserId(userId);
        playlistDTO.setTracks(tracks);

        // 파일 처리 로직
        if (playlistPhoto != null && !playlistPhoto.isEmpty()) {
            try {
                String fileName = fileStorageService.storeFile(playlistPhoto);
                String filePath = "/images/" + fileName; // 정적 리소스 경로 설정
                playlistDTO.setPlaylistPhoto(filePath); // 파일 경로 DTO에 저장
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Playlist playlist = playlistService.createPlaylist(accessToken, playlistDTO);
        return ResponseEntity.ok(playlist);
    }

    /**
     * 📝 플레이리스트 수정
     */

    @PostMapping("/update")
    public ResponseEntity<Playlist> updatePlaylist(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("playlistId") String playlistId,
            @RequestParam("playlistTitle") String playlistTitle,
            @RequestParam("playlistComment") String playlistComment,
            @RequestParam("playlistDate") String playlistDate,
            @RequestParam("isPublic") boolean isPublic,
            @RequestParam("userId") String userId,
            @RequestParam("tracks") List<String> tracks, // String으로 받음
            @RequestParam(value = "playlistPhoto", required = false) MultipartFile playlistPhoto,
            @RequestParam(value = "existingPlaylistPhoto", required = false) String existingPlaylistPhoto) {

        String accessToken = bearerToken.replace("Bearer ", "");
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setPlaylistId(playlistId);
        playlistDTO.setPlaylistTitle(playlistTitle);
        playlistDTO.setPlaylistComment(playlistComment);
        playlistDTO.setPlaylistDate(Date.valueOf(playlistDate));
        playlistDTO.setIsPublic(isPublic);
        playlistDTO.setUserId(userId);
        playlistDTO.setTracks(tracks); // DTO에 설정

        // 파일 처리 로직
        if (playlistPhoto != null && !playlistPhoto.isEmpty()) {
            try {
                String fileName = fileStorageService.storeFile(playlistPhoto);
                String filePath = "/images/" + fileName; // 정적 리소스 경로 설정
                playlistDTO.setPlaylistPhoto(filePath); // 파일 경로 DTO에 저장
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            playlistDTO.setPlaylistPhoto(existingPlaylistPhoto); // 기존 이미지 사용
        }

        Playlist updatedPlaylist = playlistService.updatePlaylist(accessToken, playlistDTO);
        return ResponseEntity.ok(updatedPlaylist);
    }



    /**
     * 🗑 플레이리스트 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlaylist(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam String playlistId) {
        String accessToken = bearerToken.replace("Bearer ", "");
        playlistService.deletePlaylist(accessToken, playlistId);
        return ResponseEntity.ok("삭제 완료");
    }
    /**
     * 🎵 플레이리스트의 트랙 ID 목록 조회
     */
    @GetMapping("/detail/{playlistId}")
    public ResponseEntity<Map<String, Object>> getTracksInPlaylist(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String playlistId) {
        String accessToken = bearerToken.replace("Bearer ", "");
        return ResponseEntity.ok(playlistService.getPlaylistDetail(accessToken, playlistId));
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
