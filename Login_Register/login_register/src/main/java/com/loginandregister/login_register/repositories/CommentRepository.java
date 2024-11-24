package com.loginandregister.login_register.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loginandregister.login_register.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByStoryId(long storyId);
}
