package com.musicovery.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicovery.post.entity.PlaylistPost;

@Repository
public interface PlaylistPostRepository extends JpaRepository<PlaylistPost, Long> {
    List<PlaylistPost> findAllByOrderByLikeCountDesc();
}