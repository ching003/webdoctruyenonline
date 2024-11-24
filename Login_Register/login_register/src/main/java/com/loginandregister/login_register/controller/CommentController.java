package com.loginandregister.login_register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginandregister.login_register.dto.CommentDto;
import com.loginandregister.login_register.service.CommentService;
import com.loginandregister.login_register.service.CustomUserDetail;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    
    @PostMapping
    public ResponseEntity<CommentDto> postComment(@RequestBody CommentDto commentDto, Authentication authentication) {
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        CommentDto savedComment = commentService.saveComment(commentDto, userDetails.getId());

        return ResponseEntity.ok(savedComment);
    }

    @GetMapping("/story-info/{storyId}")
    public ResponseEntity<List<CommentDto>> getCommentsByStory(@PathVariable Long storyId) {
        List<CommentDto> comments = commentService.getCommentsByStoryId(storyId);
        return ResponseEntity.ok(comments);
    }
}
