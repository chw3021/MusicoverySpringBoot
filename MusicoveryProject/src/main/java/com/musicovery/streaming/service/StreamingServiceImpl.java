package com.musicovery.streaming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicovery.streaming.dto.StreamingDTO;
import com.musicovery.streaming.entity.Streaming;
import com.musicovery.streaming.repository.StreamingRepository;

@Service
public class StreamingServiceImpl implements StreamingService {
    
    @Autowired
    private StreamingRepository streamingRepository;

    public boolean createStreaming(StreamingDTO streamingDTO) {
        try {
            Streaming streaming = new Streaming();
            streaming.setId(streamingDTO.getId());  
            streaming.setPlaylistName(streamingDTO.getPlaylistName());
            streaming.setHostUserId(Long.parseLong(streamingDTO.getHostUserId())); 
            streaming.setLive(true);  
            streaming.setPremiumOnly(false);  
            streaming.setPublic(streamingDTO.isPublic());

            streamingRepository.save(streaming);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public StreamingServiceImpl(StreamingRepository streamingRepository) {
        this.streamingRepository = streamingRepository;
    }

    public Streaming startStreaming(StreamingDTO streamingDTO) {
        Streaming streaming = Streaming.builder()
            .hostUserId(Long.parseLong(streamingDTO.getHostUserId())) // ✅ 문자열을 Long 타입으로 변환
            .playlistName(streamingDTO.getPlaylistName()) // ✅ 변수명 수정
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
            Streaming updatedStreaming = Streaming.builder()
                    .id(s.getId())  // ✅ ID 유지
                    .hostUserId(s.getHostUserId())
                    .playlistName(s.getPlaylistName())
                    .isLive(false) // ✅ 스트리밍 종료
                    .isPremiumOnly(s.isPremiumOnly())
                    .isPublic(s.isPublic())
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