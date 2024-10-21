package com.loginandregister.login_register.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.loginandregister.login_register.model.Story;

public interface StoryRepository extends JpaRepository<Story, Long>{
    List<Story> findByTitleContaining(String keyword);
}
