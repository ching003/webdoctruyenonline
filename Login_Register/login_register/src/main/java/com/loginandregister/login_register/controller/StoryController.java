package com.loginandregister.login_register.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.loginandregister.login_register.model.Chapter;
import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.model.User;
import com.loginandregister.login_register.repositories.StoryRepository;
import com.loginandregister.login_register.repositories.UserRepository;
import com.loginandregister.login_register.service.ChapterService;
import com.loginandregister.login_register.service.StoryService;


@Controller
public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChapterService chapterService;
    private final String UPLOAD_DIR = "src/main/resources/static/image/";

    @GetMapping("/admin/new-story")
    public String showAddStoryForm(Model model) {
        model.addAttribute("story", new Story());
        return "add-story";
    }

    @PostMapping("/admin/new-story")
    public String saveStory(@RequestParam("title") String title,
                            @RequestParam("author") String author,
                            @RequestParam("category") String category,
                            @RequestParam("description") String description,
                            @RequestParam("coverImage") MultipartFile coverImage, 
                            Model model, Principal principal) {        
        if (coverImage.isEmpty()) {
            model.addAttribute("message", "Chưa chọn ảnh để tải lên!");
            model.addAttribute("story", new Story());
            return "add-story";
        }
        
        String fileName = coverImage.getOriginalFilename();
        //String upDir = "uploads/";
        String upDir = Paths.get("Login_Register/login_register/src/main/resources/static/image").toAbsolutePath().toString();
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
        story.setCreatedDate(LocalDateTime.now());
        story.setCoverImage("/image/" + fileName);
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            story.setUser(user);
        }
        storyService.save(story);
        return "redirect:/admin-page";
    }
       

    @GetMapping("/admin/stories")
    public String viewStories(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user!= null) {
            model.addAttribute("stories", storyService.getStoriesByUser(user));
        } else {
            model.addAttribute("stories", List.of());
        }
        return "story-list";
    }

    @GetMapping("/story-info/{id}")
    public String getStoryInfo(@PathVariable Long id, Model model) {
        Story story = storyService.findById(id); 
        model.addAttribute("story", story); 
        return "story-info"; 
    }

    @PostMapping("/addChapter")
    public ModelAndView addChapter(@RequestParam Long storyId, @RequestParam String title, @RequestParam String content, @RequestParam String longContent) {
        Story story = storyService.findById(storyId); 
        if (story != null) {
            Chapter chapter = new Chapter();
            chapter.setTitle(title);
            chapter.setContent(content);
            chapter.setLongContent(longContent);
            chapter.setStory(story); 
            chapterService.save(chapter);
        }
        
        ModelAndView modelAndView = new ModelAndView("redirect:/story-info/" + storyId);
        return modelAndView;
    }

    @GetMapping("/chapter/{id}")
    public String viewChapter(@PathVariable Long id, Model model) {
        Chapter chapter = chapterService.getChapterById(id);
        String formattedContent = chapter.getLongContent().replace("\n", "<br>");
        model.addAttribute("formattedContent", formattedContent);
        Story story = chapter.getStory();
        model.addAttribute("storyId", story.getId());
        model.addAttribute("storyTitle", story.getTitle());
        model.addAttribute("chapter", chapter);
        return "chapter-details";
    }
}
