package com.myblog3.controller;

import com.myblog3.payload.PostDto;
import com.myblog3.payload.PostResponce;
import com.myblog3.service.PostService;
import com.myblog3.utils.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
   @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> savePost(@Valid @RequestBody PostDto postDto , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

       return new ResponseEntity<>( postService.saveOnePost(postDto), HttpStatus.CREATED);
    }

    // http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public PostResponce getAllPost(@RequestParam(value = "pageNo",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER,required = false)int pageNo,
                                   @RequestParam(value = "pageSize",defaultValue = AppConstant.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                   @RequestParam(value = "sortBy" , defaultValue = AppConstant.DEFAULT_SORT_BY,required = false)String sortBy,
                                   @RequestParam(value = "sortDir",defaultValue = AppConstant.DEFAULT_SORT_DIR,required = false)String sortDir){
      return   postService.getAllPost(pageNo,pageSize,sortBy,sortDir);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
       return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }
   // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable("id") long id){
      return new ResponseEntity<> (postService.updatePost(postDto,id),HttpStatus.OK);
    }
   // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post Delete Successfully",HttpStatus.OK);
    }

}
