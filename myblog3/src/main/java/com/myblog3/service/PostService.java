package com.myblog3.service;

import com.myblog3.payload.PostDto;
import com.myblog3.payload.PostResponce;

public interface PostService {
    PostDto saveOnePost(PostDto postDto);

    PostResponce getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deleteById(long id);
}
