package com.reactjs.service;

import com.reactjs.dto.UserDto;
import com.reactjs.entity.User;
import com.reactjs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserId(String username) {
        return userRepository.findByUserId(username);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getUserId(), passwordEncoder.encode(userDto.getPassword()),
                userDto.getFirstName());
        return userRepository.save(user);
    }

}