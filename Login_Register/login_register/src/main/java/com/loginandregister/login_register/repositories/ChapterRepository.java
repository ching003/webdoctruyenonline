package com.loginandregister.login_register.repositories;


import java.util.List;
import java.util.Optional;

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

}
