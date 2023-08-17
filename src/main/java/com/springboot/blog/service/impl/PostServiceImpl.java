package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    @Override
    public PostDto createpost(PostDto postDto) {

        //Convert Dto to entity
        Post post = mapToEntity(postDto);

        Post newpost = postRepository.save(post);

        //Convert entity to Dto
        PostDto postResponse = mapToDTO(newpost);

        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts= postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //get post by id from the database
        Post post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        //delete post by id from database
        Post post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);

    }

    //convert entity into DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }
}
