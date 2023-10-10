package com.yc.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class MutipartFileToFileUtils {
    /**
     * TODO MultipartFile文件转为File文件
     *
     * @param multipartFile
     * @return
     */
    public static File MultipartFileToFile(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        String perfix = filename.substring(filename.lastIndexOf("."));
        File file = null;
        try {
            file = File.createTempFile(filename, perfix);
            multipartFile.transferTo(file);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
