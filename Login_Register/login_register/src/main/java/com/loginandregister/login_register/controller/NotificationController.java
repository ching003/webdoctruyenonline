package com.loginandregister.login_register.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginandregister.login_register.model.Notification;
import com.loginandregister.login_register.service.ChapterService;
import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ChapterService chapterService;

    @PostMapping("/{storyId}")
    public ResponseEntity<Boolean> toggleNotification(@PathVariable Long storyId, Authentication authentication) {
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        boolean isSubscribed = notificationService.toggleNotification(userDetails.getId(), storyId);

        return ResponseEntity.ok(isSubscribed);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Map<String, String>>> getUserNotifications(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        List<Notification> notifications = notificationService.getRecentNotifications(userDetails.getId());
        
        List<Map<String, String>> notificationDetails = notifications.stream().map(notification -> {
            Map<String, String> map = new HashMap<>();
            map.put("message", notification.getMessage());
            map.put("chapterId", String.valueOf(notification.getChapterId()));
            map.put("elapsedTime", chapterService.getTimeElapsed(notification.getCreatedAt()));
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(notificationDetails);
    }
}
