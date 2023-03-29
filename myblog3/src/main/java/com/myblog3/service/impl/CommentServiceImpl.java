package com.myblog3.service.impl;

import com.myblog3.entity.Comment;
import com.myblog3.entity.Post;
import com.myblog3.exception.BlogApiException;
import com.myblog3.exception.ResourceNotFoundException;
import com.myblog3.payload.CommentDto;
import com.myblog3.repository.CommentsRepository;
import com.myblog3.repository.PostRepository;
import com.myblog3.service.CommentsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentsService {

    private PostRepository postRepo;
    private CommentsRepository commentsRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentsRepository commentsRepo) {
        this.postRepo = postRepo;
        this.commentsRepo = commentsRepo;
    }

    @Override
    public CommentDto saveCommentByPostId(CommentDto commentDto, long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = mapToCommentEntity(commentDto);
        comment.setPost(post);
        Comment commentEntity = commentsRepo.save(comment);
       return  mapToCommentDto(commentEntity);

    }

    @Override
    public List<CommentDto> getAllCommentByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        List<Comment> comment = commentsRepo.findByPostId(postId);
        return comment.stream().map(x -> mapToCommentDto(x)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", id));


        if (comment.getPost().getId()!=post.getId()) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }
            return mapToCommentDto(comment);
        }



    @Override
    public CommentDto updateCommentByPostId(CommentDto commentDto, long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("comments", "id", id));
        if(comment.getPost().getId()!=post.getId()){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Comments is not belong to post Id");
        }
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        Comment commentEntity = commentsRepo.save(comment);
         return  mapToCommentDto(commentEntity);

    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", id));
        if(comment.getPost().getId()!= post.getId()){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Comments is not belong to post Id");
        }
        commentsRepo.delete(comment);
    }

    Comment mapToCommentEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
    CommentDto mapToCommentDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        dto.setEmail(comment.getEmail());
        dto.setName(comment.getName());
        return dto;
    }
}
