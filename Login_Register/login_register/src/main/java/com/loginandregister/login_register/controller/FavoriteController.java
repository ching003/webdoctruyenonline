package com.loginandregister.login_register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.FavoriteService;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    // API để thêm hoặc xóa truyện yêu thích
    @PostMapping("/{storyId}")
    public ResponseEntity<Boolean> toggleFavorite(@PathVariable Long storyId, Authentication authentication) {
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);  
        }
    
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        boolean isFavorite = favoriteService.toggleFavorite(userDetails.getId(), storyId);
    
        return ResponseEntity.ok(isFavorite);
    }

    // API để lấy danh sách truyện yêu thích của user
    @GetMapping("/list")
    public ResponseEntity<List<Long>> getUserFavorites(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        List<Long> favoriteStoryIds = favoriteService.getFavoriteStoryIds(userDetails.getId());

        return ResponseEntity.ok(favoriteStoryIds);
    }
}
