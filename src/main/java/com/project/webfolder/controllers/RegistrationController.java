package com.project.webfolder.controllers;

import com.project.webfolder.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationRepository repository;

    @GetMapping(value = "/registration")
    public String registration(){
        return "registration";
    }
}
