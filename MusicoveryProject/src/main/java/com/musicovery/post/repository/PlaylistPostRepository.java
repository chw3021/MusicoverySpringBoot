package com.musicovery.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.musicovery.post.dto.PlaylistPostDTO;
import com.musicovery.post.entity.PlaylistPost;


public interface PlaylistPostRepository extends JpaRepository<PlaylistPost, Long> {
    List<PlaylistPost> findAllByOrderByLikeCountDesc();
    Page<PlaylistPost> findAll(Pageable pageable);

    Page<PlaylistPost> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<PlaylistPost> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<PlaylistPost> findByUser_NicknameContainingIgnoreCase(String nickname, Pageable pageable);

    @Query(value = """
        SELECT new com.musicovery.post.dto.PlaylistPostDTO(
            p.id, p.title, p.description, p.user, 
            p.createdDate, p.likeCount, p.replyCount, p.viewCount, p.isNotice, p.playlist
        ) 
        FROM PlaylistPost p
        WHERE p.isNotice = false
        ORDER BY p.createdDate DESC
        """)
    Page<PlaylistPostDTO> findAllWithProjection(Pageable pageable);

    @Query(value = """
        SELECT new com.musicovery.post.dto.PlaylistPostDTO(
            p.id, p.title, p.description, p.user, 
            p.createdDate, p.likeCount, p.replyCount, p.viewCount, p.isNotice, p.playlist
        ) 
        FROM PlaylistPost p
        WHERE p.isNotice = false
        ORDER BY p.viewCount DESC, p.createdDate DESC
        """)
    Page<PlaylistPostDTO> findAllWithProjectionByViewCount(Pageable pageable);

    // 네이티브 쿼리 버전 추가
    @Query(value = """
        SELECT id, title, description, user_id, 
               created_date as createdDate, 
               like_count as likeCount, 
               reply_count as replyCount, 
               view_count as viewCount , 
               is_notice as isNotice ,
               playlist_id as playlistId
        FROM post 
        WHERE is_notice = false 
        ORDER BY created_date DESC
        """,
        nativeQuery = true)
    Page<PlaylistPost> findAllWithNativeQuery(Pageable pageable);
    
	Page<PlaylistPost> findByTitleContainingIgnoreCaseAndIsNoticeFalse(String keyword, Pageable pageable);
	Page<PlaylistPost> findByUser_NicknameContainingIgnoreCaseAndIsNoticeFalse(String keyword, Pageable pageable);
	Page<PlaylistPost> findByDescriptionContainingIgnoreCaseAndIsNoticeFalse(String keyword, Pageable pageable);
	Page<PlaylistPost> findByIsNoticeFalse(Pageable pageable);
	Page<PlaylistPost> findByIsNoticeTrue(Pageable pageable);
}