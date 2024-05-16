package com.example.backend.service;

import com.example.backend.dto.CommentDto;
import com.example.backend.dto.PostDto;
import com.example.backend.dto.UserDto;

import java.util.List;

public interface UserService {

    public void createUser(UserDto userDto);


    UserDto getUser(Long id);

    UserDto getUserByUsername(String username); // new method

    void deleteUser(Long id);

    void followUser(Long followerId, Long followedId); // new method

    List<PostDto> getLikedPostsByUser(Long userId); //retrieve all posts liked by a user
    List<CommentDto> getCommentsByUser(Long userId); //retrieve all comments made by a user
}


