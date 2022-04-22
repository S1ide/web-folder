package com.project.webfolder.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private String path;
}
