package com.loginandregister.login_register.service;

import com.loginandregister.login_register.dto.UserDto;
import com.loginandregister.login_register.model.User;

public interface UserService {
    User save(UserDto userDto);
}
