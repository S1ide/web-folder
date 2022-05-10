package com.project.webfolder.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.project.webfolder.WebFolderApplication;
import com.project.webfolder.service.CommentService;
import com.project.webfolder.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final CommentService commentService;

    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("comments", commentService.getComments());
        return "fileUpload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile uploadingFile) throws IOException {
        return fileService.uploadFile(uploadingFile);
    }

    @PostMapping("/upload_cloud")
    public String uploadFileInCloud(@RequestParam("file") MultipartFile uploadingFile) throws IOException {
        return fileService.uploadFileInCloud(uploadingFile);
    }

    @GetMapping("/download")
    public String download(Model model) {
        model.addAttribute("comments", commentService.getComments());
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

    @PostMapping("/download_cloud")
    public ResponseEntity<FileSystemResource> downloadFileFromCloud(@RequestParam("id") Long id){
        File file = fileService.getFileByIdFromCloud(id);
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentLength(file.length());

        return new ResponseEntity<>(new FileSystemResource(file), respHeaders, HttpStatus.OK);
    }

}
