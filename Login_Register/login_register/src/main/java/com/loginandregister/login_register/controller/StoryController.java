package com.loginandregister.login_register.controller;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.service.StoryService;


@Controller
public class StoryController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/admin/story/new")
    public String showAddStoryForm(Model model) {
        model.addAttribute("story", new Story());
        return "add-story";
    }
    
    @PostMapping("/stories/add")
    public String saveStory(@RequestParam("title") String title,
                            @RequestParam("author") String author,
                            @RequestParam("category") String category,
                            @RequestParam("description") String description,
                            @RequestParam("coverImage") MultipartFile coverImage, 
                            Model model) {        
        if (coverImage.isEmpty()) {
            model.addAttribute("message", "Chưa chọn ảnh để tải lên!");
            model.addAttribute("story", new Story());
            return "add-story";
        }

        String fileName = coverImage.getOriginalFilename();
        //String upDir = "uploads/";
        String upDir = Paths.get("uploads").toAbsolutePath().toString();
        Path upPath = Paths.get(upDir);
        File dir = new File(upDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try{  
            coverImage.transferTo(new File(dir, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Có lỗi khi upload ảnh!");
            model.addAttribute("story", new Story());
            return "add-story";
        }
        Story story = new Story();
        story.setTitle(title);
        story.setAuthor(author);
        story.setCategory(category);
        story.setDescription(description);
        story.setCoverImage(upDir + '/' + fileName);
        storyService.save(story);
        return "redirect:/admin-page";
    }

    @GetMapping("/admin/stories")
    public String viewStories(Model model) {
        model.addAttribute("stories", storyService.findAll());
        return "story-list";
    }
    
}
