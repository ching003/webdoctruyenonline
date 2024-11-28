package com.loginandregister.login_register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.service.StoryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/list")
public class ListController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/new")
    public String getNewStories(Model model, @RequestParam(defaultValue = "0") int page) {
        List<Story> stories = storyService.findByOrderByCreatedDateDesc();
        storyService.setLatestChapterForStories(stories); 
        //model.addAttribute("stories", stories);
        model.addAttribute("listname", "Truyện mới");
        model.addAttribute("basePath", "/list/new");
        addPagination(model, stories, page);
        return "danhsach";
    }

    @GetMapping("/hot")
    public String getHotStories(Model model, @RequestParam(defaultValue = "0") int page) {
        List<Story> stories = storyService.findHotStories();
        storyService.setLatestChapterForStories(stories); 
        //model.addAttribute("stories", stories);
        addPagination(model, stories, page);
        model.addAttribute("listname", "Truyện hot");
        model.addAttribute("basePath", "/list/hot");
        return "danhsach";
    }

    @GetMapping("/full")
    public String getCompletedStories(Model model, @RequestParam(defaultValue = "0") int page) {
        List<Story> stories = storyService.findRecentlyCompletedStories();
        storyService.setLatestChapterForStories(stories); 
        //model.addAttribute("stories", stories);
        addPagination(model, stories, page);
        model.addAttribute("listname", "Truyện full");
        model.addAttribute("basePath", "/list/full");
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
    public String getMostCommentedStories(Model model, @RequestParam(defaultValue = "0") int page) {
        List<Story> stories = storyService.getMostCommentedStories();
        storyService.setLatestChapterForStories(stories); 
        //model.addAttribute("stories", stories);
        addPagination(model, stories, page);
        model.addAttribute("listname", "Bình luận nhiều nhất");
        model.addAttribute("basePath", "/list/most-commented");
        return "danhsach";
    }

    @GetMapping("/filter")
    public String filterStories(@RequestParam(value = "status", required = false) List<String> status,
                                @RequestParam(value = "chapters", required = false) List<String> chapters,
                                @RequestParam(value = "sort", required = false) String sort,
                                @RequestParam(value = "categories", required = false) List<String> categories,
                                Model model,
                                HttpServletRequest request,
                                @RequestParam(defaultValue = "0") int page) {

        List<Story> filteredStories = storyService.filterStories(status, chapters, sort, categories);
        if(filteredStories.isEmpty()) model.addAttribute("message", "Không có truyện thỏa mãn yêu cầu!");
        //model.addAttribute("stories", filteredStories);
        addPagination(model, filteredStories, page);
        String basePath = request.getRequestURL().toString();
        String query = request.getQueryString();
        System.out.println(basePath);
        System.out.println(query);
        if (query != null) {
            query = query.replaceAll("(&page=\\d+)", "");
            basePath += '?' + query + "&page=" + page;
        }        
        model.addAttribute("basePath", basePath);
        model.addAttribute("listname", "Danh sách truyện đã lọc");
        return "danhsach2";
    }

    private void addPagination(Model model, List<Story> stories, int page) {
        int pageSize = 10;  
        int totalStories = stories.size();  
        int totalPages = (int) Math.ceil((double) totalStories / pageSize);  
        int start = page * pageSize;  
        int end = Math.min(start + pageSize, totalStories); 

        List<Story> subStory = stories.subList(start, end);  

        model.addAttribute("stories", subStory);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }
}
