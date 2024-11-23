package com.loginandregister.login_register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loginandregister.login_register.service.StoryService;

@RestController
@RequestMapping("/api/stories")
public class UpdateController {
    private final StoryService storyService;

    @Autowired
    public UpdateController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/update-tags")
    @ResponseBody
    public String updateTags() {
        try {
            storyService.updateAllTags(); 
            return "Cập nhật tag thành công!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
