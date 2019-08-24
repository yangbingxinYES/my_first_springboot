package com.springboot.demo_ybx07.controller;

import com.springboot.demo_ybx07.DemoYbx07Application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import exception.MyException;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传.
 *
 * @author 杨冰鑫
 * @since 2019/8/24 22:33
 */
@Slf4j
@PropertySource("classpath:application.properties")
@Controller
@RequestMapping("/springboot/")
public class FileUploadApiController {

    /**
     * 跳转页面
     * @param html 页面name
     * @return 页面
     */
    @GetMapping("html/{html}")
    public String html(@PathVariable("html") String html){
        return html;
    }

    /**
     * 实现文件上传
     * */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file)throws Exception{
        if(file.isEmpty()){
            return "亲，还没选择文件呢！";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        File path = new File(ResourceUtils.getURL("").getPath());
        File upload = new File( URLDecoder.decode(path.getPath(), "UTF-8"),"src/main/resources/static/fileupload/");
        //时间格式化格式
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //获取当前时间并作为时间戳
        String timeStamp=simpleDateFormat.format(new Date());
        //获取文件的类型
        String contentType = file.getContentType();
        String[] split = contentType.split("/");
        File dest = new File(upload + "/" +timeStamp+"."+split[1]);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();//创建文件夹
        }
        try {
            file.transferTo(dest); //保存文件
            return "文件上传成功";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "文件上传失败！";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "文件上传失败！";
        }
    }


}
