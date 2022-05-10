package com.project.webfolder.controller;

import com.project.webfolder.entity.Comment;
import com.project.webfolder.repository.CommentRepository;
import com.project.webfolder.service.CommentService;
import com.project.webfolder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/place_comment")
    public String placeComment(@ModelAttribute Comment comment){
        commentService.placeComment(comment);
        return "redirect:/upload";
    }

    @GetMapping("/comments")
    public String list(Model model){
        model.addAttribute("comments", commentService.getComments());
        return "redirect:/.";
    }
}
