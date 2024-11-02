package com.loginandregister.login_register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.loginandregister.login_register.dto.ChapterDto;
import com.loginandregister.login_register.service.ChapterService;
import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.StoryService;


@Controller
public class HomeController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private ChapterService chapterService;
    
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; 
    }
    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && 
            authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getPrincipal().toString())) {
                
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetail) {
                CustomUserDetail userDetails = (CustomUserDetail) principal;
                model.addAttribute("fullname", userDetails.getFullname());
            }
        }
        model.addAttribute("hotStories", storyService.findHotStories());
        model.addAttribute("completedStories", storyService.findRecentlyCompletedStories());

        List<ChapterDto> recentChapters = chapterService.getRecentChaptersWithElapsedTime();
        model.addAttribute("recentChapters", recentChapters);
        return "we";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication) {
        return "redirect:/home"; 
    }
}
