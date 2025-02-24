package com.musicovery.post.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.post.dto.PlaylistPostDTO;
import com.musicovery.post.dto.ReplyDTO;
import com.musicovery.post.entity.PlaylistPost;
import com.musicovery.post.service.PlaylistPostService;
import com.musicovery.user.entity.User;
import com.musicovery.user.service.UserService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PlaylistPostController {

    private final PlaylistPostService playlistPostService;
    private final PagedResourcesAssembler<PlaylistPostDTO> pagedResourcesAssembler;
    private final UserService userService; 

    @PostMapping("/create")
    public ResponseEntity<?> createPost(
            @RequestHeader("Authorization") String bearerToken, @RequestParam String userId,
            @RequestParam String title, @RequestParam String description, @RequestParam String playlistId) {
        User user = userService.findByUserId(userId);
        String accessToken = bearerToken.replace("Bearer ", "");
        PlaylistPost post = playlistPostService.createPost(accessToken, user, title, description, playlistId);
        return ResponseEntity.ok(post);
    }


    @PutMapping("/update/{postId}")
    public ResponseEntity<?> updatePost(
            @RequestHeader("Authorization") String bearerToken, @PathVariable Long postId,
            @RequestParam String title, @RequestParam String description, @RequestParam String playlistId) {
        String accessToken = bearerToken.replace("Bearer ", "");
        PlaylistPost post = playlistPostService.updatePost(accessToken, postId, title, description, playlistId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> likePost(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam String userId,
            @PathVariable Long postId) {
        User user = userService.findByUserId(userId); // userId를 통해 User 객체 조회
        String accessToken = bearerToken.replace("Bearer ", "");
        playlistPostService.likePost(accessToken, user, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/like/{postId}")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Long postId) {
        int likeCount = playlistPostService.getLikeCount(postId);
        return ResponseEntity.ok(likeCount);
    }
    
    
    @PostMapping("/reply/{postId}")
    public ResponseEntity<?> addReply(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam String userId,
            @PathVariable Long postId, @RequestParam String content) {
        User user = userService.findByUserId(userId); // userId를 통해 User 객체 조회
        String accessToken = bearerToken.replace("Bearer ", "");
        playlistPostService.addReply(accessToken, user, postId, content);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reply/{postId}")
    public ResponseEntity<List<ReplyDTO>> getReplies(@PathVariable Long postId) {
        List<ReplyDTO> replies = playlistPostService.getRepliesByPostId(postId);
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<PlaylistPost>> getRanking() {
        List<PlaylistPost> ranking = playlistPostService.getRanking();
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/list")
    public ResponseEntity<PagedModel<EntityModel<PlaylistPostDTO>>> getPlaylistPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword) {
        Page<PlaylistPostDTO> posts = playlistPostService.getPlaylistPosts(page, size, sort, searchType, keyword);
        PagedModel<EntityModel<PlaylistPostDTO>> pagedModel = pagedResourcesAssembler.toModel(posts);
        return ResponseEntity.ok(pagedModel);
    }
}

