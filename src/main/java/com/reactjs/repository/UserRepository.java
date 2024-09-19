package com.reactjs.repository;

import com.reactjs.dto.UserDto;
import com.reactjs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserId(String userId);

    User save(UserDto userDto);
}