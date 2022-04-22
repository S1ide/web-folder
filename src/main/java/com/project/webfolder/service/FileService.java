package com.project.webfolder.service;

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

    public File getFileById(long id) {
        File filesFolder = new File(fileDir + id);
        log.info("Get file by id {}, in path: {}", id, filesFolder.getPath());

        return filesFolder.listFiles()[0];
    }

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = (User) userService.loadUserByUsername(currentPrincipalName);

        return user.getId();
    }
}
