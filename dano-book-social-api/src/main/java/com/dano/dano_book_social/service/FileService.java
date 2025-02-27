package com.dano.dano_book_social.service;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
    @Value("${application.file.upload.pictures-path}")
    private String PICTURE_UPLOAD_PATH;

    public String saveFile(MultipartFile file, Integer userId) {

        String fileUploadSubPath = "users" + separator + userId;

        return uploadFile(file, fileUploadSubPath);
    }

    private String uploadFile(MultipartFile file, String fileUploadSubPath) {
        final String finalUploadPath = PICTURE_UPLOAD_PATH + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Filed to create the traget folder");
                return null;
            }
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        String targetFilePath = finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;

        Path targetPath = Paths.get(targetFilePath); 

        try {
            Files.write(targetPath, file.getBytes());
            log.info("File saved to " + targetFilePath);
            return targetFilePath;
        } catch(IOException exc) {
            log.error("File was not saved", exc);
        }

        return null;

    }

    private String getFileExtension(String fileName) {
        if(fileName == null || fileName.isBlank()) {
            return null;
        }


        int index = fileName.lastIndexOf(".");

        return fileName.substring(index + 1);

    }

    public byte[] readFile(String location) {

        if(StringUtils.isBlank(location)) return null;

        try {
            Path filePath = Paths.get(location);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.warn("No file found");
        }
        return null;
    }
}
