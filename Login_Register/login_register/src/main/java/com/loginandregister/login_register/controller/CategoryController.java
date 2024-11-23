package com.loginandregister.login_register.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.service.StoryService;


@Controller
public class CategoryController {
    @Autowired 
    private StoryService storyService;

    @GetMapping("/category/{categoryName}")
    public String getStoriesByCategory(Model model, @PathVariable String categoryName) {
        List<Story> stories = storyService.getStoriesByCategory(categoryName);
        
        if(stories.isEmpty()) model.addAttribute("message", "Chưa có truyện thuộc thể loại này!");
        else{
            storyService.setLatestChapterForStories(stories);
        }
        for (Story story : stories) {
            String tags = story.getTags(); 
            if (tags != null && !tags.isEmpty()) {
                List<String> tagList = Arrays.asList(tags.split(","));
                story.setTagList(tagList); 
            }
        }

        model.addAttribute("stories", stories);
        model.addAttribute("categoryName", categoryName);
        return "category";
    }
}
