package com.example.backend.service;

import com.example.backend.dto.CommentDto;
import com.example.backend.dto.PostDto;
import com.example.backend.dto.UserDto;
import com.example.backend.entities.Follow;
import com.example.backend.entities.User;
import com.example.backend.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


        private final UserRepo userRepo;

        public UserServiceImpl(UserRepo userRepo) {
            this.userRepo = userRepo;
        }
    public void createUser(UserDto userDto) { // Implements the createUser method from UserService
        User user = new User(); // Create a new User object
        user.setUsername(userDto.getUsername()); // Set the username of the User object to the username of the userDto
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepo.save(user); // Save the user to the database

    }

    public UserDto getUserByUsername(String username) { // Implements the getUserByUsername method from UserService
        User user = userRepo.findByUsername(username).orElseThrow(); // Find the user by username
        UserDto userDto = new UserDto(); // Create a new UserDto object
        userDto.setUserId(user.getUserId()); // Set the userId of the UserDto object to the userId of the user
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setBio(user.getBio());
        userDto.setProfilePicture(user.getProfilePicture());
        userDto.setPassword(user.getPassword());
        return userDto; // Return the UserDto object
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public void updateUser(UserDto userDto) {
        User user = userRepo.findById(userDto.getUserId()).orElseThrow();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setBio(userDto.getBio());
        user.setProfilePicture(userDto.getProfilePicture());
        userRepo.save(user); // Save the updated user to the database

    }
    public UserDto getUser(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setBio(user.getBio());
        userDto.setProfilePicture(user.getProfilePicture());
        return userDto;
    }
        public void followUser(Long followerId, Long followedId) {// Return the UserDto objectpublic void followUser(Long followerId, Long followedId) {
    User follower = userRepo.findById(followerId).orElseThrow(); // Retrieve the follower user
    User followed = userRepo.findById(followedId).orElseThrow(); // Retrieve the followed user

    Follow follow = new Follow(); // Create a new Follow entity
    follow.setFollower(follower); // Set the follower user
    follow.setFollowed(followed); // Set the followed user
    follow.setCreated(new Date()); // Set the current date as the creation date

    follower.getFollowing().add(follow); // Add the follow to the follower's following list
    followed.getFollowers().add(follow); // Add the follow to the followed's followers list

    userRepo.save(follower); // Save the follower user
    userRepo.save(followed); // Save the followed user
    }


    //retrieve a list of posts liked by a user
    @Override
    public List<PostDto> getLikedPostsByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //only returning the fields we want to display such as username, content, and date
        return user.getLikedPosts().stream()
                .map(post -> {
                    PostDto postDto = new PostDto();
                    postDto.setPostId(post.getPostId());
                    postDto.setContent(post.getContent());
                    postDto.setCreated(post.getCreated());
                    postDto.setUsername(post.getUser().getUsername());
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    //return a list of comments made by a user
    @Override
    public List<CommentDto> getCommentsByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //only returning the fields we want to display such as username, content, and date
        return user.getComments().stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setCommentId(comment.getCommentId());
                    commentDto.setContent(comment.getContent());
                    commentDto.setCreated(comment.getCreated());
                    commentDto.setUsername(comment.getUser().getUsername());

                    return commentDto;
                })
                .collect(Collectors.toList());
    }
}

