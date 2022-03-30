package com.project.webfolder.controllers;

import com.project.webfolder.model.Comment;
import com.project.webfolder.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentController {

    @Autowired
    public CommentRepository repository;

    @GetMapping(value = "/addComment")
    public ResponseEntity<Long> addComment(@){
        Comment comment = new Comment("Привет");
        comment.setId(2);
        repository.save(comment);
        return ResponseEntity.status(HttpStatus.OK).body(comment.getId());
    }
}
