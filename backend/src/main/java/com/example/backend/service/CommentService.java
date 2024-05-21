package com.example.backend.service;

import com.example.backend.dto.CommentDto;
import com.example.backend.entities.Comment;

import java.util.List;

public interface CommentService {
    CommentDto addComment(Long postId, CommentDto commentDto);
    CommentDto getCommentById(Long id);
    List<CommentDto> getAllCommentsByPostId(Long postId);
    void deleteComment(Long id);
    void likeComment(Long commentId, Long userId);
    void unlikeComment(Long commentId, Long userId);
}