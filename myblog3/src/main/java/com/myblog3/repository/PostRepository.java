package com.myblog3.repository;

import com.myblog3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post , Long> {
}
