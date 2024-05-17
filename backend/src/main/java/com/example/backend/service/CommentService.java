package com.example.backend.service;

import com.example.backend.dto.CommentDto;
import com.example.backend.entities.Comment;

import java.util.List;

public interface CommentService {
    CommentDto addComment(Long postId, CommentDto commentDto);
    CommentDto addReply(Long parentCommentId, CommentDto replyDto);
    CommentDto getCommentById(Long id);
    List<CommentDto> getAllCommentsByPostId(Long postId);
    void deleteComment(Long id);
    List<CommentDto> getReplies(Long commentId);
    int getCommentCount(Long postId);
}