package com.loginandregister.login_register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.dto.UserDto;
import com.loginandregister.login_register.model.User;
import com.loginandregister.login_register.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getPassword(), userDto.getRole(), userDto.getFullname());
        return userRepository.save(user);
    }
}
