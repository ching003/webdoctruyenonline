package com.loginandregister.login_register.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getFavoriteStories(Model model, Authentication authentication, @RequestParam(defaultValue = "0") int page) {
        if(authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        List<Long> favoriteStoryIds = favoriteService.getFavoriteStoryIds(userDetails.getId());
        List<Story> favoriteStories = favoriteStoryIds.stream()
            .map(storyId -> storyService.findById(storyId)) 
            .sorted((s1, s2) -> s2.getCreatedDate().compareTo(s1.getCreatedDate()))
            .collect(Collectors.toList());
        
        int pageSize = 4; 
        int totalStories = favoriteStories.size();
        int totalPages = (int) Math.ceil((double) totalStories / pageSize);
        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalStories);
    
        List<Story> storiesOnPage = favoriteStories.subList(start, end); 
    
        model.addAttribute("favoriteStories", storiesOnPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        //model.addAttribute("favoriteStories", favoriteStories);
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
