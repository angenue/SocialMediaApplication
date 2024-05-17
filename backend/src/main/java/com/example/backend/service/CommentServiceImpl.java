package com.example.backend.service;

import com.example.backend.dto.CommentDto;
import com.example.backend.entities.Comment;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repository.CommentRepo;
import com.example.backend.repository.PostRepo;
import com.example.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepository;
    private final UserRepo userRepository;
    private final PostRepo postRepo;

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepository, UserRepo userRepository, PostRepo postRepo) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setContent(commentDto.getContent());
        comment.setUser(userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        comment.setCreated(new Date());

        Post post = postRepo.findById(commentDto.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post); // set the post that the comment is being made on


        comment = commentRepository.save(comment); // save the comment to the database
        post.setNumComments(post.getNumComments() + 1); // increment the number of comments
        postRepo.save(post);
        return mapToDto(comment);
    }

    @Override
    public CommentDto addReply(Long parentCommentId, CommentDto replyDto) {
        // find the parent comment
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        Comment reply = new Comment();
        reply.setContent(replyDto.getContent());
        reply.setUser(userRepository.findById(replyDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        reply.setParentComment(parentComment);
        reply.setCreated(new Date());
        // set the user that this comment is a reply to
        User repliedTo = userRepository.findByUsername(replyDto.getRepliedToUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        reply.setRepliedTo(repliedTo);

        reply = commentRepository.save(reply);
        parentComment.setNumReplies(parentComment.getNumReplies() + 1); // increment the number of replies
        commentRepository.save(parentComment);
        return mapToDto(reply);
    }

    @Override
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return mapToDto(comment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostPostId(postId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Post post = comment.getPost();

        if (post != null) {
            post.setNumComments(post.getNumComments() - 1 - comment.getNumReplies()); // decrement the number of comments and replies
            postRepo.save(post);
        }

        // if the comment is a reply, decrement the number of replies of the parent comment
        Comment parentComment = comment.getParentComment();
        if (parentComment != null) {
            parentComment.setNumReplies(parentComment.getNumReplies() - 1); // decrement the number of replies
            commentRepository.save(parentComment);
        }

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getReplies(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return commentRepository.findAllByParentComment(comment).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getCommentId());
        dto.setContent(comment.getContent());
        dto.setNumLikes(comment.getNumLikes());

        dto.setCreated(comment.getCreated().toString());
        dto.setUsername(comment.getUser().getUsername());
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getCommentId().toString());
        }
        if (comment.getRepliedTo() != null) {
            dto.setRepliedToUsername(comment.getRepliedTo().getUsername());
        }
        return dto;
    }
}
