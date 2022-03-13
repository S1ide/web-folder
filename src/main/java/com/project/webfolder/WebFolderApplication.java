package com.project.webfolder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("src/main/java/com/project/webfolder/model")
public class WebFolderApplication {


    public static void main(String[] args) {

        SpringApplication.run(WebFolderApplication.class, args);
    }

}
