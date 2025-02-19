package com.musicovery.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.musicovery.post.dto.PlaylistPostDTO;
import com.musicovery.post.entity.PlaylistPost;

@Repository
public interface PlaylistPostRepository extends JpaRepository<PlaylistPost, Long> {
    List<PlaylistPost> findAllByOrderByLikeCountDesc();
    Page<PlaylistPost> findAll(Pageable pageable);

    
    @Query(value = """
        SELECT new com.musicovery.post.dto.PlaylistPostDTO(
            p.id, p.title, p.description, p.user, 
            p.createdDate, p.likeCount, p.replyCount, p.viewCount
        ) 
        FROM PlaylistPost p
        ORDER BY p.createdDate DESC
        """)
    Page<PlaylistPostDTO> findAllWithProjection(Pageable pageable);

    // 네이티브 쿼리 버전 추가
    @Query(value = """
        SELECT id, title, description, user, 
               created_date as createdDate, 
               like_count as likeCount, 
               reply_count as replyCount, 
               view_count as viewCount 
        FROM post 
        ORDER BY created_date DESC
        """, 
        nativeQuery = true)
    Page<PlaylistPost> findAllWithNativeQuery(Pageable pageable);
}