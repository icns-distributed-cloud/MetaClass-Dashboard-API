package com.classMetabus.web.Admin.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class FileService {
    // application.properties 에 app.upload.dir을 정의하고, 없는 경우에 default 값으로 user.home (System에 종속적인)
    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    public String fileUpload(MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        File target = new File(uploadDir, LocalTime.now().toString().substring(0,8).replace(":","")+"_"+fileName);

        if ( ! new File(uploadDir).exists()) {
            new File(uploadDir).mkdirs();
        }

        try{
            FileCopyUtils.copy(multipartFile.getBytes(), target);
            return target.toString();
            }catch (IOException e){
            e.printStackTrace();
            }
        return "";
    }

}
