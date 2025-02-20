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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.post.dto.PlaylistPostDTO;
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
            @RequestHeader("Authorization") String bearerToken,@PathVariable String userId,
            @RequestParam String title, @RequestParam String description, @RequestParam String playlistId) {
        User user = userService.findByUserId(userId); // userId를 통해 User 객체 조회
        String accessToken = bearerToken.replace("Bearer ", "");
        PlaylistPost post = playlistPostService.createPost(accessToken, user, title, description, playlistId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> likePost(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String userId,
            @PathVariable Long postId) {
        User user = userService.findByUserId(userId); // userId를 통해 User 객체 조회
        String accessToken = bearerToken.replace("Bearer ", "");
        playlistPostService.likePost(accessToken, user, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply/{postId}")
    public ResponseEntity<?> addReply(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String userId,
            @PathVariable Long postId, @RequestParam String content) {
        User user = userService.findByUserId(userId); // userId를 통해 User 객체 조회
        String accessToken = bearerToken.replace("Bearer ", "");
        playlistPostService.addReply(accessToken, user, postId, content);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<PlaylistPost>> getRanking() {
        List<PlaylistPost> ranking = playlistPostService.getRanking();
        return ResponseEntity.ok(ranking);
    }
    @GetMapping("/list")
    public ResponseEntity<PagedModel<EntityModel<PlaylistPostDTO>>> getPlaylistPosts(@RequestParam int page, @RequestParam int size) {
        Page<PlaylistPostDTO> posts = playlistPostService.getPlaylistPosts(page, size);
        PagedModel<EntityModel<PlaylistPostDTO>> pagedModel = pagedResourcesAssembler.toModel(posts);
        return ResponseEntity.ok(pagedModel);
    }
}

