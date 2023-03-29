package com.myblog3.repository;

import com.myblog3.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(long postId);

}
