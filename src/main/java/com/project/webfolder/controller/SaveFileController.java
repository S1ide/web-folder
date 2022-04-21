package com.project.webfolder.controller;

import com.project.webfolder.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SaveFileController {
    private final FileService fileService;

    @GetMapping("/upload")
    public String upload() {
        return "fileUpload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile uploadingFile) throws IOException {
        fileService.uploadFile(uploadingFile);

        return "redirect:/upload";
    }

    @GetMapping("/download")
    public String download() {
        return "fileDownload";
    }

    @PostMapping("/download")
    public ResponseEntity<FileSystemResource> downloadFile(@RequestParam("id") Long id) {
        File file = fileService.getFileById(id);

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentLength(file.length());
        respHeaders.setContentDispositionFormData("attachment", file.getName());

        return new ResponseEntity<>(new FileSystemResource(file), respHeaders, HttpStatus.OK);
    }
}
