package com.project.webfolder.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    private List<File> files;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate joiningDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate leavingDate;
    private boolean active;


    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.comments = new ArrayList<>();
        active = true;
    }


    public User() {

    }
}
