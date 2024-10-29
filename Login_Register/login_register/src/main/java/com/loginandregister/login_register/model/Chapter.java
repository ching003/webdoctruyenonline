package com.loginandregister.login_register.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chapters") 
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title, content;
    @Column(columnDefinition = "LONGTEXT")
    private String longContent;

    @ManyToOne
    @JoinColumn(name = "story_id") 
    private Story story;

    public Chapter(){
        super();
    }

    public Chapter(String title, String content) {
        this.title = title;
        this.content = content;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Story getStory() {
        return story;
    }
    public void setStory(Story story) {
        this.story = story;
    }
    public String getLongContent() {
        return longContent;
    }
    public void setLongContent(String longContent) {
        this.longContent = longContent;
    }
}
