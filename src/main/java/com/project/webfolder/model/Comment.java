package com.project.webfolder.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Comment {
    @Id
    private long id;
    @ManyToOne
    @MapsId
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
