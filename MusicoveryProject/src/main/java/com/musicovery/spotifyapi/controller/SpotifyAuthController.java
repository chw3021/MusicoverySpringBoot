package com.musicovery.spotifyapi.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.musicovery.spotifyapi.service.SpotifyAuthService;

import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyAuthController {

    private final SpotifyAuthService spotifyAuthService;

    public SpotifyAuthController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }


    @GetMapping("/getUserAccessToken")
    public ResponseEntity<String> login() {
        String authUrl = spotifyAuthService.getSpotifyAuthUrl();
        return ResponseEntity.ok(authUrl); // Spotify 인증 페이지 URL 반환
    }
    
    @Value("${react.base_url}")
    private String reactUrl;

    @GetMapping("/callback")
    public ResponseEntity<String> handleSpotifyCallback(@RequestParam("code") String code) {
        try {
            AuthorizationCodeRequest authRequest = spotifyAuthService.getSpotifyApi().authorizationCode(code).build();
            AuthorizationCodeCredentials credentials = authRequest.execute();

            String accessToken = credentials.getAccessToken();
            String refreshToken = credentials.getRefreshToken();

            return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(String.format("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <script>
                            window.onload = function() {
                                try {
                                    // 토큰을 메시지로 전달
                                    window.opener.postMessage({
                                        type: 'auth_complete',
                                        accessToken: '%s',
                                        refreshToken: '%s'
                                    }, '%s');
                                    
                                    // 창 닫기
                                    setTimeout(function() {
                                        window.close();
                                    }, 1000);
                                } catch(e) {
                                    console.error('오류 발생:', e);
                                    alert('인증 처리 중 오류가 발생했습니다.');
                                }
                            };
                        </script>
                    </head>
                    <body>
                        <h3>인증이 완료되었습니다. 잠시 후 창이 닫힙니다.</h3>
                    </body>
                    </html>
                    """, accessToken, refreshToken, reactUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("인증 실패: " + e.getMessage());
        }
    }
    
//    @GetMapping("/callback")
//    public ResponseEntity<Map<String, String>> handleSpotifyCallback(@RequestParam("code") String code) {
//        if (code == null || code.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "인증 코드가 없습니다."));
//        }
//
//        try {
//            // Spotify 인증 요청 및 토큰 획득
//            AuthorizationCodeRequest authRequest = spotifyAuthService.getSpotifyApi().authorizationCode(code).build();
//            AuthorizationCodeCredentials credentials = authRequest.execute();
//
//            String accessToken = credentials.getAccessToken();
//            String refreshToken = credentials.getRefreshToken();
//
//            // 클라이언트에 JSON 형태로 응답
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Spotify 로그인 성공!");
//            response.put("accessToken", accessToken);
//            response.put("refreshToken", refreshToken);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Spotify 인증 실패: " + e.getMessage()));
//        }
//    }





    
    @PostMapping("/refresh-token")  // GET에서 POST로 변경
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            String newAccessToken = spotifyAuthService.refreshUserAccessToken(refreshToken);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);

            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getResponseBodyAsString());
            }
            throw e;
        } catch (Exception e) {
            e.printStackTrace();  // 디버깅을 위한 로그 추가
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("토큰 갱신 실패: " + e.getMessage());
        }
    }
}
