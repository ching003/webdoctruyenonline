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
public class AuthorController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/author/{authorName}")
    public String getStoriesByAuthor(@PathVariable String authorName, Model model) {
        List<Story> storiesByAuthor = storyService.findStoriesByAuthor(authorName);
        model.addAttribute("authorName", authorName);
        model.addAttribute("stories", storiesByAuthor);
        return "author"; 
    }
}
