package com.project.webfolder.controller;

import com.project.webfolder.entity.User;
import com.project.webfolder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(Model model){
       model.addAttribute("userForm", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User userForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "login";
        }

        try {
            UserDetails user = userService.loadUserByUsername(userForm.getUsername());

        }
        catch (UsernameNotFoundException e){
            return "login";
        }

        return "login";

    }
}
