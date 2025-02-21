package com.musicovery.streaming.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.streaming.entity.Streaming;
import com.musicovery.user.entity.User;

public interface StreamingRepository extends JpaRepository<Streaming, Long> {
    List<Streaming> findByIsLiveTrue(); // 현재 라이브 중인 스트리밍 목록 가져오기


	/*
	 * // ✅ user_id로 nickname 조회
	 * 
	 * @Query("SELECT u.nickname FROM User u WHERE u.userId = :userId") String
	 * findNicknameByUserId(@Param("userId") Long streamId);
	 */  
    Optional<Streaming> findByPlaylist_PlaylistId(String playlistId);

    Optional<Streaming> findByHostUser(User user); // User 객체를 인자로 받는 메소드로 수정
    
    
    
    
    default Streaming getById(Long id) {
        return findById(id).orElse(null);
    }
    
    
}