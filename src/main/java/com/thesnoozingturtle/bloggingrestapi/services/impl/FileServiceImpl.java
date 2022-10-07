package com.thesnoozingturtle.bloggingrestapi.services.impl;

import com.thesnoozingturtle.bloggingrestapi.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //file name
        String name = file.getOriginalFilename();

        //Generating random name for file
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId + name.substring(name.lastIndexOf('.'));

        //full path
        String fullPath = path + File.separator + fileName;

        //create folder if not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        //copy file
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        return fileName;
    }

    @Override
    public InputStream downloadImage(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
