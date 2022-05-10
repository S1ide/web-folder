package com.project.webfolder.service;

import com.project.webfolder.entity.Comment;

import com.project.webfolder.entity.User;
import com.project.webfolder.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    private final UserService userService;

    public String placeComment(Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userService.loadUserByUsername(currentPrincipalName);

        comment.setDate(LocalDate.now());
        comment.setUser(user);
        commentRepository.save(comment);
        return "redirect:/.";
    }

    public List<Comment> getComments(){
        return commentRepository.findAll();
    }
}
