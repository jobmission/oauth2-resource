package com.revengemission.sso.oauth2.resource.coupon.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    /**
     * 保存等硬盘等
     *
     * @param directoryPath
     * @param file
     * @return
     * @throws IOException
     */
    String save(Path directoryPath, MultipartFile file) throws IOException;

    /**
     * 以Resource的形式读取
     *
     * @param fullPath
     * @return
     * @throws FileNotFoundException
     */
    Resource loadAsResource(Path fullPath) throws FileNotFoundException;
}
