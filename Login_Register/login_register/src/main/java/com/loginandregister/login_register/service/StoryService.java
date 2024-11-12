package com.loginandregister.login_register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.dto.StoryTitleProjection;
import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.model.User;
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

    //bonus
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
}
