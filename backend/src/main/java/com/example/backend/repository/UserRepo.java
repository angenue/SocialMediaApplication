package com.example.backend.repository;


import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    //User findByUsername(String username);
    Optional<User> findByUsername(String username); //Define a new method to find a user by username
}
