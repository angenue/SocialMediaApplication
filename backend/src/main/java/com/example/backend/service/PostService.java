
package com.example.backend.service;

import com.example.backend.dto.PostDto;

public interface PostService {

    void createPost(PostDto postDto);
    void deletePost(Long id);
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
    int getLikes(Long postId);

}

