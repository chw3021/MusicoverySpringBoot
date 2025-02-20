package com.musicovery.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.musicovery.musicrecommendation.service.WeightService;
import com.musicovery.post.dto.PlaylistPostDTO;
import com.musicovery.post.entity.Like;
import com.musicovery.post.entity.PlaylistPost;
import com.musicovery.post.entity.Reply;
import com.musicovery.post.repository.LikeRepository;
import com.musicovery.post.repository.PlaylistPostRepository;
import com.musicovery.post.repository.ReplyRepository;
import com.musicovery.spotifyapi.service.SpotifyApiPlaylistService;
import com.musicovery.user.entity.User;

import jakarta.transaction.Transactional;

@Service
public class PlaylistPostService {

    private final WeightService weightService;
    private final PlaylistPostRepository playlistPostRepository;
    private final SpotifyApiPlaylistService spotifyApiPlaylistService;
    private final LikeRepository likeRepository;
    private final ReplyRepository replyRepository;


    public PlaylistPostService(WeightService weightService, PlaylistPostRepository playlistPostRepository, 
                               SpotifyApiPlaylistService spotifyApiPlaylistService, LikeRepository likeRepository,
                               ReplyRepository replyRepository) {
        this.weightService = weightService;
        this.playlistPostRepository = playlistPostRepository;
        this.spotifyApiPlaylistService = spotifyApiPlaylistService;
		this.likeRepository = likeRepository;
		this.replyRepository = replyRepository;
    }
    
    @Transactional
    public PlaylistPost createPost(User user, String title, String description, String playlistId) {
        PlaylistPost post = new PlaylistPost();
        post.setTitle(title);
        post.setDescription(description);
        post.setPlaylistId(playlistId);
        post.setUser(user);
        //post.setUser(user.getUserId());
        post.setLikeCount(0);
        post.setReplyCount(0);
        playlistPostRepository.save(post);

        // Spotify API에서 플레이리스트 ID로 음악 리스트 불러오기
        List<String> trackIds = spotifyApiPlaylistService.getTracksInPlaylist(playlistId);

        // 음악들에 대해 가중치 증가
        for (String trackId : trackIds) {
            weightService.increaseWeightForLikedPlaylist(user.getUserId(), trackId);
        }
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
        

        String playlistId = post.getPlaylistId();
        
        // Spotify API에서 플레이리스트 ID로 음악 리스트 불러오기
        List<String> trackIds = spotifyApiPlaylistService.getTracksInPlaylist(playlistId);

        // 좋아요 처리 후 음악들에 대한 가중치 증가
        for (String trackId : trackIds) {
            weightService.increaseWeightForLikedPlaylist(user.getUserId(), trackId);
        }
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
        
        String playlistId = post.getPlaylistId();

        // 댓글 처리 후 음악들에 대한 가중치 증가
        List<String> trackIds = spotifyApiPlaylistService.getTracksInPlaylist(playlistId);
        for (String trackId : trackIds) {
            weightService.increaseWeightForCommentedPlaylist(user.getUserId(), trackId);
        }
    }
    

    public Page<PlaylistPostDTO> getPlaylistPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        
        return playlistPostRepository.findAllWithProjection(pageable)
            .map(post -> PlaylistPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .user(post.getUser())
                .createdDate(post.getCreatedDate())
                .likeCount(post.getLikeCount())
                .replyCount(post.getReplyCount())
                .viewCount(post.getViewCount())
                .build());
    }
    
    public List<PlaylistPost> getRanking() {
        return playlistPostRepository.findAllByOrderByLikeCountDesc();
    }

    public List<PlaylistPost> getPlaylistPosts() {
        return playlistPostRepository.findAll(); // 모든 게시글을 가져오는 메서드
    }
}
