package com.example.backend.service;

import com.example.backend.dto.CommentDto;
import com.example.backend.entities.Comment;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repository.CommentRepo;
import com.example.backend.repository.PostRepo;
import com.example.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public CommentDto addComment(Long postId, CommentDto commentDto) {
        Comment comment = new Comment();

        // get the post that the comment is being made on
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        comment.setContent(commentDto.getContent());
        comment.setUser(userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        comment.setCreated(new Date());
        comment.setPost(post);

        post.setNumComments(post.getComments().size()); // increment the number of comments

        postRepo.save(post);

        comment = commentRepository.save(comment);

        return mapToDto(comment);
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
        String currentUsername = getCurrentUsername();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getUsername().equals(currentUsername)) {
            throw new SecurityException("You are not authorized to delete this post");
        }

        Post post = comment.getPost();

        if (post != null) {
            post.getComments().remove(comment); // remove the comment from the post's comments list
            post.setNumComments(post.getComments().size()); // update the number of comments
            postRepo.save(post);
        }

        commentRepository.delete(comment);
    }

    @Override
    public void likeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // add the user to the list of users who liked the comment
        comment.getLikedUsers().add(user);
        // set the number of likes to the size of the liked users list
        comment.setNumLikes(comment.getLikedUsers().size());

        commentRepository.save(comment);
    }

    @Override
    public void unlikeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // remove the user from the list of users who liked the comment
        comment.getLikedUsers().remove(user);
        // set the number of likes to the size of the liked users list
        comment.setNumLikes(comment.getLikedUsers().size());

        commentRepository.save(comment);
    }

    // map a Comment entity to a CommentDto for sending to the client side
    private CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getCommentId());
        dto.setContent(comment.getContent());
        dto.setNumLikes(comment.getNumLikes());
        dto.setCreated(comment.getCreated().toString());
        dto.setUsername(comment.getUser().getUsername());
        dto.setProfilePic(comment.getUser().getProfilePicture());
        dto.setNumReplies(comment.getNumReplies());
        return dto;
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
