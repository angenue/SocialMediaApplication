package com.example.backend.service;

import com.example.backend.dto.PostDto;
import com.example.backend.dto.UserDto;
import com.example.backend.entities.Follow;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repository.PostRepo;
import com.example.backend.repository.UserRepo;
import com.example.backend.util.UserContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final UserContextService userContextService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);


    public PostServiceImpl(PostRepo postRepo, UserRepo userRepo, UserContextService userContextService) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.userContextService = userContextService;
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

    public void deletePost(Long postId) {
        User currentUser = userContextService.getCurrentUser();

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!currentUser.getUserId().equals(post.getUser().getUserId())) {
            throw new SecurityException("You are not authorized to delete this user");
        }

        postRepo.delete(post);
    }

    @Override
    public PostDto getPost(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToPostDto(post);
    }

    //for when we create the main feed to return posts from users that the current user follows
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

    //for when user clicks on a user profile to see their posts
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
        if (!post.getLikedUsers().contains(user)) {
            post.getLikedUsers().add(user); //add user to list of liked users
            post.setNumLikes(post.getLikedUsers().size());
            postRepo.save(post);
            LOGGER.info("User {} liked post {}. Liked users: {}", userId, postId, post.getLikedUsers());
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
            post.setNumLikes(post.getLikedUsers().size()); // Update like count
            postRepo.save(post);
        }
    }

    @Override
    public int getLikes(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getLikedUsers().size();
    }


    //helper method to get posts
    private PostDto mapToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setContent(post.getContent());
        postDto.setCreated(post.getCreated().toString());
        postDto.setNumLikes(post.getNumLikes());
        postDto.setNumComments(post.getNumComments());
        postDto.setProfilePic(post.getUser().getProfilePicture());

        // Set the username of the user who created the post
        postDto.setUsername(post.getUser().getUsername());

        return postDto;
    }


//helper method to get list of users who liked the post
    @Override
    public List<UserDto> getLikedUsers(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        //LOGGER.info("Liked users for post {}: {}", postId, post.getLikedUsers());
        return post.getLikedUsers().stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setUsername(user.getUsername());
                    userDto.setUserId(user.getUserId());
                    return userDto;
                })
                .collect(Collectors.toList());
    }

}
