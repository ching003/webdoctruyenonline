package com.loginandregister.login_register.controller;

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
        model.addAttribute("stories", stories);
        model.addAttribute("categoryName", categoryName);
        return "category";
    }
}
