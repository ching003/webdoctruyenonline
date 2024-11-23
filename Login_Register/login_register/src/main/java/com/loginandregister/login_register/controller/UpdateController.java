package com.loginandregister.login_register.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginandregister.login_register.service.StoryService;

@RestController
@RequestMapping("/api/stories")
public class UpdateController {
    private final StoryService storyService;

    public UpdateController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/update-tags")
    public String updateTags() {
        storyService.updateAllTags();
        return "All story tags updated successfully!";
    }
}
