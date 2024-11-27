package com.loginandregister.login_register.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.loginandregister.login_register.dto.StoryTitleProjection;
import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.model.User;

public interface StoryRepository extends JpaRepository<Story, Long>{
    List<Story> findByTitleContaining(String keyword);
    Page<Story> findByUser(User user, Pageable pageable);
    long countByUser(User user);

    //list
    @Query("SELECT s FROM Story s WHERE s.views > 100 ORDER BY s.views DESC")
    List<Story> findStoriesWithViewsAbove100();

    List<Story> findByStatusOrderByCompletedDateDesc(String status);

    @Query("SELECT s FROM Story s WHERE s.createdDate >= :sevenDaysAgo ORDER BY s.createdDate DESC")
    List<Story> findStoriesCreatedWithinLastWeek(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    @Query("SELECT s, COUNT(c) FROM Story s JOIN Comment c on s.id = c.storyId GROUP BY s.id ORDER BY COUNT(c) DESC")
    List<Object[]> findMostCommentedStories();

    List<Story> findByCategoryContainingIgnoreCaseOrderByViewsDesc(String category, Pageable pageable);

    //tìm kiếm theo tên 
    @Query("SELECT s.id AS id, s.title AS title FROM Story s WHERE s.title LIKE :keyword")
    List<StoryTitleProjection> findTitlesByKeyword(@Param("keyword") String keyword);

    //chọn thể loại
    List<Story> findByCategoryContainingIgnoreCase(String category);
    //tìm tác giả
    List<Story> findByAuthor(String author);
}
