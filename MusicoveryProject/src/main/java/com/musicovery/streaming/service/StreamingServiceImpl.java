package com.musicovery.streaming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musicovery.playlist.service.PlaylistService;
import com.musicovery.streaming.dto.StreamingDTO;
import com.musicovery.streaming.entity.Streaming;
import com.musicovery.streaming.repository.StreamingRepository;

@Service
public class StreamingServiceImpl implements StreamingService {

    private final StreamingRepository streamingRepository;
    private final PlaylistService playlistService;

    public StreamingServiceImpl(StreamingRepository streamingRepository, PlaylistService playlistService) {
        this.streamingRepository = streamingRepository;
        this.playlistService = playlistService;
    }
    
    @Override
    public boolean stopStreamingByPlaylist(String playlistId) {
        Optional<Streaming> streaming = streamingRepository.findByPlaylist_PlaylistId(playlistId);
        if (streaming.isPresent()) {
            streamingRepository.delete(streaming.get());
            playlistService.updatePlaylistPublicStatus(playlistId, false); // 비공개로 변경
            return true;
        }
        return false;
    }


    @Override
    public boolean createStreaming(String accessToken, StreamingDTO streamingDTO) {
        try {
            Streaming streaming = new Streaming();
            streaming.setPlaylist(playlistService.getPlaylist(accessToken, streamingDTO.getPlaylistId()));
            streaming.setHostUser(streamingDTO.getHostUser());
            streaming.setIsLive(true);
            streaming.setIsPremiumOnly(false);
            System.out.println("streamingDTO.getIsPublic() "+streamingDTO.getIsPublic());
            streaming.setIsPublic(streamingDTO.getIsPublic());

            streamingRepository.save(streaming);

         // 플레이리스트의 isPublic 상태를 비공식 스트리밍으로 변경할 필요가 있는지 확인
            if (streamingDTO.getIsPublic()) {
                playlistService.updatePlaylistPublicStatus(streaming.getPlaylist().getPlaylistId(), true);
            } else {
                playlistService.updatePlaylistPublicStatus(streaming.getPlaylist().getPlaylistId(), false);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Streaming startStreaming(String accessToken, StreamingDTO streamingDTO) {
        Streaming streaming = Streaming.builder()
            .hostUser(streamingDTO.getHostUser()) // ✅ 문자열을 Long 타입으로 변환
            .playlist(playlistService.getPlaylist(accessToken, streamingDTO.getPlaylistId())) // ✅ 변수명 수정
            .isLive(true)
            .isPremiumOnly(streamingDTO.getIsPremiumOnly())
            .isPublic(streamingDTO.getIsPublic())
            .build();

        return streamingRepository.save(streaming);
    }
    
    @Override
    public void stopStreaming(Long streamingId) {
        Optional<Streaming> streaming = streamingRepository.findById(streamingId);
        streaming.ifPresent(s -> {
            Streaming updatedStreaming = Streaming.builder()
                    .id(s.getId())  // ✅ ID 유지
                    .hostUser(s.getHostUser())
                    .playlist(s.getPlaylist())
                    .isLive(false) // ✅ 스트리밍 종료
                    .isPremiumOnly(s.getIsPremiumOnly())
                    .isPublic(s.getIsPublic())
                    .build();
            
            streamingRepository.save(updatedStreaming);
        });
    }

	/*
	 * @Override public void stopStreaming(Long streamingId) { Optional<Streaming>
	 * streaming = streamingRepository.findById(streamingId); streaming.ifPresent(s
	 * -> { s = Streaming.builder() .id(s.getId()) .hostUserId(s.getHostUserId())
	 * .playlistName(s.getPlaylistName()) .isLive(false) // 스트리밍 종료
	 * .isPremiumOnly(s.isPremiumOnly()) .build(); streamingRepository.save(s); });
	 * }
	 */

    @Override
    public List<Streaming> getLiveStreams() {
        return streamingRepository.findByIsLiveTrue();
    }

}