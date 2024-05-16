package com.example.backend.service;

import com.example.backend.dto.PostDto;
import com.example.backend.dto.UserDto;
import com.example.backend.entities.Follow;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repository.PostRepo;
import com.example.backend.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public PostDto getPost(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToPostDto(post);
    }

    @Override
    public List<PostDto> getPostsOfFollowedUsers(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowing().stream()
                .map(Follow::getFollowed) // Get the User entity from each Follow entity
                .flatMap(followedUser -> followedUser.getPosts().stream())
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getPosts().stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public void likePost(Long postId, Long userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        // if post is not liked by the current user
        if (!post.getLikedUsers().contains(user)) { //checks if user is in the list of liked users
            post.getLikedUsers().add(user); //add user to list of liked users
            post.setNumLikes(post.getNumLikes() + 1); //increase like count
            postRepo.save(post);
        }
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user has liked the post
        if (post.getLikedUsers().contains(user)) {
            post.getLikedUsers().remove(user); // Remove the user from the set of users who have liked the post
            post.setNumLikes(post.getNumLikes() - 1); // Decrease like count
            postRepo.save(post);
        }
    }

    @Override
    public int getLikes(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getNumLikes();
    }


    //helper method to get posts
    private PostDto mapToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setContent(post.getContent());
        postDto.setCreated(post.getCreated());
        postDto.setNumLikes(post.getNumLikes());

        // Set the username of the user who created the post
        postDto.setUsername(post.getUser().getUsername());

        return postDto;
    }
}
