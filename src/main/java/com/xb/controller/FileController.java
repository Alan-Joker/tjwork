package com.xb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xb.entity.Filemanager;
import com.xb.entity.Result;
import com.xb.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Alan_
 * @create 2021/4/1 7:37
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param username
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("username") String username, @RequestParam("file")MultipartFile file){

        String filepath = fileService.upload(username,file);
        return Result.ok().data("filepath",filepath);
    }
    /**
     * 分类查询文件
     */
    @GetMapping("/serachFileBytype")
    public Result serachFileBytype(@RequestParam("type") String type){
        List<Filemanager> filemanagerList = fileService.serachFileBytype(type);
        if(filemanagerList == null){
            return Result.error().message("没有此类型的数据");
        }
        return Result.ok().data("lists",filemanagerList);
    }

    /**
     * 分页查询所有文件信息
     */
    @GetMapping("/findAll")
    public Result findAll(@RequestParam("page") Integer page,@RequestParam("rows") Integer rows){
        PageHelper.startPage(page,rows);
        List<Filemanager> lists = fileService.findAll();
        PageInfo<Filemanager> pageInfo = new PageInfo<>(lists);
        //总记录数
        long total = pageInfo.getTotal();
        //当前页数据
        List<Filemanager> fileList = pageInfo.getList();
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("fileList",fileList);
        return Result.ok().data(map);
    }
    /**
     * 文件下载
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "fileDownload")
    public void fileDownload(HttpServletResponse response,@RequestParam String filename,@RequestParam String filePath) throws IOException {
        //自己定义文件名和文件路径
        String fileName = filename;
        //根据filename查询下载次数并+1
        //fileService.updateCount(fileName);
        //读取文件
        FileInputStream fis = null;
        try {
            setResponseHeader(response, fileName);
            fis = new FileInputStream(new File(filePath));
            //获得HttpServletResponse输出流
            OutputStream os = response.getOutputStream();
            //缓存字节数组
            byte[] b = new byte[2048];
            int length;
            //把输入流中的文件分段读入缓存字节数组，再把字节数组中的数据写到输出流。
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();
            //根据filename查询下载次数并+1
            fileService.updateCount(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * 设置响应HTTP报文头
     * @param response HTTP响应
     * @param fileName 文件名
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName);
    }

}
