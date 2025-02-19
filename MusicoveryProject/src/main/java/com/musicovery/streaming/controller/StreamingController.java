package com.musicovery.streaming.controller;

import java.util.List;

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

    private final StreamingService streamingService;

    public StreamingController(StreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @PostMapping("/start")
    public Streaming startStreaming(@RequestBody StreamingDTO streamingDTO) {
        return streamingService.startStreaming(streamingDTO);
    }

    @PostMapping("/stop/{streamingId}")
    public void stopStreaming(@PathVariable Long streamingId) {
        streamingService.stopStreaming(streamingId);
    }

    @GetMapping("/live")
    public List<Streaming> getLiveStreams() {
        List<Streaming> liveStreams = streamingService.getLiveStreams();
        //System.out.println("📡 현재 라이브 스트리밍 데이터: " + liveStreams);
        return liveStreams;
    }
}