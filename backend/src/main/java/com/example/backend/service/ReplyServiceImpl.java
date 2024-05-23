package com.example.backend.service;

import com.example.backend.dto.ReplyDto;
import com.example.backend.entities.Comment;
import com.example.backend.entities.Reply;
import com.example.backend.entities.User;
import com.example.backend.repository.CommentRepo;
import com.example.backend.repository.ReplyRepo;
import com.example.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepo replyRepository;
    private final UserRepo userRepository;
    private final CommentRepo commentRepo;

    @Autowired
    public ReplyServiceImpl(ReplyRepo replyRepository, UserRepo userRepository, CommentRepo commentRepo) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
        this.commentRepo = commentRepo;
    }

    @Override
    public ReplyDto addReply(Long parentCommentId, ReplyDto replyDto) {
        Reply reply = new Reply();

        //the comment that you are replying to
        Comment parentComment = commentRepo.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        //your user id
        User user = userRepository.findById(replyDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        //user that you are replying to
        User repliedTo = userRepository.findByUsername(replyDto.getRepliedToUsername())
                .orElseThrow(() -> new RuntimeException("Replied-to user not found"));

        reply.setContent(replyDto.getContent());
        reply.setUser(user);
        reply.setRepliedTo(repliedTo);
        reply.setParentComment(parentComment);
        reply.setCreated(new Date());

        reply = replyRepository.save(reply);

        parentComment.setNumReplies(parentComment.getReplies().size()); // increment the number of replies

        commentRepo.save(parentComment);

        return mapToDto(reply);
    }
    @Override
    public List<ReplyDto> getAllRepliesByCommentId(Long commentId) {
        return replyRepository.findAllByParentCommentCommentId(commentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReply(Long id) {
        String currentUsername = getCurrentUsername();
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        if (!reply.getUser().getUsername().equals(currentUsername)) {
            throw new SecurityException("You are not authorized to delete this post");
        }

        Comment parentComment = reply.getParentComment();

        if (parentComment != null) {
            parentComment.getReplies().remove(reply); // remove the reply from the parent comment's replies list
            parentComment.setNumReplies(parentComment.getReplies().size()); // update the number of replies
            commentRepo.save(parentComment);
        }

        replyRepository.delete(reply);
    }

    @Override
    public void likeReply(Long replyId, Long userId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //add the user to the list of liked users
        reply.getLikedUsers().add(user);
        //set the number of likes to the size of the liked users list
        reply.setNumLikes(reply.getLikedUsers().size());

        replyRepository.save(reply);
    }

    @Override
    public void unlikeReply(Long replyId, Long userId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //remove the user from the list of liked users
        reply.getLikedUsers().remove(user);
        //set the number of likes to the size of the liked users list
        reply.setNumLikes(reply.getLikedUsers().size());

        replyRepository.save(reply);
    }

    //helper method for code reuse
    private ReplyDto mapToDto(Reply reply) {
        ReplyDto dto = new ReplyDto();
        dto.setReplyId(reply.getReplyId());
        dto.setContent(reply.getContent());
        dto.setNumLikes(reply.getNumLikes());
        dto.setCreated(reply.getCreated().toString());
        dto.setUsername(reply.getUser().getUsername());
        dto.setProfilePic(reply.getUser().getProfilePicture());
        dto.setParentCommentId(reply.getParentComment().getCommentId());
        dto.setRepliedToUsername(reply.getRepliedTo().getUsername());
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