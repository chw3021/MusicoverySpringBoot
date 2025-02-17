package com.musicovery.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.post.entity.Like;
import com.musicovery.post.entity.PlaylistPost;
import com.musicovery.post.entity.Reply;
import com.musicovery.post.repository.LikeRepository;
import com.musicovery.post.repository.PlaylistPostRepository;
import com.musicovery.post.repository.ReplyRepository;
import com.musicovery.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistPostService {

    private final PlaylistPostRepository playlistPostRepository;
    private final LikeRepository likeRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public PlaylistPost createPost(User user, String title, String description, String playlistId) {
        PlaylistPost post = new PlaylistPost();
        post.setTitle(title);
        post.setDescription(description);
        post.setPlaylistId(playlistId);
        post.setUser(user);
        post.setLikeCount(0);
        post.setReplyCount(0);
        playlistPostRepository.save(post);

        return post;
    }

    @Transactional
    public void likePost(User user, Long postId) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow();
        if (likeRepository.existsByUserAndPlaylistPost(user, post)) {
            likeRepository.deleteByUserAndPlaylistPost(user, post);
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setPlaylistPost(post);
            likeRepository.save(like);
            post.setLikeCount(post.getLikeCount() + 1);
        }
        playlistPostRepository.save(post);
    }

    @Transactional
    public void addReply(User user, Long postId, String content) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow();
        Reply reply = new Reply();
        reply.setUser(user);
        reply.setPlaylistPost(post);
        reply.setContent(content);
        replyRepository.save(reply);

        post.setReplyCount(post.getReplyCount() + 1);
        playlistPostRepository.save(post);
    }
    

    public List<PlaylistPost> getRanking() {
        return playlistPostRepository.findAllByOrderByLikeCountDesc();
    }

    public List<PlaylistPost> getPlaylistPosts() {
        return playlistPostRepository.findAll(); // 모든 게시글을 가져오는 메서드
    }
}
