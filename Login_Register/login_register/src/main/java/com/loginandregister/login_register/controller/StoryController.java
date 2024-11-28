package com.loginandregister.login_register.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.loginandregister.login_register.dto.ChapterDto;
import com.loginandregister.login_register.dto.CommentDto;
import com.loginandregister.login_register.dto.StoryTitleProjection;
import com.loginandregister.login_register.model.Chapter;
import com.loginandregister.login_register.model.Notification;
import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.model.User;
import com.loginandregister.login_register.repositories.StoryRepository;
import com.loginandregister.login_register.repositories.UserRepository;
import com.loginandregister.login_register.service.ChapterService;
import com.loginandregister.login_register.service.CommentService;
import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.FavoriteService;
import com.loginandregister.login_register.service.NotificationService;
import com.loginandregister.login_register.service.StoryService;


@Controller
public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ChapterService chapterService;
    private final String UPLOAD_DIR = "src/main/resources/static/image/";

    @GetMapping("/admin/new-story")
    public String showAddStoryForm(Model model) {
        model.addAttribute("story", new Story());
        return "add-story";
    }

    @PostMapping("/admin/new-story")
    public String saveStory(@RequestParam("title") String title,
                            @RequestParam("author") String author,
                            @RequestParam("category") List<String> category,
                            @RequestParam("description") String description,
                            @RequestParam("coverImage") MultipartFile coverImage, 
                            Model model, Principal principal) {        
        if (coverImage.isEmpty()) {
            model.addAttribute("message", "Chưa chọn ảnh để tải lên!");
            model.addAttribute("story", new Story());
            return "add-story";
        }
        
        String fileName = coverImage.getOriginalFilename();
        //String upDir = "uploads/";
        String upDir = Paths.get("Login_Register/login_register/src/main/resources/static/image").toAbsolutePath().toString();
        File dir = new File(upDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try{  
            coverImage.transferTo(new File(dir, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Có lỗi khi upload ảnh!");
            model.addAttribute("story", new Story());
            return "add-story";
        }
        Story story = new Story();
        story.setTitle(title);
        story.setAuthor(author);
        String formattedCategory = String.join(", ", category);
        story.setCategory(formattedCategory);
        story.setDescription(description);
        story.setCreatedDate(LocalDateTime.now());
        story.setCoverImage("/image/" + fileName);
        story.setStatus("in-progress");
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            story.setUser(user);
        }
        storyService.save(story);
        return "add-story";
    }
       

    @GetMapping("/admin/stories")
    public String viewStories(Model model, Principal principal, @RequestParam(defaultValue = "0") int page) {
        User user = userRepository.findByEmail(principal.getName());
        if (user != null) {
            int pageSize = 4; 
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdDate").descending());
            Page<Story> storiesPage = storyService.getStoriesByUser(user, pageable);

            model.addAttribute("stories", storiesPage.getContent()); 
            model.addAttribute("currentPage", page); 
            model.addAttribute("totalPages", storiesPage.getTotalPages()); 
        } else {
            model.addAttribute("stories", List.of());
        }
        return "story-list";
    }


    @PostMapping("/updateStoryStatus/{id}")
    public ResponseEntity<String> updateStoryStatus(@PathVariable Long id, @RequestParam String status) {
        Story story = storyService.findById(id);
        if (story != null) {
            story.setStatus(status); 
            storyService.save(story); 
            return ResponseEntity.ok("Cập nhật trạng thái thành công");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Truyện không tìm thấy");
    }

    @PostMapping("/admin/stories/delete/{id}")
    public String deleteStory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            storyService.deleteStory(id);  
            redirectAttributes.addFlashAttribute("message", "Truyện đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa truyện!");
        }
        return "redirect:/admin/stories";  
    }

    @GetMapping("/story-info/{id}")
    public String getStoryInfo(@PathVariable Long id, Model model, Authentication authentication, @RequestParam(defaultValue = "0") int page) {
        Story story;
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
            story = storyRepository.findById(id).orElseThrow(() -> new RuntimeException("Story not found"));
        } else {
            story = storyService.incrementViews(id); 
        }
        String formattedDescription = story.getDescription().replace("\n", "<br>");
        model.addAttribute("formattedDescription", formattedDescription);
        model.addAttribute("story", story); 
        String categories = story.getCategory();
        List<String> categoryList = Arrays.asList(categories.split(",\\s*"));
        model.addAttribute("categoryList", categoryList);
        
        //ds comment
        List<CommentDto> comments = commentService.getCommentsByStoryId(id);
        for (CommentDto comment : comments) {
            if (comment.getUserId() == 1) {
                comment.setUserName(comment.getUserName() + " (Admin)");
            }
        }
        comments.sort((c1, c2) -> c2.getCreatedDate().compareTo(c1.getCreatedDate()));
        model.addAttribute("comments", comments);

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal().toString())) {
            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            List<Long> favoriteStoryIds = favoriteService.getFavoriteStoryIds(userDetails.getId());
            List<Long> notificationStoryIds = notificationService.getNotificationStoryIds(userDetails.getId());

            model.addAttribute("favoriteStoryIds", favoriteStoryIds);
            model.addAttribute("notificationStoryIds", notificationStoryIds);

            ChapterDto latestChapterDto = chapterService.getLatestChapterAndTime(id);
            List<Long> userIds = notificationService.getUsersFollowingStory(id);
            if (latestChapterDto != null) {
                String message = latestChapterDto.getTitle() + " cập nhật chương mới - " + latestChapterDto.getLatestChapter();
        
                for (Long userId : userIds) {
                    Notification notification = new Notification(
                        id,
                        userId,
                        latestChapterDto.getId(),
                        message
                    );
                    notificationService.saveOrUpdate(notification); 
                }
        
                model.addAttribute("message", message);
                String elapsedTime = latestChapterDto.getElapsedTime();  
                model.addAttribute("elapsedTime", elapsedTime); 
            }                      
        }

        int pageSize = 40;  // Số chương mỗi trang
        List<Chapter> allChapters = story.getChapters();  
        int totalChapters = allChapters.size();  
        int totalPages = (int) Math.ceil((double) totalChapters / pageSize);  
        int start = page * pageSize;  
        int end = Math.min(start + pageSize, totalChapters); 

        List<Chapter> chapters = allChapters.subList(start, end);  

        model.addAttribute("chapters", chapters);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "story-info"; 
    }

    @PostMapping("/addChapter")
    public ModelAndView addChapter(@RequestParam Long storyId, @RequestParam String title, @RequestParam String content, @RequestParam String longContent) {
        Story story = storyService.findById(storyId); 
        if (story != null) {
            Chapter chapter = new Chapter();
            chapter.setTitle(title);
            chapter.setContent(content);
            chapter.setLongContent(longContent);
            chapter.setStory(story); 
            chapterService.save(chapter);
        }
        
        ModelAndView modelAndView = new ModelAndView("redirect:/story-info/" + storyId);
        return modelAndView;
    }

    @GetMapping("/chapter/{id}")
    public String viewChapter(@PathVariable Long id, Model model) {
        Chapter chapter = chapterService.getChapterById(id);
        String formattedContent = chapter.getLongContent().replace("\n", "<br>");
        model.addAttribute("formattedContent", formattedContent);

        Story story = chapter.getStory();
        Long storyId = story.getId();
        
        model.addAttribute("storyId", storyId);
        model.addAttribute("storyTitle", story.getTitle());
        model.addAttribute("chapter", chapter);

        Chapter prevChapter = chapterService.findPrevChapter(storyId, id);
        Chapter nextChapter = chapterService.findNextChapter(storyId, id);
        List<Chapter> chapterList = chapterService.findChaptersByStoryId(storyId);
        model.addAttribute("prevChapterId", (prevChapter != null) ? prevChapter.getId() : null);
        model.addAttribute("nextChapterId", (nextChapter != null) ? nextChapter.getId() : null);
        model.addAttribute("chapterList", chapterList);

        return "chapter-details";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<StoryTitleProjection> searchStoriesByTitle(@RequestParam("query") String query) {
        return storyService.findTitlesByKeyword(query);
    }

    @PostMapping("/editStory")
    public String editStory(@RequestParam Long storyId,
                            @RequestParam String title,
                            @RequestParam String author,
                            @RequestParam String category,
                            @RequestParam String description,
                            @RequestParam(required = false) MultipartFile coverImage,
                            RedirectAttributes redirectAttributes) {
        try {
            Story story = storyService.findById(storyId);
            if (story != null) {
                story.setTitle(title);
                story.setAuthor(author);
                story.setCategory(category);
                story.setDescription(description);
                if (coverImage != null && !coverImage.isEmpty()) {
                    String fileName = coverImage.getOriginalFilename();
                    String uploadDir = Paths.get("Login_Register/login_register/src/main/resources/static/image").toAbsolutePath().toString();
                    File dir = new File(uploadDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    coverImage.transferTo(new File(dir, fileName));
                    story.setCoverImage("/image/" + fileName); 
                }

                storyService.save(story); 
                redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy truyện!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin!");
        }
        return "redirect:/story-info/" + storyId; 
    }

    @PostMapping("/editChapter")
    public String editChapter(@RequestParam Long chapterId,
                            @RequestParam String title,
                            @RequestParam(required = false) String content,
                            @RequestParam String longContent,
                            RedirectAttributes redirectAttributes) {
        try {
            Chapter chapter = chapterService.getChapterById(chapterId);
            if (chapter != null) {
                chapter.setTitle(title);
                chapter.setContent(content);
                chapter.setLongContent(longContent);

                chapterService.save(chapter); 
                redirectAttributes.addFlashAttribute("message", "Chỉnh sửa chương thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy chương!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi chỉnh sửa chương!");
        }
        return "redirect:/chapter/" + chapterId;
    }


}
