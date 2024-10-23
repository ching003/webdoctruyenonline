package com.loginandregister.login_register.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.loginandregister.login_register.service.CustomUserDetail;


@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; 
    }
    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && 
            authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getPrincipal().toString())) {
                
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetail) {
                CustomUserDetail userDetails = (CustomUserDetail) principal;
                model.addAttribute("fullname", userDetails.getFullname());
            }
        }
        return "we";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication) {
        return "redirect:/home"; 
    }
}
