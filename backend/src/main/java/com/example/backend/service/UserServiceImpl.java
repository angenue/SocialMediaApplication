package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.entities.User;
import com.example.backend.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


        private final UserRepo userRepo;

        public UserServiceImpl(UserRepo userRepo) {
            this.userRepo = userRepo;
        }
    public void createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepo.save(user);

    }

    public UserDto getUserByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setBio(user.getBio());
        userDto.setProfilePicture(user.getProfilePicture());
        userDto.setPassword(user.getPassword());
        return userDto;
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
        userRepo.save(user);

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
}
