package com.xb.mapper;

import com.xb.entity.Filemanager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Alan_
 * @create 2021/4/1 8:38
 */
@Mapper
public interface FileMapper {

    int selectCountByFileName(String username);

    int saveFile(Filemanager files);

    List<Filemanager> serachFileBytype(String type);

    List<Filemanager> findAll();

    void updateCountByfilename(@Param("i") int i,@Param("filename") String filename);
}
