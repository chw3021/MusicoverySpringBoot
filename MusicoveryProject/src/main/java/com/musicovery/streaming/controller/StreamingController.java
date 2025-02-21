package com.musicovery.streaming.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.streaming.dto.StreamingDTO;
import com.musicovery.streaming.entity.Streaming;
import com.musicovery.streaming.service.StreamingService;

@RestController
@RequestMapping("/api/streaming")
public class StreamingController {
    
	@Autowired
    private final StreamingService streamingService;
   
	
	@GetMapping("/{streamId}")
	public ResponseEntity<?> getStreamingById(@PathVariable Long streamId) {
	    Streaming streaming = streamingService.getStreamingById(streamId);
	    if (streaming != null) {
	        return ResponseEntity.ok(streaming);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("스트리밍 정보를 찾을 수 없습니다.");
	    }
	}
	
	
	
	
	
    // 스트리밍 데이터 저장 API
	@PostMapping("/create")
    public ResponseEntity<?> createStreaming(@RequestBody StreamingDTO streamingDTO) {
        boolean success = streamingService.createStreaming(streamingDTO);
        if (success) {
            return ResponseEntity.ok("스트리밍 생성 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스트리밍 생성 실패");
        }
    }

    public StreamingController(StreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @PostMapping("/start")
    public Streaming startStreaming(@RequestBody StreamingDTO streamingDTO) {
        return streamingService.startStreaming(streamingDTO);
    }

    @PostMapping("/stop/{playlistId}")
    public ResponseEntity<?> stopStreaming(@PathVariable String playlistId) {
        boolean deleted = streamingService.stopStreamingByPlaylist(playlistId);
        if (deleted) {
            return ResponseEntity.ok("스트리밍 삭제 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스트리밍 삭제 실패");
        }
    }

    @GetMapping("/live")
    public List<Streaming> getLiveStreams() {
        List<Streaming> liveStreams = streamingService.getLiveStreams();
        //System.out.println("📡 현재 라이브 스트리밍 데이터: " + liveStreams);
        return liveStreams;
    }
    
}