package com.loginandregister.login_register.dto;

import java.time.LocalDateTime;

public class CommentDto {
    private Long id;
    private Long userId;
    private String userName; 
    private Long storyId;
    private String content;
    private LocalDateTime createdDate;
    private String elapsedTime; 

    public CommentDto(Long id, Long userId, String userName, Long storyId, String content, LocalDateTime createdDate, String elapsedTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.storyId = storyId;
        this.content = content;
        this.createdDate = createdDate;
        this.elapsedTime = elapsedTime;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getStoryId() {
        return storyId;
    }
    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public String getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
