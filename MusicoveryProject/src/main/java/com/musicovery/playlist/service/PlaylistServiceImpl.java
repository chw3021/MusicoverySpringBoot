package com.musicovery.playlist.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicovery.playlist.dto.PlaylistDTO;
import com.musicovery.playlist.entity.Playlist;
import com.musicovery.playlist.repository.PlaylistRepository;
import com.musicovery.spotifyapi.service.SpotifyApiPlaylistService;
import com.musicovery.user.entity.User;
import com.musicovery.user.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SpotifyApiPlaylistService spotifyApiPlaylistService;
    private final UserService userService;


    /**
     * 📂 플레이리스트 생성 + Spotify API 동기화
     */
    @Override
    public Playlist createPlaylist(String accessToken, PlaylistDTO playlistDTO) {
        // Spotify API를 호출하여 플레이리스트 생성
        String spotifyPlaylistId = spotifyApiPlaylistService.createPlaylist(accessToken, playlistDTO.getUserId(), playlistDTO.getPlaylistTitle(), playlistDTO.getPlaylistComment(), playlistDTO.getTracks());
        // 현재 시간 생성
        Date currentDate = new Date();

        // 생성된 플레이리스트 정보를 DB에 저장
        Playlist playlist = Playlist.builder()
                .playlistId(spotifyPlaylistId)
                .playlistTitle(playlistDTO.getPlaylistTitle())
                .playlistComment(playlistDTO.getPlaylistComment())
                .playlistPhoto(playlistDTO.getPlaylistPhoto())
                .user(userService.findByUserId(playlistDTO.getUserId()))
                .playlistDate(currentDate)
                .isPublic(playlistDTO.getIsPublic())
                .build();
        return playlistRepository.save(playlist);
    }
    
    @Override
    public Playlist updatePlaylist(String accessToken, PlaylistDTO playlistDTO) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistDTO.getPlaylistId());
        if (optionalPlaylist.isPresent()) {

            Playlist updatePlaylist = optionalPlaylist.get();
            
            updatePlaylist.setPlaylistTitle(playlistDTO.getPlaylistTitle());
            updatePlaylist.setPlaylistComment(playlistDTO.getPlaylistComment());
            updatePlaylist.setUser(userService.findByUserId(playlistDTO.getUserId()));
            updatePlaylist.setPlaylistDate(playlistDTO.getPlaylistDate());
            updatePlaylist.setPlaylistId(playlistDTO.getPlaylistId());
            updatePlaylist.setPlaylistPhoto(playlistDTO.getPlaylistPhoto());
            updatePlaylist.setIsPublic(playlistDTO.getIsPublic());
			spotifyApiPlaylistService.updatePlaylistTracks(accessToken, playlistDTO.getPlaylistId(),
					playlistDTO.getTracks());

            // Spotify API에도 반영
			spotifyApiPlaylistService.updatePlaylist(accessToken, playlistDTO.getPlaylistId(),
					playlistDTO.getPlaylistTitle(), playlistDTO.getPlaylistComment());

            return playlistRepository.save(updatePlaylist);
        } else {
            throw new RuntimeException("플레이리스트가 존재하지 않습니다.");
        }
    }

    /**
     * 🗑 플레이리스트 삭제 + Spotify API 동기화
     */
    @Override
    public void deletePlaylist(String accessToken, String playlistId) {
        playlistRepository.deleteById(playlistId);
        spotifyApiPlaylistService.deletePlaylist(accessToken, playlistId);
    }

    /**
     * 🔍 플레이리스트 조회
     */
    @Override
    public Playlist getPlaylist(String accessToken, String playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("플레이리스트를 찾을 수 없습니다."));

        return playlist;
    }

    /**
     * 🎵 플레이리스트 내 트랙 조회
     */
    @Override
    public String getTracksInPlaylist(String accessToken, String playlistId) {
        return spotifyApiPlaylistService.getTracksInPlaylist(accessToken, playlistId);
    }
    
    @Override
    public Map<String, Object> getPlaylistDetail(String accessToken, String playlistId) {
        Playlist playlist = getPlaylist(accessToken, playlistId);
        String trackDataJson = getTracksInPlaylist(accessToken, playlistId); // JSON 전체 반환

        Map<String, Object> response = new HashMap<>();
        response.put("playlist", playlist);
        response.put("tracks", trackDataJson); // JSON 그대로 반환

        return response;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public List<Playlist> getAllPlaylistsByUser(User user) {
        return playlistRepository.findAllByUser(user);
    }

    @Override
    @Transactional
    public void updatePlaylistPublicStatus(String playlistId, Boolean isPublic) {
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id: " + playlistId));
        playlist.setIsPublic(isPublic);
        playlistRepository.save(playlist);
    }

}
