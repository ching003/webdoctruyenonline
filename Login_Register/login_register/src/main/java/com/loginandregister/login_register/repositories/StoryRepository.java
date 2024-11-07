package com.loginandregister.login_register.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.model.User;

public interface StoryRepository extends JpaRepository<Story, Long>{
    List<Story> findByTitleContaining(String keyword);
    List<Story> findByUser(User user);
    long countByUser(User user);

    //bonus
    List<Story> findByOrderByViewsDesc(Pageable pageable);
    List<Story> findByStatusOrderByCompletedDateDesc(String status, Pageable pageable);

    //tìm kiếm theo tên 
    @Query("SELECT s FROM Story s WHERE s.title LIKE %:query% OR s.author LIKE %:query%")
    List<Story> searchStories(@Param("query") String query);

    //chọn thể loại
    List<Story> findByCategoryContainingIgnoreCase(String category);
}
