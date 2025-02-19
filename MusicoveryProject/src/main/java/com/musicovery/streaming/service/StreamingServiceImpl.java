package com.musicovery.streaming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musicovery.streaming.dto.StreamingDTO;
import com.musicovery.streaming.entity.Streaming;
import com.musicovery.streaming.repository.StreamingRepository;

@Service
public class StreamingServiceImpl implements StreamingService {

    private final StreamingRepository streamingRepository;

    public StreamingServiceImpl(StreamingRepository streamingRepository) {
        this.streamingRepository = streamingRepository;
    }

    public Streaming startStreaming(StreamingDTO streamingDTO) {
        Streaming streaming = Streaming.builder()
            .hostUserId(streamingDTO.getHostUserId())
            .playlistName(streamingDTO.getPlaylistName())
            .isLive(true)
            .isPremiumOnly(streamingDTO.isPremiumOnly())
            .isPublic(streamingDTO.isPublic())
            .build();
        
        return streamingRepository.save(streaming);
    }

    @Override
    public void stopStreaming(Long streamingId) {
        Optional<Streaming> streaming = streamingRepository.findById(streamingId);
        streaming.ifPresent(s -> {
            s = Streaming.builder()
                    .id(s.getId())
                    .hostUserId(s.getHostUserId())
                    .playlistName(s.getPlaylistName())
                    .isLive(false) // 스트리밍 종료
                    .isPremiumOnly(s.isPremiumOnly())
                    .build();
            streamingRepository.save(s);
        });
    }

    @Override
    public List<Streaming> getLiveStreams() {
        return streamingRepository.findByIsLiveTrue();
    }
}