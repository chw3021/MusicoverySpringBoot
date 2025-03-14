package com.musicovery.post.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.musicovery.musicrecommendation.service.WeightService;
import com.musicovery.playlist.entity.Playlist;
import com.musicovery.playlist.service.PlaylistService;
import com.musicovery.post.dto.PlaylistPostDTO;
import com.musicovery.post.dto.ReplyDTO;
import com.musicovery.post.entity.Like;
import com.musicovery.post.entity.PlaylistPost;
import com.musicovery.post.entity.Reply;
import com.musicovery.post.repository.LikeRepository;
import com.musicovery.post.repository.PlaylistPostRepository;
import com.musicovery.post.repository.ReplyRepository;
import com.musicovery.spotifyapi.service.SpotifyApiPlaylistService;
import com.musicovery.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PlaylistPostService {

    private final WeightService weightService;
    private final PlaylistPostRepository playlistPostRepository;
    private final SpotifyApiPlaylistService spotifyApiPlaylistService;
    private final LikeRepository likeRepository;
    private final ReplyRepository replyRepository;
    private final PlaylistService playlistService;

    public PlaylistPost createPost(String accessToken, User user, String title, 
    		String description, String playlistId) {
        PlaylistPost post = new PlaylistPost();
        post.setTitle(title);
        post.setDescription(description);
        post.setUser(user);
        post.setPlaylist(playlistService.getPlaylist(accessToken, playlistId));
        post.setLikeCount(0);
        post.setCreatedDate(new Date());
        post.setReplyCount(0);
        post.setIsNotice(false);
        playlistPostRepository.save(post);
        return post;
    }

	public PlaylistPost createNotice(String accessToken, User user, String title, String description) {
        PlaylistPost post = new PlaylistPost();
        post.setTitle(title);
        post.setDescription(description);
        post.setUser(user);
        post.setLikeCount(0);
        post.setCreatedDate(new Date());
        post.setReplyCount(0);
        post.setIsNotice(true);
        playlistPostRepository.save(post);
        return post;
	}
    public String getTracksInPlaylist(String accessToken, String playlistId) {
        return spotifyApiPlaylistService.getTracksInPlaylist(accessToken, playlistId);
    }
    @Transactional
    public Map<String, Object> getPostPlaylist(String accessToken, Long postId) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Playlist playlist = post.getPlaylist();
        String trackDataJson = getTracksInPlaylist(accessToken, playlist.getPlaylistId()); // JSON 전체 반환

        Map<String, Object> response = new HashMap<>();
        response.put("playlist", playlist);
        response.put("tracks", trackDataJson); // JSON 그대로 반환
        
        return response;
    }

    public PlaylistPost updatePost(String accessToken, Long postId, String title, String description) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setTitle(title);
        post.setDescription(description);
        playlistPostRepository.save(post);
        return post;
    }
    

    public PlaylistPost increaseViewCount(String accessToken, Long postId) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setViewCount(post.getViewCount()+1);
        playlistPostRepository.save(post);
        return post;
    }
    

    public PlaylistPost deletePost(String accessToken, Long postId) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        playlistPostRepository.delete(post);
        
        return post;
    }
    
    @Transactional
    public void likePost(String accessToken, User user, Long postId) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow();
        if (likeRepository.existsByUserAndPlaylistPost(user, post)) {
            likeRepository.deleteByUserAndPlaylistPost(user, post);
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setPlaylistPost(post);
            

            if(like.getIsFirst()==null || !like.getIsFirst()) {
                like.setIsFirst(true);
                // Spotify API에서 플레이리스트 ID로 음악 리스트 불러오기
                List<String> trackIds = spotifyApiPlaylistService.getTracksIdInPlaylist(accessToken, post.getPlaylist().getPlaylistId());

                // 좋아요 처리 후 음악들에 대한 가중치 증가
                for (String trackId : trackIds) {
                    weightService.increaseWeightForLikedPlaylist(user.getUserId(), trackId);
                }
            }
            
            
            likeRepository.save(like);
            post.setLikeCount(post.getLikeCount() + 1);
        }
        playlistPostRepository.save(post);
    }

    public int getLikeCount(Long postId) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow();
        return post.getLikeCount();
    }
    
    
    @Transactional
    public void addReply(String accessToken, User user, Long postId, String content) {
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow();
        Reply reply = new Reply();
        reply.setUser(user);
        reply.setPlaylistPost(post);
        reply.setContent(content);
        reply.setCreatedDate(LocalDateTime.now());
        replyRepository.save(reply);

        post.setReplyCount(post.getReplyCount() + 1);
        playlistPostRepository.save(post);

        // 댓글 처리 후 음악들에 대한 가중치 증가
        List<String> trackIds = spotifyApiPlaylistService.getTracksIdInPlaylist(accessToken, post.getPlaylist().getPlaylistId());
        for (String trackId : trackIds) {
            weightService.increaseWeightForCommentedPlaylist(user.getUserId(), trackId);
        }
    }
    

    @Transactional
    public List<ReplyDTO> getRepliesByPostId(Long postId) {
        List<Reply> replies = replyRepository.findByPlaylistPostId(postId);
        return replies.stream()
                .map(reply -> ReplyDTO.builder()
                        .id(reply.getId())
                        .content(reply.getContent())
                        .user(reply.getUser())
                        .playlistPostId(reply.getPlaylistPost().getId())
                        .createdDate(reply.getCreatedDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public Reply deleteReply(String accessToken, Long postId, Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new EntityNotFoundException("Reply not found"));

        replyRepository.delete(reply);
        PlaylistPost post = playlistPostRepository.findById(postId).orElseThrow();
        post.setReplyCount(post.getReplyCount() - 1);
        playlistPostRepository.save(post);
        
        return reply;
    }
    
    public Page<PlaylistPostDTO> getPlaylistPosts(int page, int size, String sort, String searchType, String keyword) {
        Sort.Direction direction = Sort.Direction.DESC;
        String sortField = "createdDate";

        if (sort != null) {
            if (sort.equals("oldest")) {
                direction = Sort.Direction.ASC;
            } else if (sort.equals("popular")) {
                sortField = "viewCount";
            }
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<PlaylistPostDTO> posts;
        if (keyword != null && !keyword.isEmpty()) {
            // 검색 조건이 있을 때는 기존 방식 사용
            Page<PlaylistPost> postEntities;
            if (searchType.equals("title")) {
                postEntities = playlistPostRepository.findByTitleContainingIgnoreCaseAndIsNoticeFalse(keyword, pageable);
            } else if (searchType.equals("description")) {
                postEntities = playlistPostRepository.findByDescriptionContainingIgnoreCaseAndIsNoticeFalse(keyword, pageable);
            } else if (searchType.equals("author")) {
                postEntities = playlistPostRepository.findByUser_NicknameContainingIgnoreCaseAndIsNoticeFalse(keyword, pageable);
            } else {
                postEntities = playlistPostRepository.findByIsNoticeFalse(pageable);
            }
            posts = postEntities.map(post -> PlaylistPostDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .description(post.getDescription())
                    .user(post.getUser())
                    .createdDate(post.getCreatedDate())
                    .likeCount(post.getLikeCount())
                    .replyCount(post.getReplyCount())
                    .viewCount(post.getViewCount())
                    .isNotice(post.getIsNotice())
                    .build());
        } else {
            // 검색 조건이 없을 때는 프로젝션 쿼리 사용
            if ("popular".equals(sort)) {
                posts = playlistPostRepository.findAllWithProjectionByViewCount(pageable);
            } else {
                posts = playlistPostRepository.findAllWithProjection(pageable);
            }
        }

        return posts;
    }
    
    public Page<PlaylistPostDTO> getNoticePosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<PlaylistPost> postEntities = playlistPostRepository.findByIsNoticeTrue(pageable);
        return postEntities.map(post -> PlaylistPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .user(post.getUser())
                .createdDate(post.getCreatedDate())
                .likeCount(post.getLikeCount())
                .replyCount(post.getReplyCount())
                .viewCount(post.getViewCount())
                .isNotice(post.getIsNotice())
                .build());
    }
    public List<PlaylistPost> getRanking() {
        return playlistPostRepository.findAllByOrderByLikeCountDesc();
    }

    public List<PlaylistPost> getPlaylistPosts() {
        return playlistPostRepository.findAll(); // 모든 게시글을 가져오는 메서드
    }

}