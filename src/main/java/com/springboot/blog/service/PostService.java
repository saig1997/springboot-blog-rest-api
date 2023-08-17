package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createpost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);

}
