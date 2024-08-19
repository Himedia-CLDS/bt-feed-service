package com.clds.bottletalk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileConfig {

    private String linuxPath = "/home" + File.separator + "ec2-user" + File.separator + "bottletalk" + File.separator + "files";
    private String windowsPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "files";

    public File saveFile(String userId, MultipartFile file) throws Exception {

        String os = System.getProperty("os.name");
        UUID uuid = UUID.randomUUID();
        String filePath;

        if (os.contains("Linux")){
            filePath = linuxPath;
        } else {
            filePath = windowsPath;
        }
        String orgName = file.getOriginalFilename();
        int index = orgName.lastIndexOf(".");
        String ext = orgName.substring(index);
        String fileName = userId + "_" + uuid + ext;
        File saveFile = new File(filePath, fileName);

        file.transferTo(saveFile);

        return saveFile;
    }

    public boolean deleteFile(String fileName) throws Exception {
        String os = System.getProperty("os.name");
        String filePath;

        if (os.contains("Linux")){
            filePath = linuxPath;
        } else {
            filePath = windowsPath;
        }
        filePath = filePath + File.separator + fileName;

        File file = new File(filePath);
        boolean isDelete = file.delete();
        return isDelete;
    }
}
