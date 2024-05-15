package com.example.backend.service;

import com.example.backend.dto.UserDto;

public interface UserService {

    public void createUser(UserDto userDto);


    UserDto getUser(Long id);

    UserDto getUserByUsername(String username); // new method

    void deleteUser(Long id);

    void followUser(Long followerId, Long followedId); // new method
}


