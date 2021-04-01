package com.xb.service;

import com.xb.entity.Filemanager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Alan_
 * @create 2021/4/1 8:18
 */
public interface FileService {
    String upload(String username, MultipartFile file);

    List<Filemanager> serachFileBytype(String type);

    List<Filemanager> findAll();

    void updateCount(String fileName);
}
