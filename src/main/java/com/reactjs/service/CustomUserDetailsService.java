package com.reactjs.service;

import java.util.Arrays;
import java.util.Collection;

import com.reactjs.dto.CustomUserDetails;
import com.reactjs.entity.User;
import com.reactjs.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        super();
        System.out.println("CustomUserDetailsService constructor::");
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(user_id);
        if (user == null) {
            throw new UsernameNotFoundException("Username or Password not found");
        }
        return new CustomUserDetails(user.getUserId(), user.getPassword(), authorities(), user.getFirstName());
    }

    public Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

}