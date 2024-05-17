package com.example.backend.repository;

import com.example.backend.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepo extends JpaRepository<Reply, Long> {
    List<Reply> findAllByParentCommentCommentId(Long commentId);
}