package com.loginandregister.login_register.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long storyId;
    private Long chapterId; 
    private String message; 

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Notification() {
        this.createdAt = new Date();
    }

    public Notification(Long userId, Long storyId) {
        this.userId = userId;
        this.storyId = storyId;
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getStoryId() {
        return storyId;
    }
    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Long getChapterId() {
        return chapterId;
    }
    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
