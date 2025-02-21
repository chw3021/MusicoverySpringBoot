package com.musicovery.streaming.service;

import java.util.List;

import com.musicovery.streaming.dto.StreamingDTO;
import com.musicovery.streaming.entity.Streaming;

public interface StreamingService {
    Streaming startStreaming(StreamingDTO streamingDTO);
    void stopStreaming(Long streamingId);
    List<Streaming> getLiveStreams();
    boolean createStreaming(StreamingDTO streamingDTO);
    
	boolean stopStreamingByPlaylist(String playlistId);
	
	
	
	Streaming getStreamingById(Long streamId);
	
    
}