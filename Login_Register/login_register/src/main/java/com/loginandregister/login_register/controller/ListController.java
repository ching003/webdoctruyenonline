package com.loginandregister.login_register.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.service.StoryService;

@Controller
@RequestMapping("/list")
public class ListController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/new")
    public String getNewStories(Model model) {
        List<Story> stories = storyService.findByOrderByCreatedDateDesc();
        storyService.setLatestChapterForStories(stories); 
        model.addAttribute("stories", stories);
        model.addAttribute("listname", "Truyện mới");
        return "danhsach";
    }

    @GetMapping("/hot")
    public String getHotStories(Model model) {
        List<Story> stories = storyService.findHotStories();
        storyService.setLatestChapterForStories(stories); 
        model.addAttribute("stories", stories);
        model.addAttribute("listname", "Truyện hot");
        return "danhsach";
    }

    @GetMapping("/full")
    public String getCompletedStories(Model model) {
        List<Story> stories = storyService.findRecentlyCompletedStories();
        storyService.setLatestChapterForStories(stories); 
        model.addAttribute("stories", stories);
        model.addAttribute("listname", "Truyện full");
        return "danhsach";
    }

    @GetMapping("/romance")
    public String getRomanceStories(Model model) {
        List<Story> stories = storyService.findTopByCategory("Ngôn tình");
        storyService.setLatestChapterForStories(stories);
        model.addAttribute("stories", stories);
        model.addAttribute("listname", "Ngôn tình hay");
        return "danhsach";
    }

    @GetMapping("/time-travel")
    public String getTimeTravelStories(Model model) {
        List<Story> stories = storyService.findTopByCategory("Xuyên không");
        storyService.setLatestChapterForStories(stories);
        model.addAttribute("stories", stories);
        model.addAttribute("listname", "Xuyên không hay");
        return "danhsach";
    }

    @GetMapping("/most-commented")
    public String getMostCommentedStories(Model model) {
        List<Story> stories = new ArrayList<>();
        storyService.setLatestChapterForStories(stories); 
        model.addAttribute("stories", stories);
        model.addAttribute("listname", "Bình luận nhiều nhất");
        return "danhsach";
    }

    @GetMapping("/filter")
    public String filterStories(@RequestParam(value = "status", required = false) List<String> status,
                                @RequestParam(value = "chapters", required = false) List<String> chapters,
                                @RequestParam(value = "sort", required = false) String sort,
                                @RequestParam(value = "categories", required = false) List<String> categories,
                                Model model) {

        List<Story> filteredStories = storyService.filterStories(status, chapters, sort, categories);
        if(filteredStories.isEmpty()) model.addAttribute("message", "Không có truyện thỏa mãn yêu cầu!");
        model.addAttribute("stories", filteredStories);
        model.addAttribute("listname", "Danh sách truyện đã lọc");
        return "danhsach";
    }
}
