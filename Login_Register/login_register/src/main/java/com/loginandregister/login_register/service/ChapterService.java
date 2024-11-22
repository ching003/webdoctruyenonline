package com.loginandregister.login_register.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.dto.ChapterDto;
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

    public List<ChapterDto> getRecentChaptersWithElapsedTime() {
        List<Chapter> chapters = chapterRepository.findTop10ByOrderByCreatedDateDesc(); // Lấy 10 chương mới nhất
        return chapters.stream().map(chapter -> new ChapterDto(
                chapter.getId(),
                chapter.getStory().getTitle(),
                chapter.getTitle(),
                getTimeElapsed(chapter.getCreatedDate()),
                chapter.getStory().getCategory()
        )).collect(Collectors.toList());
    }

    public String getTimeElapsed(LocalDateTime createdDate) {
        if (createdDate == null) {
            return "Không có thông tin"; 
        }
        Duration duration = Duration.between(createdDate, LocalDateTime.now());
        
        if (duration.toMinutes() < 60) {
            return duration.toMinutes() + " phút trước";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " giờ trước";
        } else if (duration.toDays() < 30) {
            return duration.toDays() + " ngày trước";
        } else {
            return (duration.toDays() / 30) + " tháng trước";
        }
    }

    public ChapterDto getLatestChapterAndTime(Long storyId) {
        Chapter latestChapter = chapterRepository.findTopByStoryIdOrderByCreatedDateDesc(storyId);
        
        if (latestChapter != null) {
            String elapsedTime = getTimeElapsed(latestChapter.getCreatedDate());
            return new ChapterDto(
                latestChapter.getId(),
                latestChapter.getStory().getTitle(),
                latestChapter.getTitle(),
                elapsedTime,
                latestChapter.getStory().getCategory()
            );
        } else {
            return null;
        }
    }
    
}
