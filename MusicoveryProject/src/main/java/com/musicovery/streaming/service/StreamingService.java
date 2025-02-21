package com.musicovery.streaming.service;

import java.util.List;

import com.musicovery.streaming.dto.StreamingDTO;
import com.musicovery.streaming.entity.Streaming;

public interface StreamingService {
    void stopStreaming(Long streamingId);
    List<Streaming> getLiveStreams();
	boolean createStreaming(String accessToken, StreamingDTO streamingDTO);
	Streaming startStreaming(String accessToken, StreamingDTO streamingDTO);
    
	boolean stopStreamingByPlaylist(String playlistId);
	
	
	
	Streaming getStreamingById(Long streamId);
	
    
}