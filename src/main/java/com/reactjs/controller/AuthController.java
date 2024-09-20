package com.reactjs.controller;


import com.reactjs.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    @Value("${restapi.host}")
    private String restapiHost;

    @GetMapping("/token")
    public String token(Authentication authentication){
        if(authentication != null){
            LOG.debug("Token requested for user '{}", authentication.getName() );
            System.out.println("Token requested for user '{}"+ authentication.getName() );
        } else{
            System.out.println("Token requested for user ...");
        }

        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted '{}", token);
        System.out.println("Token granted '{}"+ token);
        return token;
    }

    @GetMapping("/restapihost")
    public String restapiHost(){
        System.out.println("restapiHost: " + restapiHost );
        return restapiHost;
    }

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}