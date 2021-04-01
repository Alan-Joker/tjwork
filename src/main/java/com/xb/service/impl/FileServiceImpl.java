package com.xb.service.impl;

import com.xb.entity.Filemanager;
import com.xb.mapper.FileMapper;
import com.xb.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author Alan_
 * @create 2021/4/1 8:20
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Transactional
    @Override
    public String upload(String username, MultipartFile file) {

        //获取文件名
        String filename = file.getOriginalFilename();
        //获取文件类型
        String fileexr = filename.substring(filename.lastIndexOf(".")+1);
        File file1 = new File("D://test//" + filename);
        if(!file1.exists()){
            file1.mkdirs();
        }
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Filemanager files = new Filemanager();
        files.setFilename(filename);
        files.setType(fileexr);
        files.setUrl("D://test//" + filename);
        files.setUsername(username);


        files.setCount(0);
        //保存上传信息
        int flag = fileMapper.saveFile(files);
        return flag > 0 ? "D://test//" + filename : null;
    }

    @Override
    public List<Filemanager> serachFileBytype(String type) {

        if(type == null){
            return null;
        }
        List<Filemanager> filemanagerList = fileMapper.serachFileBytype(type);
        return filemanagerList;
    }

    @Override
    public List<Filemanager> findAll() {

        return fileMapper.findAll();
    }

    @Transactional
    @Override
    public void updateCount(String fileName) {
        int num = fileMapper.selectCountByFileName(fileName);
        fileMapper.updateCountByfilename(num + 1,fileName);
    }


}
