
package com.example.backend.service;

import com.example.backend.dto.PostDto;

import java.util.List;

public interface PostService {

    void createPost(PostDto postDto);
    void deletePost(Long id);

    PostDto getPost(Long id);
    List<PostDto> getPostsOfFollowedUsers(Long userId);
    List<PostDto> getPostsByUser(Long userId);
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
    int getLikes(Long postId);

}

