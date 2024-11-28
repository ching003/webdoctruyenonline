package com.loginandregister.login_register.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.service.StoryService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class CategoryController {
    @Autowired 
    private StoryService storyService;

    @GetMapping("/category/{categoryName}")
    public String getStoriesByCategory(Model model, @PathVariable String categoryName, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
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
        int pageSize = 10;  
        int totalStories = stories.size();  
        int totalPages = (int) Math.ceil((double) totalStories / pageSize);  
        int start = page * pageSize;  
        int end = Math.min(start + pageSize, totalStories); 

        List<Story> subStory = stories.subList(start, end);  

        model.addAttribute("stories", subStory);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        String basePath = request.getRequestURL().toString();
        String query = request.getQueryString();
        if (query != null) {
            query = query.replaceAll("(&page=\\d+)", "");
            basePath += '?' + query + "&page=" + page;
        }        
        model.addAttribute("basePath", basePath);
        //model.addAttribute("stories", stories);
        model.addAttribute("categoryName", categoryName);
        return "category";
    }
}
