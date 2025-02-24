package com.musicovery.gemini.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicovery.gemini.dto.SongRecommendation;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class GeminiService {
    private final String apiKey;
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
    private final RestTemplate restTemplate;

    public GeminiService(String apiKey) {
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
    }

    public List<SongRecommendation> getRecommendations(String genres, String mood, Integer bpm) {
        String prompt = String.format(
            "장르: %s, 분위기: %s, BPM: %d인 노래 20곡을 추천해주세요. " +
            "JSON 형식으로 응답해주세요: [{\"title\": \"노래제목\", \"artist\": \"가수이름\"}]", 
            genres, mood, bpm
        );
        log.info(prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GEMINI_API_URL)
            .queryParam("key", apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Arrays.asList(Map.of("parts", Arrays.asList(Map.of("text", prompt)))));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), request, String.class);

        // 응답 본문을 로그에 출력하여 확인
        log.info("Gemini API 응답 본문: {}", response.getBody());

        // JSON 파싱 및 변환
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            String content = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            // JSON 문자열에서 불필요한 부분 제거
            content = content.replace("```json", "").replace("```", "").trim();

            return mapper.readValue(content, new TypeReference<List<SongRecommendation>>() {});
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 에러", e);
            throw new RuntimeException("추천 목록 생성 실패", e);
        }
    }
    
    
    
    
    
    
    public String getLyrics(String artist, String title) {
        // 가사를 요청하기 위한 프롬프트 생성
        String prompt = String.format("다음 노래의 가사만 제공해주세요: \"%s\"의 \"%s\"", artist, title);
        log.info("가사 요청 프롬프트: {}", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GEMINI_API_URL)
                .queryParam("key", apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Arrays.asList(Map.of("parts", Arrays.asList(Map.of("text", prompt)))));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), request, String.class);

        // 응답 본문을 로그에 출력하여 확인
        log.info("Gemini API 응답 본문: {}", response.getBody());

        // JSON 파싱 및 가사 추출
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            String content = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            // 가사만 추출
            return content;
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 에러", e);
            throw new RuntimeException("가사 추출 실패", e);
        }
    }
    
    
    
    
}