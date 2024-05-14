package com.example.backend.controller;

import com.example.backend.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{postId}/like")
    public void likePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.likePost(postId, userId);
    }

    @PostMapping("/{postId}/unlike")
    public void unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.unlikePost(postId, userId);
    }

    @GetMapping("/{postId}/likes")
    public int getLikes(@PathVariable Long postId) {
        return postService.getLikes(postId);
    }

}