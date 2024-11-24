package com.loginandregister.login_register.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.dto.CommentDto;
import com.loginandregister.login_register.model.Comment;
import com.loginandregister.login_register.repositories.CommentRepository;
import com.loginandregister.login_register.repositories.UserRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private StoryService storyService;
    
    public CommentDto saveComment(CommentDto commentDto, Long userId) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setStoryId(commentDto.getStoryId());
        comment.setContent(commentDto.getContent());
        comment.setCreatedDate(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        String elapsedTime = chapterService.getTimeElapsed(savedComment.getCreatedDate());
        String username = userRepository.findById(userId)
                                        .map(user -> user.getFullname())
                                        .orElse("Ẩn danh");
        return new CommentDto(savedComment.getId(), userId, username, savedComment.getStoryId(), savedComment.getContent(), savedComment.getCreatedDate(), elapsedTime);
    }

    public List<CommentDto> getCommentsByStoryId(Long storyId) {
        List<Comment> comments = commentRepository.findByStoryId(storyId);

        return comments.stream().map(comment -> {
            String elapsedTime = chapterService.getTimeElapsed(comment.getCreatedDate());
            String userName = userRepository.findById(comment.getUserId())
                                            .map(user -> user.getFullname())
                                            .orElse("Ẩn danh");
            return new CommentDto(
                comment.getId(),
                comment.getUserId(),
                userName,
                comment.getStoryId(),
                comment.getContent(),
                comment.getCreatedDate(),
                elapsedTime
            );
        }).collect(Collectors.toList());
    }

    public List<CommentDto> getUserComments(Long userId) {
        List<Comment> comments = commentRepository.findByUserIdOrderByCreatedDateDesc(userId);
        return comments.stream()
                .map(comment -> new CommentDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getStoryId(),
                        storyService.getStoryTitleById(comment.getStoryId()),
                        comment.getCreatedDate(),
                        chapterService.getTimeElapsed(comment.getCreatedDate())
                ))
                .collect(Collectors.toList());
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        
        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this comment.");
        }
        commentRepository.delete(comment);
    }
}
