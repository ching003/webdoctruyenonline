package com.loginandregister.login_register.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.dto.StoryTitleProjection;
import com.loginandregister.login_register.model.Chapter;
import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.model.User;
import com.loginandregister.login_register.repositories.ChapterRepository;
import com.loginandregister.login_register.repositories.StoryRepository;
import com.loginandregister.login_register.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChapterRepository chapterRepository;
    
    public List<Story> getStoriesByUser(User user) {
        return storyRepository.findByUser(user);
    }
    /*bonus
    public long getStoryCount(User user) {
        return storyRepository.countByUser(user);
    }
    
    public Story addStory(Story story, User user) {
        story.setUser(user);
        story.setCreatedDate(LocalDateTime.now());
        return storyRepository.save(story);
    }
    
    public void deleteStory(Long id, User user) {
        Story story = storyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Story not found"));
        if (!story.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }
        storyRepository.delete(story);
    }
    end bonus*/

    public void addStory(Story story) {
        storyRepository.save(story);
    }
    
    public void save(Story story){
        storyRepository.save(story);
    }

    public List<Story> findAll(){
        return storyRepository.findAll();
    }

    public List<Story> searchStories(String keyword){
        return storyRepository.findByTitleContaining(keyword);
    }

    public Story incrementViews(Long id) {
        Optional<Story> storyOpt = storyRepository.findById(id);
        if (storyOpt.isPresent()) {
            Story story = storyOpt.get();
            story.setViews(story.getViews() + 1); 
            return storyRepository.save(story); 
        }
        throw new RuntimeException("Story not found");
    }

    public List<Story> findHotStories() {
        return storyRepository.findByOrderByViewsDesc(PageRequest.of(0, 10));
    }

    public List<Story> findRecentlyCompletedStories() {
        return storyRepository.findByStatusOrderByCompletedDateDesc("completed", PageRequest.of(0, 10));
    }

    public Story findById(Long id) {
        return storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found"));
    }

    public List<StoryTitleProjection> findTitlesByKeyword(String keyword) {
        return storyRepository.findTitlesByKeyword("%" + keyword + "%");
    }

    public List<Story> getStoriesByCategory(String name){
        return storyRepository.findByCategoryContainingIgnoreCase(name);
    }

    public List<Story> findByOrderByCreatedDateDesc() {
        return storyRepository.findByOrderByCreatedDateDesc(PageRequest.of(0, 10));
    }

    public List<Story> findTopByCategory(String category) {
        return storyRepository.findByCategoryContainingIgnoreCaseOrderByViewsDesc(category, PageRequest.of(0, 10));
    }

    public void setLatestChapterForStories(List<Story> stories) {
        for (Story story : stories) {
            if (!story.getChapters().isEmpty()) {
                Chapter latestChapter = story.getChapters().get(story.getChapters().size() - 1);
                story.setLatestChapterTitle(latestChapter.getTitle());
            }
        }
    }

    public List<Story> filterStories(List<String> status, List<String> chapters, String sort, List<String> categories) {
        List<Story> stories = storyRepository.findAll(); // Bắt đầu với tất cả truyện

        if (categories != null && !categories.isEmpty()) {
            for (String category : categories) {
                List<Story> categoryStories = getStoriesByCategory(category);
                stories = stories.stream()
                    .filter(categoryStories::contains) // Chỉ giữ lại truyện có trong danh sách thể loại này
                    .collect(Collectors.toList());
            }
        }

        if (status != null && !status.isEmpty()) {
            stories = stories.stream()
                .filter(story -> status.contains(story.getStatus())) 
                .collect(Collectors.toList());
        }

        if (chapters != null && !chapters.isEmpty()) {
            stories = stories.stream()
                .filter(story -> {
                    int chapterCount = story.getChapters().size();
                    return chapters.stream().anyMatch(chapterRange -> isInRange(chapterCount, chapterRange));
                })
                .collect(Collectors.toList());
        }

        if ("old".equals(sort)) {
            stories.sort(Comparator.comparing(Story::getCreatedDate));
        } else if ("views".equals(sort)) {
            stories.sort(Comparator.comparing(Story::getViews).reversed());
        } else if ("new".equals(sort)) {
            stories.sort(Comparator.comparing(Story::getCreatedDate).reversed());
        }

        return stories;
    }

    private boolean isInRange(int chapterCount, String range) {
        switch (range) {
            case "<50": return chapterCount < 50;
            case "50-100": return chapterCount >= 50 && chapterCount <= 100;
            case "100-200": return chapterCount >= 100 && chapterCount <= 200;
            case "200-500": return chapterCount >= 200 && chapterCount <= 500;
            case "500-1000": return chapterCount >= 500 && chapterCount <= 1000;
            case ">1000": return chapterCount > 1000;
            default: return true;
        }
    }

    //tìm tác giả
    public List<Story> findStoriesByAuthor(String authorName) {
        return storyRepository.findByAuthor(authorName);
    }
    
    
    public String getStoryTags(Story story) {
        List<String> tags = new ArrayList<>();

        if (isNew(story.getCreatedDate())) {
            tags.add("New");
        }
        if (isHot(story)) {
            tags.add("Hot");
        }
        if ("completed".equalsIgnoreCase(story.getStatus())) {
            tags.add("Full");
        }
        if(!tags.isEmpty()) {
            story.setTagList(tags);
            return String.join(",", tags);
        }
        return "";
    }

    private boolean isNew(String createdDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"); 
        LocalDate createdDate = LocalDate.parse(createdDateStr, formatter);
        
        LocalDate currentDate = LocalDate.now();
        return !createdDate.isBefore(currentDate.minusWeeks(1));
    }

    private boolean isHot(Story story) {
        return story.getViews() > 100; 
    }

    public void saveTagsToDatabase(Story story) {
        String tags = getStoryTags(story);
        story.setTags(tags);  
        storyRepository.save(story);  
    }

    public void updateAllTags() {
        List<Story> stories = storyRepository.findAll();
        for (Story story : stories) {
            saveTagsToDatabase(story);
        }
    }
}
