package com.project.webfolder.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String text;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    public Comment(String text){
        this.text = text;
    }

    public Comment(){

    }
}
