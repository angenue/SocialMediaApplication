package com.example.backend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.example.backend.repository.UserRepo;
import com.example.backend.entities.User;

@Component
public class UserContextService {

    private final UserRepo userRepo;

    public UserContextService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        throw new UsernameNotFoundException("User not authenticated");
    }

    public boolean isOwner(Long userId) {
        User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getUserId().equals(userId);
    }
}
