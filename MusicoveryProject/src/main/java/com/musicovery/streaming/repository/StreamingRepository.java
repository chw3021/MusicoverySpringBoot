package com.musicovery.streaming.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.musicovery.streaming.entity.Streaming;

public interface StreamingRepository extends JpaRepository<Streaming, Long> {
    List<Streaming> findByIsLiveTrue(); // 현재 라이브 중인 스트리밍 목록 가져오기


    // ✅ user_id로 nickname 조회
    @Query("SELECT u.nickname FROM User u WHERE u.userId = :userId") 
    String findNicknameByUserId(@Param("userId") String userId);
    
    Optional<Streaming> findByPlaylist_PlaylistId(String playlistId);
    
}