package com.project.webfolder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.project.webfolder.entity")
@EnableJpaRepositories("com.project.webfolder.repository")
public class WebFolderApplication {



    public static void main(String[] args) {
        SpringApplication.run(WebFolderApplication.class, args);

    }

}
