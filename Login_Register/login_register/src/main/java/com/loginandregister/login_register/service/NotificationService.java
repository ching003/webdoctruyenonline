package com.loginandregister.login_register.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.model.Notification;
import com.loginandregister.login_register.repositories.NotificationRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }
    public boolean toggleNotification(Long userId, Long storyId) {
        Notification existing = notificationRepository.findByUserIdAndStoryId(userId, storyId);
        if (existing != null) {
            notificationRepository.delete(existing);
            return false;
        } else {
            notificationRepository.save(new Notification(userId, storyId));
            return true; 
        }
    }

    public List<Notification> getRecentNotifications(Long userId) {
        return notificationRepository.findRecentByUserId(userId, PageRequest.of(0, 5));
    }

    public List<Long> getNotificationStoryIds(Long userId) {
        return notificationRepository.findStoryIdsByUserId(userId);
    }

    public List<Long> getUsersFollowingStory(Long storyId) {
        return notificationRepository.findUserIdsByStoryId(storyId);
    }

    public Notification saveOrUpdate(Notification notification) {
        Notification existingNotification = notificationRepository.findByStoryIdAndUserId(
            notification.getStoryId(),
            notification.getUserId()
        );

        if (existingNotification != null) {
            existingNotification.setChapterId(notification.getChapterId());
            existingNotification.setMessage(notification.getMessage());
            existingNotification.setCreatedAt(LocalDateTime.now()); 
            return notificationRepository.save(existingNotification);
        } else {
            return notificationRepository.save(notification);
        }
    }
}