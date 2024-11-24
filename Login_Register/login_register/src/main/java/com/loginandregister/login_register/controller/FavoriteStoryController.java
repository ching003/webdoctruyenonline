package com.loginandregister.login_register.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.loginandregister.login_register.dto.CommentDto;
import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.service.CommentService;
import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.FavoriteService;
import com.loginandregister.login_register.service.StoryService;


@Controller
public class FavoriteStoryController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private StoryService storyService;
    @Autowired
    private CommentService commentService;
    
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
    
    @GetMapping("/user-page/comment")
    public String getUserComments(Model model, Authentication authentication) {
        if(authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        List<CommentDto> userComments = commentService.getUserComments(userDetails.getId());

        model.addAttribute("comments", userComments);
        return "user-comments";
    }

}
