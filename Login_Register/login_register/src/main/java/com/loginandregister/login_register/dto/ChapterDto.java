package com.loginandregister.login_register.dto;

public class ChapterDto {
    private Long id;
    private String title;
    private String category;
    private String latestChapter;
    private String elapsedTime;

    public ChapterDto(Long id, String title, String latestChapter, String elapsedTime, String category) {
        this.id = id;
        this.title = title;
        this.latestChapter = latestChapter;
        this.elapsedTime = elapsedTime;
        this.category = category;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getLatestChapter(){
        return latestChapter;
    }
    public void setLatestChapter(String latestChapter){
        this.latestChapter = latestChapter;
    }
    public String getElapsedTime(){
        return elapsedTime;
    }
    public void setElapsedTime(String elapsedTime){
        this.elapsedTime = elapsedTime;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }

}
