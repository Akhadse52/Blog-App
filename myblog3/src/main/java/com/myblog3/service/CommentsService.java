package com.myblog3.service;

import com.myblog3.payload.CommentDto;

import java.util.List;

public interface CommentsService {
    CommentDto saveCommentByPostId(CommentDto commentDto, long postId);

    List<CommentDto> getAllCommentByPostId(long postId);

    CommentDto getCommentById(long postId, long id);

    CommentDto updateCommentByPostId(CommentDto commentDto, long postId, long id);

    void deleteCommentById(long postId, long id);
}
