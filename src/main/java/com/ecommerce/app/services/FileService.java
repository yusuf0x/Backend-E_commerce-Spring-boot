package com.ecommerce.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    @Value("${upload.path}")
    private String UPLOAD_DIR;
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
//        String randomId = UUID.randomUUID().toString();
//        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
        File directory = new File(UPLOAD_DIR +path );
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath =  directory.getPath() + File.separator + name;
        file.transferTo(new File(filePath));
        return name;
    }
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = UPLOAD_DIR + path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
