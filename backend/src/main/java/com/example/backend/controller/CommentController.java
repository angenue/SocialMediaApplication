package com.example.backend.controller;

import com.example.backend.dto.CommentDto;
import com.example.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addComment(postId, commentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getAllCommentsByPostId(postId));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeComment(@PathVariable Long id, @RequestBody Long userId) {
        commentService.likeComment(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikeComment(@PathVariable Long id, @RequestBody Long userId) {
        commentService.unlikeComment(id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}