package com.reactjs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO : Need to review this class usage...
// @Component
public class AuthenticationLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationLoggingFilter.class);

    // older API implementation...
   // @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            logger.info("Authenticated request - Username: {}", authentication.getName());
        } else {
            logger.warn("Unauthenticated request - IP: {}", request.getRemoteAddr());
        }

        filterChain.doFilter(request, response);
    }

    // Latest API implementation...
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        logger.info("doFilterInternal@AuthenticationLoggingFilter Override():: ");
        System.out.println("doFilterInternal@AuthenticationLoggingFilter Override():: ");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            logger.info("Authenticated request - Username: {}", authentication.getName());
            System.out.println("Authenticated request - Username: {}" + authentication.getName());
        } else {
            logger.warn("Unauthenticated request - IP: {}", request.getRemoteAddr());
        }
        filterChain.doFilter(request, response);
    }
}