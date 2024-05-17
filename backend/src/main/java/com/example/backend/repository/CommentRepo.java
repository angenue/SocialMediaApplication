package com.example.backend.repository;

import com.example.backend.entities.Comment;
import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostPostId(Long postId);
    List<Comment> findAllByParentComment(Comment parentComment);
    int countByParentComment(Comment parentComment);
}
