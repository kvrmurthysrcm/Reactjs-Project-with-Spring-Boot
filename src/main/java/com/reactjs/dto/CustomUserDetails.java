package com.reactjs.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Integer idusers;
    private String userId;

    private String password;

    private String firstName;
    private String lastName;

    private Boolean active;
    private String userscol;

    private Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(String userId, String password, Collection<? extends GrantedAuthority> authorities,
                             String firstName) {
        this.userId = userId;
        this.password = password;
        this.authorities = authorities;
        this.firstName = firstName;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
