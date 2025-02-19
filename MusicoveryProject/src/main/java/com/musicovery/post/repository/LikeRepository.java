package com.musicovery.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicovery.post.entity.Like;
import com.musicovery.post.entity.PlaylistPost;
import com.musicovery.user.entity.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPlaylistPost(User user, PlaylistPost playlistPost);
    void deleteByUserAndPlaylistPost(User user, PlaylistPost playlistPost);
}