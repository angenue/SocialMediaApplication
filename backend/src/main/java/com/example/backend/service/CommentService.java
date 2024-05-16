package com.example.backend.service;

import com.example.backend.entities.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);
    Comment addReply(Long parentCommentId, Comment reply);
    Comment getCommentById(Long id);
    List<Comment> getAllCommentsByPostId(Long postId);
    void deleteComment(Long id);
    List<Comment> getReplies(Long commentId);
}