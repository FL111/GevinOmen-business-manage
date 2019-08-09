package com.neuedu.controller;

import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/product")
public class ProductController {

    @Autowired
    IProductService productService;

    @RequestMapping(value = "/find")
    public String find(HttpSession session){
        List<Product> productList=productService.findAll();
        for(Product product:productList){
            String subImg=product.getSubImages();
            String[] sub=subImg.split(";");
            product.setSubImages(sub[0]);
        }
        session.setAttribute("productlist",productList);
        return "product/list";
    }


    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public  String  update(@PathVariable("id") Integer productId,HttpServletRequest request){


        Product product=productService.findProductById(productId);
        String subImg=product.getSubImages();
        String[] sub=subImg.split(";");
        List<String> list=new ArrayList<>();
        for(int i=0;i<(sub.length);i++){
            System.out.println(sub[i]);
            list.add(sub[i]);
        }
        request.setAttribute("imglist",list);
        request.setAttribute("productInfo",product);

        return "product/index";
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(@RequestParam("picture")MultipartFile file,
                         @RequestParam("picture1")MultipartFile[] files,
                         @RequestParam("categoryId")int categoryId,
                         @RequestParam("id")int id,
                         @RequestParam("name")String name,
                         @RequestParam("subtitle")String subtitle,
                         @RequestParam("detail")String detail,
                         @RequestParam("price") BigDecimal price,
                         @RequestParam("stock")Integer stock,
                         @RequestParam("status")Integer status){
        String uuid= UUID.randomUUID().toString();
        String uuid1=UUID.randomUUID().toString();
        String fileName=file.getOriginalFilename();
        StringBuffer buffer=null;
        for(MultipartFile file1:files){
            String fileName1=file1.getOriginalFilename();
            String fileextendname1=fileName1.substring(fileName1.lastIndexOf(".")+1);
            String newFilename1=uuid1+"."+fileextendname1;
            File tranFile=new File("C:\\Users\\32871\\eclipse-workspace\\businessmanager\\src\\main\\webapp\\WEB-INF\\img");
            if(!tranFile.exists()){
                tranFile.mkdir();
            }
            File newFile1=new File(tranFile,newFilename1);
            try {
                file1.transferTo(newFile1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.append(newFilename1).append(";");
        }
        String fileextendname=fileName.substring(fileName.lastIndexOf(".")+1);
        String newFilename=uuid+"."+fileextendname;
        File tranFile=new File("C:\\Users\\32871\\eclipse-workspace\\businessmanager\\src\\main\\webapp\\WEB-INF\\img");
        if(!tranFile.exists()){
            tranFile.mkdir();
        }
        File newFile=new File(tranFile,newFilename);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product=new Product(categoryId,name,subtitle,newFilename,price,stock,status,buffer.toString(),detail);
        int count = productService.updateProduct(product);
        if (count>0){
            return "redirect:/user/product/find";
        }
        return "product/index";
    }

    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public String insert(){
        return "product/insert";
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public String insert(@RequestParam("picture")MultipartFile file,
                         @RequestParam("picture1")MultipartFile[] files,
                         @RequestParam("categoryId")int categoryId,
                         @RequestParam("name")String name,
                         @RequestParam("subtitle")String subtitle,
                         @RequestParam("detail")String detail,
                         @RequestParam("price") BigDecimal price,
                         @RequestParam("stock")Integer stock,
                         @RequestParam("status")Integer status){
        StringBuffer buffer=new StringBuffer("");
        File tranFile=new File("C:\\Users\\32871\\eclipse-workspace\\businessmanager\\src\\main\\webapp\\WEB-INF\\img");
        if(!tranFile.exists()){
            tranFile.mkdir();
        }
        for(MultipartFile file1:files){
            String uuid=UUID.randomUUID().toString();
            String fileName1=file1.getOriginalFilename();
            String fileextendname1=fileName1.substring(fileName1.lastIndexOf(".")+1);
            String newFilename1=uuid+"."+fileextendname1;
            File newFile1=new File(tranFile,newFilename1);
            try {
                file1.transferTo(newFile1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.append(newFilename1).append(";");
        }
        String uuid= UUID.randomUUID().toString();
        String fileName=file.getOriginalFilename();
        String fileextendname=fileName.substring(fileName.lastIndexOf(".")+1);
        String newFilename=uuid+"."+fileextendname;
        File newFile=new File(tranFile,newFilename);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product=new Product(categoryId,name,subtitle,newFilename,price,stock,status,buffer.toString(),detail);
        int count = productService.addProduct(product);
        if (count>0){
            return "redirect:/user/product/find";
        }
        return "product/list";
    }

    @RequestMapping(value = "/delete/{id}")
    public  String delete(@PathVariable Integer id){
        int count =productService.deleteProduct(id);
        if (count>0){
            return "redirect:/user/product/find";
        }
        return "error";
    }


}
