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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.post.dto.PlaylistPostDTO;
import com.musicovery.post.entity.PlaylistPost;
import com.musicovery.post.service.PlaylistPostService;
import com.musicovery.user.entity.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PlaylistPostController {

    private final PlaylistPostService playlistPostService;
    private final PagedResourcesAssembler<PlaylistPostDTO> pagedResourcesAssembler;
    
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestParam String title, @RequestParam String description, @RequestParam String playlistId, HttpSession session) {
        User user = (User) session.getAttribute("user"); // 세션에서 사용자 정보를 가져옴
        PlaylistPost post = playlistPostService.createPost(user, title, description, playlistId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId, HttpSession session) {
        User user = (User) session.getAttribute("user"); // 세션에서 사용자 정보를 가져옴
        playlistPostService.likePost(user, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/reply")
    public ResponseEntity<?> addReply(@PathVariable Long postId, @RequestParam String content, HttpSession session) {
        User user = (User) session.getAttribute("user"); // 세션에서 사용자 정보를 가져옴
        playlistPostService.addReply(user, postId, content);
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

