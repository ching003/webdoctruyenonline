package com.loginandregister.login_register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.loginandregister.login_register.dto.UserDto;
import com.loginandregister.login_register.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto, Model model){
        //model.addAttribute("user", new UserDto());
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
}
