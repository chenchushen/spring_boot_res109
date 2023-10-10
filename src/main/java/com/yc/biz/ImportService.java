package com.yc.biz;

import org.springframework.web.multipart.MultipartFile;

public interface ImportService {
    /**
     * 名单导入
     * @param file
     * @return
     */
    String importDeadManList(MultipartFile file);
}
