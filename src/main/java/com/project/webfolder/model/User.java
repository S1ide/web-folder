package com.project.webfolder.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate joiningDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate leavingDate;
    private boolean active;


    public User(String username, String password){
        this.username = username;
        this.password = password;
        active = true;
    }


    public User() {

    }
}
