package com.loginandregister.login_register.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.loginandregister.login_register.model.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByStoryId(Long storyId);
    
    @Query("SELECT c FROM Chapter c WHERE c.story.id = :storyId AND c.id < :chapterId ORDER BY c.id DESC")
    List<Chapter> findPrevChapter(@Param("storyId") Long storyId, @Param("chapterId") Long chapterId);

    @Query("SELECT c FROM Chapter c WHERE c.story.id = :storyId AND c.id > :chapterId ORDER BY c.id ASC")
    List<Chapter> findNextChapter(@Param("storyId") Long storyId, @Param("chapterId") Long chapterId);

    // Lấy 10 chương mới nhất theo ngày tạo
    List<Chapter> findTop10ByOrderByCreatedDateDesc();

    // Lấy tất cả chương của một truyện cụ thể và sắp xếp theo ngày tạo
    List<Chapter> findByStoryIdOrderByCreatedDateDesc(Long storyId);

    // Lấy chương mới nhất của một truyện
    Chapter findTopByStoryIdOrderByCreatedDateDesc(Long storyId);
}
