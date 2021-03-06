package com.neuedu.controller;

import com.neuedu.dbutils.ServerResponse;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.PageModul;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.pojoVO.ProductVO;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/product")
public class ProductController {

    @Autowired
    IProductService productService;

    @RequestMapping(value = "/find/{pageNum}/{pageSize}")
    public String find(@PathVariable("pageNum")int currentPage,
                        @PathVariable("pageSize")int pageSize,
        HttpSession session){
            PageModul pageModul=new PageModul(currentPage,pageSize);
            int counn=productService.getCount();
            counn=counn/pageSize+1;
        List<Product> productList=productService.findXXX(pageModul);
        for(Product product:productList){
            String subImg=product.getSubImages();
            String[] sub=subImg.split(",");
            product.setSubImages(sub[1]);
        }
        session.setAttribute("url","/user/product/find");
        session.setAttribute("currentPage",currentPage);
        session.setAttribute("size",pageSize);
        session.setAttribute("conn",counn);
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
            File tranFile=new File("/neuedu");
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
        File tranFile=new File("/neuedu");
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
            return "redirect:/user/product/find/1/3";
        }
        return "commom/error";
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
//        C:\Users\32871\eclipse-workspace\businessmanager\src\main\webapp\WEB-INF\img
        File tranFile=new File("/neuedu");
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
            return "redirect:/user/product/find/1/3";
        }
        return "commom/error";
    }
    @RequestMapping("/deletesub/{id}/{img}.{enend}")
    public String deletesub(@PathVariable("id") Integer id,
                            @PathVariable("img") String img,
                            @PathVariable("enend")String enend){
        img=img+"."+enend;
        Product product=productService.findProductById(id);
        String subImgs=product.getSubImages();
        String[] subs=subImgs.split(";");
        StringBuffer buffer=new StringBuffer();
        for(String sub:subs){
          // String[] x=sub.split(".");
//            String a=x[0];
            if(!sub.equals(img)){
                buffer.append(sub).append(";");
            }
        }
        buffer.append(";");
        System.out.println(buffer.toString());
        product.setSubImages(buffer.toString());
        int count=productService.updateProduct(product);
        if(count>0){
            return "redirect:/user/product/update/"+product.getId().toString();
        }
        return "commom/error";
    }

    @RequestMapping(value = "/delete/{id}")
    public  String delete(@PathVariable Integer id,HttpSession session){
        int count =productService.deleteProduct(id);
        String url = (String) session.getAttribute("url");
        String[] text=url.split("/");
        String resutlt= null;
        int pageNum=(int) session.getAttribute("currentPage");
        int pageSize=(int) session.getAttribute("size");
        if (text.length==4){
            resutlt="redirect:/user/product/find/"+pageNum+"/"+pageSize;
            return resutlt;
        }
        if (text.length==5){
            try {
                resutlt="redirect:/user/product/search1/"+URLEncoder.encode(text[4],"utf-8")+"/"+pageNum+"/"+pageSize;
                return resutlt;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "commom/error";
    }

    @RequestMapping(value = "/status/{id}/{status}")
    public String status(@PathVariable("id") Integer id,
                         @PathVariable("status") Integer status){
        Product product=new Product();
        product.setStatus(status);
        product.setId(id);
        int count=productService.updateStatus(product);
        if (count>0){
            return "redirect:/user/product/find/1/3";
        }
        return "common/error";
    }



    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String searchPage(){
        return "product/search";
    }
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String searchPro(String keyword, HttpSession session, int pageNum, int pageSize){
        List<Product> products=productService.findProductByName(keyword,pageNum,pageSize);
        for(Product product:products){
            String subImg=product.getSubImages();
            String[] sub=subImg.split(",");
            product.setSubImages(sub[1]);
        }
        int count=productService.getKeyCount(keyword);
        count=count/pageSize+1;
        session.setAttribute("url","/user/product/search1/"+keyword);
        session.setAttribute("conn",count);
        session.setAttribute("currentPage",pageNum);
        session.setAttribute("productlist",products);
        session.setAttribute("keyword",keyword);
        return  "product/list";
    }
    @RequestMapping(value = "/search1/{keyword}/{pageNum}/{pageSize}")
    public String searchPro1(@PathVariable("keyword") String keyword, HttpSession session,
                             @PathVariable("pageNum") int pageNum,
                             @PathVariable("pageSize") int pageSize){
        List<Product> products=productService.findProductByName(keyword,pageNum,pageSize);
        for(Product product:products){
            String subImg=product.getSubImages();
            String[] sub=subImg.split(",");
            product.setSubImages(sub[1]);
        }
        int count=productService.getKeyCount(keyword);
        count=count/pageSize+1;
        session.setAttribute("url","/user/product/search1/"+keyword);
        session.setAttribute("conn",count);
        session.setAttribute("currentPage",pageNum);
        session.setAttribute("productlist",products);
        session.setAttribute("keyword",keyword);
        return  "product/list";
    }

    @RequestMapping("/detail/{productId}")
    public String detailProduct(@PathVariable("productId") int productId,HttpSession session){
        Product product=productService.findProductById(productId);
        session.setAttribute("product",product);
        return "product/detail";
    }
}
