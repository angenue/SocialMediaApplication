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

    public void createUser(UserDto userDto) {
        //check if the email or username is already taken
        checkEmailTaken(userDto.getEmail());
        checkUsernameTaken(userDto.getUsername());

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getCurrentPassword());
        userRepo.save(user);
    }

    public UserDto getUserByUsername(String username) { // Implements the getUserByUsername method from UserService
        User user = userRepo.findByUsername(username).orElseThrow(); // Find the user by username
        return mapToDto(user); // Return the UserDto object
    }

    public UserDto getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        return mapToDto(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void updateProfile(UserDto userDto) {
        User user = userRepo.findById(userDto.getUserId()).orElseThrow();

        // If the username in the UserDto object is not null and is different from the current username
        if (userDto.getUsername() != null && !user.getUsername().equals(userDto.getUsername())) {
            // Check if the new username is already taken
            checkUsernameTaken(userDto.getUsername());
            user.setUsername(userDto.getUsername());
        }

        // If the name in the UserDto object is not null, update the name
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        // If the email in the UserDto object is not null and is different from the current email
        if (userDto.getEmail() != null && !user.getEmail().equals(userDto.getEmail())) {
            // Check if the new email is already taken
            checkEmailTaken(userDto.getEmail());
            // If not, update the email
            user.setEmail(userDto.getEmail());
        }

        // If the bio in the UserDto object is not null, update the bio
        if (userDto.getBio() != null) {
            user.setBio(userDto.getBio());
        }

        // If the profile picture in the UserDto object is not null, update the profile picture
        if (userDto.getProfilePicture() != null) {
            user.setProfilePicture(userDto.getProfilePicture());
        }

        // Save the updated user back to the database
        userRepo.save(user);
    }

    @Override
    public void updatePassword(Long userId, String currentPassword, String newPassword, String repeatedNewPassword) {
        User user = userRepo.findById(userId).orElseThrow();

        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Current password is incorrect");
        }

        if (!newPassword.equals(repeatedNewPassword)) {
            throw new RuntimeException("New passwords do not match");
        }

        user.setPassword(newPassword);
        userRepo.save(user);
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
                    postDto.setCreated(post.getCreated().toString());
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
                    commentDto.setCreated(comment.getCreated().toString());
                    commentDto.setUsername(comment.getUser().getUsername());

                    return commentDto;
                })
                .collect(Collectors.toList());
    }

    private void checkEmailTaken(String email) {
        User existingUserByEmail = userRepo.findByEmail(email);
        if (existingUserByEmail != null) {
            throw new RuntimeException("Email is already taken");
        }
    }

    private void checkUsernameTaken(String username) {
        User existingUserByUsername = userRepo.findByUsername(username).orElse(null);
        if (existingUserByUsername != null) {
            throw new RuntimeException("Username is already taken");
        }
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setBio(user.getBio());
        userDto.setProfilePicture(user.getProfilePicture());
        return userDto;
    }
}

