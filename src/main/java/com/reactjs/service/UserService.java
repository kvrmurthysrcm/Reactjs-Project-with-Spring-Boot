package com.reactjs.service;

import com.reactjs.dto.UserDto;
import com.reactjs.entity.User;

public interface UserService {
    User findByUserId(String username);

    User save(UserDto userDto);

}
