package com.loginandregister.login_register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.repositories.StoryRepository;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    public void save(Story story){
        storyRepository.save(story);
    }

    public List<Story> findAll(){
        return storyRepository.findAll();
    }

    public List<Story> searchStories(String keyword){
        return storyRepository.findByTitleContaining(keyword);
    }
}
