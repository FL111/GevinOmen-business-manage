package com.neuedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String upload(){
        return "upload";
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("picture")MultipartFile uploadFile){
        String uuid= UUID.randomUUID().toString();
        String fileName=uploadFile.getOriginalFilename();
        String fileextendname=fileName.substring(fileName.lastIndexOf(".")+1);
        String newFilename=uuid+"."+fileextendname;
        System.out.println(newFilename);
        File file=new File("D:\\upload");
        if(!file.exists()){
            file.mkdir();
        }
        File newFile=new File(file,newFilename);
        try {
            uploadFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "upload";
    }

}
