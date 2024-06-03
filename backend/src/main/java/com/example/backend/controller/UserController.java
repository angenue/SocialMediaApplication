package com.example.backend.controller;

import com.example.backend.dto.CommentDto;
import com.example.backend.dto.PostDto;
import com.example.backend.dto.UserDto;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            UserDto userDto = userService.getUserByUsername(userDetails.getUsername());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and @userContextService.isOwner(#userDto.userId)")//ensures that user id equals the id of the logged in user
    @PutMapping("/settings/{userId}")
    public ResponseEntity<Void> updateProfile(@Valid @RequestBody UserDto userDto) {
        userService.updateProfile(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and @userContextService.isOwner(#userId)")
    @PutMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        userService.updatePassword(userId, userDto.getPassword(), userDto.getNewPassword(), userDto.getNewPasswordConfirmation());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and @userContextService.isOwner(#userId)")

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

