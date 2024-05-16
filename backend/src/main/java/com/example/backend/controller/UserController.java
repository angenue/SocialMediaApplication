package com.example.backend.controller;

import com.example.backend.dto.CommentDto;
import com.example.backend.dto.PostDto;
import com.example.backend.dto.UserDto;
import com.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/user") // This means URL's start with /user (after Application path)

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/register")
    public void createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }

    @PostMapping("/{followerId}/follow/{followedId}")
    public void followUser(@PathVariable Long followerId, @PathVariable Long followedId) {
        userService.followUser(followerId, followedId);
    }

    @GetMapping("/{userId}/likedPosts")
    public List<PostDto> getLikedPostsByUser(@PathVariable Long userId) {
        return userService.getLikedPostsByUser(userId);
    }

    @GetMapping("/{userId}/comments")
    public List<CommentDto> getCommentsByUser(@PathVariable Long userId) {
        return userService.getCommentsByUser(userId);
    }
}

