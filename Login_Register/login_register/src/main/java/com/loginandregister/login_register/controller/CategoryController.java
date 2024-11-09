package com.loginandregister.login_register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.loginandregister.login_register.model.Chapter;
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
            for(Story story : stories) {
                if(!story.getChapters().isEmpty()) {
                    Chapter latestChapter = story.getChapters().get(story.getChapters().size()-1);
                    story.setLatestChapterTitle(latestChapter.getTitle());
                }
            }
        }
        model.addAttribute("stories", stories);
        model.addAttribute("categoryName", categoryName);
        return "category";
    }
}
