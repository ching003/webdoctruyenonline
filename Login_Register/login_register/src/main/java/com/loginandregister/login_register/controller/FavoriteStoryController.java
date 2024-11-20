package com.loginandregister.login_register.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.FavoriteService;
import com.loginandregister.login_register.service.StoryService;


@Controller
public class FavoriteStoryController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private StoryService storyService;
    
    @GetMapping("/user-page/favorite")
    public String getFavoriteStories(Model model, Authentication authentication) {
        if(authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        List<Long> favoriteStoryIds = favoriteService.getFavoriteStoryIds(userDetails.getId());
        List<Story> favoriteStories = favoriteStoryIds.stream()
            .map(storyId -> storyService.findById(storyId)) 
            .collect(Collectors.toList());
        model.addAttribute("favoriteStories", favoriteStories);
        return "favorite-story";
    }
    
}
