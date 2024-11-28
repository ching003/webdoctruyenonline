package com.loginandregister.login_register.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.loginandregister.login_register.dto.ChapterDto;
import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.service.ChapterService;
import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.StoryService;


@Controller
public class HomeController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private ChapterService chapterService;

    @ModelAttribute("fullname")
    public String addUserFullname() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && 
            authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getPrincipal().toString())) {
                
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetail) {
                CustomUserDetail userDetails = (CustomUserDetail) principal;
                return userDetails.getFullname();
            }
        }
        return null;
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; 
    }
    @GetMapping("/home")
    public String home(Model model) {
        
        model.addAttribute("hotStories", storyService.findHotStories());
        List<Story> completedStories = storyService.findRecentlyCompletedStories();
        completedStories.sort(Comparator.comparing(Story::getCreatedDate).reversed());
        model.addAttribute("completedStories", completedStories.stream().limit(12).collect(Collectors.toList()));

        List<ChapterDto> recentChapters = chapterService.getRecentChaptersWithElapsedTime();
        model.addAttribute("recentChapters", recentChapters);
        return "we";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication) {
        return "redirect:/home"; 
    }
}
