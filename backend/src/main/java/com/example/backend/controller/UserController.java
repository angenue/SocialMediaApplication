package com.example.backend.controller;

import com.example.backend.dto.CommentDto;
import com.example.backend.dto.PostDto;
import com.example.backend.dto.UserDto;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This means that this class is a Controller

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

   /* @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }*/

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/settings/{userId}")
    public ResponseEntity<Void> updateProfile(@Valid @RequestBody UserDto userDto) {
        userService.updateProfile(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        userService.updatePassword(userId, userDto.getCurrentPassword(), userDto.getNewPassword(), userDto.getNewPasswordConfirmation());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

