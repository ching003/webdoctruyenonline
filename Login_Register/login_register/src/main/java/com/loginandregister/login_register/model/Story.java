package com.loginandregister.login_register.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, author, category, description, coverImage, status; // "completed" hoặc "in-progress"
    private LocalDateTime createdDate, completedDate;
    private int views;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Story(){
        super();
    }

    public Story(String title, String author, String category){
        this.title = title;
        this.author = author;
        this.category = category;
        this.createdDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCoverImage() {
        return coverImage;
    }
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }  
    public String getCreatedDate(){
        return createdDate.toString();
    }
    public void setCreatedDate(LocalDateTime localDate){
        this.createdDate = localDate;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getViews() {
        return views;
    }
    public void setViews(int views) {
        this.views = views;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCompletedDate() {
        return completedDate;
    }
    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }
    public List<Chapter> getChapters() {
        return chapters;
    }
    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
