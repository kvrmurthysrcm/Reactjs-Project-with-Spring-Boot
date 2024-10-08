package com.reactjs.controller;

import com.reactjs.dto.UserDto;
import com.reactjs.entity.User;
import com.reactjs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
// uses @Controller only, not @RestController to handle Thymeleaf MVC.
@Controller
@PropertySource("classpath:servers.properties")
public class HomeController {

    @Autowired
    private UserDetailsService userDetailsService;

    private final UserService userService;

    @Value("${restapi.host}")
    private String restapiHost;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "home";
    }

    @GetMapping("/loginn")
    public String login(Model model, UserDto userDto) {
        System.out.println("login():: userDto= " + userDto);
        model.addAttribute("user", userDto);
        return "loginn";
    }

    @PostMapping("/loginn")
    public String loginValidate(Model model, UserDto userDto) {
        System.out.println("loginValidate():: userDto= " + userDto);
        // model.addAttribute("user", userDto);

        User user = userService.findByUserId(userDto.getUserId());

        if (user == null) {
            System.out.println("loginValidate():: not a valid login, pls try again with correct User name and password ");
            // user not found in DB
            //model.addAttribute("Userexist", false);
            return "redirect:/login?error=Loginerroroccurred";
        }
        // check password also if it matches or not..
        // encrypt the input password and match it with the one from DB.

        System.out.println("loginValidate():: User= " + user);
        // user found in DB, login successful....
        return "redirect:/empui/index.html";
    }

    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {
        System.out.println("register():: userDto= " + userDto);
        model.addAttribute("user", userDto);
        return "register"; // loading registration page!
    }

    @PostMapping("/register")
    public String registerSava(@ModelAttribute("user") UserDto userDto, Model model) {
        System.out.println("registerSava():: userDto= " + userDto);
        User user = userService.findByUserId(userDto.getUserId());
        if (user != null) {
            // user found in DB, returning registration.html now..
            model.addAttribute("Userexist", user);
            return "register";
        }
        // user found in DB, saving it now..
        userService.save(userDto);
        return "redirect:/register?success";
    }
}