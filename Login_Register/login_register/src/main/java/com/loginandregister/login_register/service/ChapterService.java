package com.loginandregister.login_register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.model.Chapter;
import com.loginandregister.login_register.repositories.ChapterRepository;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;
    public void save(Chapter chapter){
        chapterRepository.save(chapter);
    }

    public Chapter getChapterById(Long id) {
        return chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
    }

    public Chapter findPrevChapter(Long storyId, Long chapterId) {
        List<Chapter> chapters = chapterRepository.findPrevChapter(storyId, chapterId);
        return (chapters.isEmpty()) ? null : chapters.get(0);
    }

    public Chapter findNextChapter(Long storyId, Long chapterId) {
        List<Chapter> chapters = chapterRepository.findNextChapter(storyId, chapterId);
        return (chapters.isEmpty()) ? null : chapters.get(0);
    }

    public List<Chapter> findChaptersByStoryId(Long storyId) {
        return chapterRepository.findByStoryId(storyId);
    }
}
