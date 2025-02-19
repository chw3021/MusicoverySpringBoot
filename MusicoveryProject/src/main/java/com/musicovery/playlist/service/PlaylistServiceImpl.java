package com.musicovery.playlist.service;

import java.util.Date;
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
    public Playlist createPlaylist(String accessToken, String name, String description) {
        // Spotify API를 호출하여 플레이리스트 생성
        String spotifyPlaylistId = spotifyApiPlaylistService.createPlaylist(accessToken, name, description);

        // 현재 시간 생성
        Date currentDate = new Date();

        // 생성된 플레이리스트 정보를 DB에 저장
        Playlist playlist = new Playlist(spotifyPlaylistId, name, description, null, accessToken, currentDate);
        return playlistRepository.save(playlist);
    }

    /**
     * 📝 플레이리스트 수정 + Spotify API 동기화
     */
    @Override
    public Playlist updatePlaylist(String accessToken, Playlist playlist, String name, String description) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlist.getPlaylistId());
        if (optionalPlaylist.isPresent()) {

    		Playlist updatePlaylist = optionalPlaylist.get();
    		
    		updatePlaylist.setPlaylistTitle(playlist.getPlaylistTitle());
    		updatePlaylist.setPlaylistComment(playlist.getPlaylistComment());
    		updatePlaylist.setUserId(playlist.getUserId());
    		updatePlaylist.setPlaylistDate(playlist.getPlaylistDate());
    		updatePlaylist.setPlaylistId(playlist.getPlaylistId());
    		updatePlaylist.setPlaylistPhoto(playlist.getPlaylistPhoto());

            // Spotify API에도 반영
            spotifyApiPlaylistService.updatePlaylist(accessToken, playlist.getPlaylistId(), name, description);


            return playlistRepository.save(playlist);
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
    public Map<String, Object> getPlaylistDetail(String accessToken, String playlistId) {
        Playlist playlist = getPlaylist(playlistId);
        List<String> trackIds = getTracksInPlaylist(playlistId);

        Map<String, Object> response = new HashMap<>();
        response.put("playlist", playlist);
        response.put("tracks", trackIds);

        return response;
    }



    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public List<Playlist> getAllPlaylistsByUserId(String userId) {
        return playlistRepository.findAllByUserId(userId);
    }
}
