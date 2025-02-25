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
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-pro-exp-02-05:generateContent";
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
    
    
    
    
    
    
    
    
    
    public static String filterLyrics(String lyrics) {
        // 1. 줄바꿈 제거
        String noNewlines = lyrics.replaceAll("\\n", "");

        // 2. 괄호와 괄호 안의 내용 제거
        String noParentheses = noNewlines.replaceAll("\\(.*?\\)", "");

        return noParentheses;
    }
    public String getLyrics(String artist, String title) {
        // 가사를 요청하기 위한 프롬프트 생성
        String prompt = String.format("Instruction: The AI provides the lyrics to the following song as pure text, without parentheses, chorus marks, or line breaks.\\n- Simply prohibit the AI from formatting the lyrics with italics, bold, parentheses, etc.\\n- Provide the original EN/KO text without translation. If the original lyrics are a mix of languages, do not translate them, but provide the original mixed lyrics.: \"%s\"의 \"%s\"", artist, title);
        log.info("가사 요청 프롬프트: {}", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GEMINI_API_URL)
                .queryParam("key", apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Arrays.asList(Map.of("parts", Arrays.asList(Map.of("text", prompt)))));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            // 응답 구조 확인 (디버깅용)
            log.info("Gemini API 응답: {}", root.toString());

            // candidates 배열이 비어 있는지 확인
            JsonNode candidatesNode = root.path("candidates");
            if (!candidatesNode.isArray() || candidatesNode.isEmpty()) {
                log.warn("candidates 노드가 비어 있음.");
                return "가사를 찾을 수 없습니다.";
            }

            // content -> parts -> text 구조 확인
            JsonNode contentNode = candidatesNode.get(0).path("content").path("parts");
            if (!contentNode.isArray() || contentNode.isEmpty()) {
                log.warn("content.parts 노드가 비어 있음.");
                return "가사를 찾을 수 없습니다.";
            }

            String lyrics = contentNode.get(0).path("text").asText("가사를 찾을 수 없습니다.");
            log.info("가사 응답 본문: {}", filterLyrics(lyrics));

            return filterLyrics(lyrics);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 에러", e);
            return "가사를 가져오는 중 오류가 발생했습니다.";
        }
    }
    
    
    
    public String getSomeTitle(String title) {
        // 제목 변형 요청을 위한 프롬프트 생성
        String prompt = String.format("Organize alternate titles for songs like an artist's 'Title'. Generate the original title and present all possible variations.: \"%s\"", title);
        log.info("제목 요청 프롬프트: {}", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GEMINI_API_URL)
                .queryParam("key", apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Arrays.asList(Map.of("parts", Arrays.asList(Map.of("text", prompt)))));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());

            // 응답 구조 확인 (디버깅용)
            log.info("Gemini API 응답: {}", root.toString());

            // candidates 배열이 비어 있는지 확인
            JsonNode candidatesNode = root.path("candidates");
            if (!candidatesNode.isArray() || candidatesNode.isEmpty()) {
                log.warn("candidates 노드가 비어 있음.");
                return "제목을 찾을 수 없습니다.";
            }

            // content -> parts -> text 구조 확인
            JsonNode contentNode = candidatesNode.get(0).path("content").path("parts");
            if (!contentNode.isArray() || contentNode.isEmpty()) {
                log.warn("content.parts 노드가 비어 있음.");
                return "제목을 찾을 수 없습니다.";
            }

            String alternativeTitles = contentNode.get(0).path("text").asText("제목을 찾을 수 없습니다.");
            log.info("제목 응답 본문: {}", alternativeTitles);

            return alternativeTitles;
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 에러", e);
            return "제목을 가져오는 중 오류가 발생했습니다.";
        }
    }
    
    
    
}