package com.loginandregister.login_register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loginandregister.login_register.model.Favorite;
import com.loginandregister.login_register.repositories.FavoriteRepository;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Transactional
    public boolean toggleFavorite(Long userId, Long storyId) {
        if (favoriteRepository.existsByUserIdAndStoryId(userId, storyId)) {
            favoriteRepository.deleteByUserIdAndStoryId(userId, storyId); 
            return false; 
        } else {
            favoriteRepository.save(new Favorite(userId, storyId)); 
            return true; 
        }
    }

    public List<Long> getFavoriteStoryIds(Long userId) {
        return favoriteRepository.findStoryIdsByUserId(userId);
    }
}
