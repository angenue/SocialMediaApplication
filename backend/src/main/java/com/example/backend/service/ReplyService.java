package com.example.backend.service;

import com.example.backend.dto.ReplyDto;

import java.util.List;

public interface ReplyService {
    ReplyDto addReply(Long parentCommentId, ReplyDto replyDto);
    List<ReplyDto> getAllRepliesByCommentId(Long commentId);
    void deleteReply(Long id);
    void likeReply(Long replyId, Long userId);
    void unlikeReply(Long replyId, Long userId);
}