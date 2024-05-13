package com.example.backend.service;

import com.example.backend.dto.PostDto;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repository.PostRepo;
import com.example.backend.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public PostServiceImpl(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void createPost(PostDto postDto) {
        Post  post = new Post();

        // find the user by username
        User user = userRepo.findByUsername(postDto.getUser().getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setUser(user);
        post.setContent(postDto.getContent());
        post.setCreated(new Date());
        postRepo.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }

    @Override
    public void likePost(Long postId, Long userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        // if post is not liked by the current user
        if (!post.isLikedByCurrentUser()) {
            post.setNumLikes(post.getNumLikes() + 1); //increase like count
            post.setLikedByCurrentUser(true);
            postRepo.save(post);
        }
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //if post is not liked by the current user
        if (post.isLikedByCurrentUser()) {
            post.setNumLikes(post.getNumLikes() - 1); //remove a like
            post.setLikedByCurrentUser(false);
            postRepo.save(post);
        }
    }

    @Override
    public int getLikes(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getNumLikes();
    }
}
