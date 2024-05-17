package com.example.backend.controller;

import com.example.backend.dto.ReplyDto;
import com.example.backend.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<ReplyDto> addReply(@PathVariable Long commentId, @RequestBody ReplyDto replyDto) {
        return ResponseEntity.ok(replyService.addReply(commentId, replyDto));
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<ReplyDto>> getAllRepliesByCommentId(@PathVariable Long commentId) {
        return ResponseEntity.ok(replyService.getAllRepliesByCommentId(commentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
        replyService.deleteReply(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeReply(@PathVariable Long id, @RequestBody Long userId) {
        replyService.likeReply(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikeReply(@PathVariable Long id, @RequestBody Long userId) {
        replyService.unlikeReply(id, userId);
        return ResponseEntity.noContent().build();
    }
}