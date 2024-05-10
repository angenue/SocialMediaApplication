package com.example.backend.repository;

import com.example.backend.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepo extends JpaRepository<Follow, Long> {
}
