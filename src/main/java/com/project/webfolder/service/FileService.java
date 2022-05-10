package com.project.webfolder.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.project.webfolder.entity.User;
import com.project.webfolder.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    @Value("${web-folder.files.upload-dir}")
    private String fileDir;

    private final AWSCredentials credentials = new BasicAWSCredentials("YCAJEhlAHj3-EkkmThyG3e5WH", "YCNpBr4rR_WL68RphRQKGslZ7Hdw_j5MJ3RDK2a9");
    private final UserService userService;
    private final FileRepository fileRepository;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Path.of(fileDir));
        log.info("Upload files directory: {}", fileDir);
    }

    public String uploadFile(MultipartFile uploadingFile) throws IOException {
        com.project.webfolder.entity.File repoFile = new com.project.webfolder.entity.File();
        repoFile.setDate(LocalDate.now());
        repoFile.setUserId(getCurrentUserId());
        repoFile = fileRepository.save(repoFile);

        String filePath = repoFile.getId() + "/" + uploadingFile.getOriginalFilename();

        repoFile.setPath(filePath);
        Files.createDirectories(Path.of(fileDir + filePath));

        File file = new File(fileDir + filePath);

        uploadingFile.transferTo(file);

        fileRepository.save(repoFile);

        return "redirect:/upload";
    }

    public String uploadFileInCloud(MultipartFile uploadingFile) throws IOException {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("storage.yandexcloud.net", "ru-central1"))
                .build();

        s3.putObject("web-folder", String.format("%s/%s", getCurrentUserId(), uploadingFile.getOriginalFilename()), FileService.convert(uploadingFile));
        return "redirect:/upload";
    }

    public File getFileById(long id) {
        File filesFolder = new File(fileDir + id);
        log.info("Get file by id {}, in path: {}", id, filesFolder.getPath());

        return filesFolder.listFiles()[0];
    }

    public File getFileByIdFromCloud(Long id) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("storage.yandexcloud.net", "ru-central1"))
                .build();
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3).build();
        File file = new File("D:\\web-folder\\downloadedFiles");
        try {
            MultipleFileDownload xfer = transferManager.downloadDirectory(
                    "web-folder", String.valueOf(id), file);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        return file;
    }

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = (User) userService.loadUserByUsername(currentPrincipalName);

        return user.getId();
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
