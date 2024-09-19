package com.reactjs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class APISecurityConfig {
//
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        JdbcUserDetailsManager jUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jUserDetailsManager.setUsersByUsernameQuery("SELECT userId, password, active FROM Users WHERE userId = ?;");
//
//        jUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT userId, role FROM Roles WHERE userId = ?;");
//
//        return jUserDetailsManager;
//    }

}