package com.myblog3.controller;

import com.myblog3.payload.CommentDto;
import com.myblog3.service.CommentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentsService commentsService;

    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> saveCommentByPostId(@RequestBody CommentDto commentDto, @PathVariable("postId") long postId){
       return new ResponseEntity<>( commentsService.saveCommentByPostId(commentDto,postId), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentById(@PathVariable("postId")long postId){
      return new ResponseEntity<>(commentsService.getAllCommentByPostId(postId),HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,@PathVariable("id") long id){
       return new ResponseEntity<>(commentsService.getCommentById(postId,id),HttpStatus.OK);
    }
    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentByPostId(@RequestBody CommentDto commentDto ,@PathVariable("postId") long postId, @PathVariable("id") long id){
       return new ResponseEntity<>(commentsService.updateCommentByPostId(commentDto,postId,id),HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") long postId,@PathVariable("id") long id){
        commentsService.deleteCommentById(postId,id);
        return new ResponseEntity<>("Comment Delete Successfully",HttpStatus.OK);
    }
}
