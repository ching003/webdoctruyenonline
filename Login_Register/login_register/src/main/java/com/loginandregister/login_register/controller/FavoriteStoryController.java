package com.loginandregister.login_register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.FavoriteService;


@Controller
public class FavoriteStoryController {
    @Autowired
    private FavoriteService favoriteService;
    
    @GetMapping("/user-page/favorite")
    public String getFavoriteStories(Model model, Authentication authentication) {
        if(authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        List<Long> favoriteStoryIds = favoriteService.getFavoriteStoryIds(userDetails.getId());
        model.addAttribute("favoriteStoryIds", favoriteStoryIds);
        return "favorite-story";
    }
    
}
