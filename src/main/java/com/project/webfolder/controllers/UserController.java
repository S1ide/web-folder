package com.project.webfolder.controllers;

import com.project.webfolder.model.Comment;
import com.project.webfolder.model.User;
import com.project.webfolder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    public UserRepository repository;

    @GetMapping(value = "/registration")
    public String registration(){
        return "registration";
    }

    @GetMapping(value = "/addUser")
    public ResponseEntity<Long> addUser(){
        User user = new User("nikita", "123");
        repository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user.getId());
    }

    @GetMapping(value = "getCommentsUser")
    public ResponseEntity<List<Comment>> getComments(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(repository.findUserByUsername("nikita").getComments());
    }


}
