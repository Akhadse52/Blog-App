package com.myblog3.service.impl;

import com.myblog3.entity.Post;
import com.myblog3.exception.ResourceNotFoundException;
import com.myblog3.payload.PostDto;
import com.myblog3.payload.PostResponce;
import com.myblog3.repository.PostRepository;
import com.myblog3.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto saveOnePost(PostDto postDto) {

        Post post = mapToPostEntity(postDto);
        Post postEntity = postRepo.save(post);
       return  mapToPostDto(postEntity);

    }

    @Override
    public PostResponce getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepo.findAll(pageable);
        List<Post> postEntity = postPage.getContent();
        List<PostDto> postDto = postEntity.stream().map(x -> mapToPostDto(x)).collect(Collectors.toList());
        PostResponce postResponce = new PostResponce();
        postResponce.setPostDto(postDto);
        postResponce.setPageNo(postPage.getNumber());
        postResponce.setPageSize(postPage.getSize());
        postResponce.setTotalPage(postPage.getTotalPages());
        postResponce.setTotalElement(postPage.getTotalElements());
        postResponce.setLast(postPage.isLast());

        return postResponce;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
         return mapToPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatepost = postRepo.save(post);
       return mapToPostDto(updatepost);

    }

    @Override
    public void deleteById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepo.delete(post);
    }

    Post mapToPostEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    PostDto mapToPostDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());

    }
}
