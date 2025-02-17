package com.musicovery.playlist.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musicovery.playlist.entity.Playlist;
import com.musicovery.playlist.repository.PlaylistRepository;
import com.musicovery.spotifyapi.service.SpotifyApiPlaylistService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SpotifyApiPlaylistService spotifyApiPlaylistService;

    /**
     * 📂 플레이리스트 생성 + Spotify API 동기화
     */
    @Override
    public Playlist createPlaylist(String sessionId, String name, String description) {
        // Spotify API를 호출하여 플레이리스트 생성
        String spotifyPlaylistId = spotifyApiPlaylistService.createPlaylist(sessionId, name, description);

        // 생성된 플레이리스트 정보를 DB에 저장
        Playlist playlist = new Playlist(spotifyPlaylistId, name, description, null, sessionId);
        return playlistRepository.save(playlist);
    }

    /**
     * 📝 플레이리스트 수정 + Spotify API 동기화
     */
    @Override
    public Playlist updatePlaylist(String sessionId, String playlistId, String name, String description) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            Playlist playlist = optionalPlaylist.get();
            playlist.setPlaylistTitle(name);
            playlist.setPlaylistComment(description);

            // Spotify API에도 반영
            spotifyApiPlaylistService.updatePlaylist(sessionId, playlistId, name, description);

            return playlistRepository.save(playlist);
        } else {
            throw new RuntimeException("플레이리스트가 존재하지 않습니다.");
        }
    }

    /**
     * 🗑 플레이리스트 삭제 + Spotify API 동기화
     */
    @Override
    public void deletePlaylist(String sessionId, String playlistId) {
        playlistRepository.deleteById(playlistId);
        spotifyApiPlaylistService.deletePlaylist(sessionId, playlistId);
    }

    /**
     * 🔍 플레이리스트 조회
     */
    @Override
    public Playlist getPlaylist(String playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("플레이리스트를 찾을 수 없습니다."));
    }

    /**
     * 🎵 플레이리스트 내 트랙 조회
     */
    @Override
    public List<String> getTracksInPlaylist(String playlistId) {
        return spotifyApiPlaylistService.getTracksInPlaylist(playlistId);
    }
    

    @Override
    public Map<String, Object> getPlaylistDetail(String sessionId, String playlistId) {
        Playlist playlist = getPlaylist(playlistId);
        List<String> trackIds = getTracksInPlaylist(playlistId);

        Map<String, Object> response = new HashMap<>();
        response.put("playlist", playlist);
        response.put("tracks", trackIds);

        return response;
    }
}
