package com.loginandregister.login_register.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.loginandregister.login_register.dto.UserDto;
import com.loginandregister.login_register.service.CustomUserDetail;
import com.loginandregister.login_register.service.UserService;




@Controller
public class UserController {
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto, Model model){
        return "register";
    }
    
    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfuly!");
        return "register";
    }
    
    @GetMapping("/login")
	public String login() {
		return "login";
	}

    @GetMapping("user-page")
	public String userPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		return "user";
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
}
