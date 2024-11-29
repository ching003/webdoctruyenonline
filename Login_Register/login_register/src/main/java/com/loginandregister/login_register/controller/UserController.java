package com.loginandregister.login_register.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.loginandregister.login_register.dto.UserDto;
import com.loginandregister.login_register.model.Story;
import com.loginandregister.login_register.model.User;
import com.loginandregister.login_register.repositories.UserRepository;
import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.FavoriteService;
import com.loginandregister.login_register.service.StoryService;
import com.loginandregister.login_register.service.UserService;




@Controller
public class UserController {
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private StoryService storyService;


    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto, Model model){
        return "register";
    }
    
    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userDto.setRole("USER");
        userService.save(userDto);
        model.addAttribute("message", "Đăng ký thành công!");
        return "register";
    }
    
    @GetMapping("/login")
	public String login() {
		return "login";
	}	
    
	@GetMapping("admin-page")
	public String adminPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
        
        CustomUserDetail customUserDetail = (CustomUserDetail) userDetailsService.loadUserByUsername(principal.getName());
        String obfuscatedEmail = obfuscateEmail(customUserDetail.getUsername());
        String obfuscatedPassword = obfuscatePassword(customUserDetail.getPassword());
        model.addAttribute("fullName", customUserDetail.getFullname());
        model.addAttribute("obfuscatedEmail", obfuscatedEmail);
        model.addAttribute("obfuscatedPassword", obfuscatedPassword);

		return "admin";
	}

    @GetMapping("user-page")
	public String userPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
        
        CustomUserDetail customUserDetail = (CustomUserDetail) userDetailsService.loadUserByUsername(principal.getName());
        String obfuscatedEmail = obfuscateEmail(customUserDetail.getUsername());
        String obfuscatedPassword = obfuscatePassword(customUserDetail.getPassword());
        model.addAttribute("fullName", customUserDetail.getFullname());
        model.addAttribute("obfuscatedEmail", obfuscatedEmail);
        model.addAttribute("obfuscatedPassword", obfuscatedPassword);

		return "user";
	}
    private String obfuscateEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex > 1) {
            return email.charAt(0) + "****" + email.substring(atIndex - 1);
        } else {
            return "****";
        }
    }
    
    private String obfuscatePassword(String password) {
        return "****";
    }

    @GetMapping("/user/{userId}")
    public String getUserProfile(@PathVariable Long userId, Model model) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<Long> favoriteStoryIds = favoriteService.getFavoriteStoryIds(userId);
        List<Story> favoriteStories = favoriteStoryIds.stream()
            .map(storyId -> storyService.findById(storyId))
            .collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("favoriteStories", favoriteStories);
        return "profile";
    }
}
