package com.example.backend.controller;

import com.example.backend.dto.PostDto;
import com.example.backend.dto.UserDto;
import com.example.backend.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public void createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
    }

    @GetMapping("/{postId}")
    public PostDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @GetMapping("/user/{userId}")
    public List<PostDto> getPostsByUser(@PathVariable Long userId) {
        return postService.getPostsByUser(userId);
    }

    @GetMapping("/followed/{userId}")
    public List<PostDto> getPostsOfFollowedUsers(@PathVariable Long userId) {
        return postService.getPostsOfFollowedUsers(userId);
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

    @GetMapping("/{postId}/likedUsers")
    public List<UserDto> getLikedUsers(@PathVariable Long postId) {
        return postService.getLikedUsers(postId);
    }
}