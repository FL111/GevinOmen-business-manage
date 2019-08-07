package com.neuedu.controller;

import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/user/product")
public class ProductController {

    @Autowired
    IProductService productService;

    @RequestMapping(value = "/find")
    public String find(HttpSession session){
        List<Product> productList=productService.findAll();
        session.setAttribute("productlist",productList);
        return "productlist";
    }


    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public  String  update(@PathVariable("id") Integer producrId,HttpServletRequest request){


        Product product=productService.findProductById(producrId);

        request.setAttribute("product",product);

        return "productupdate";
    }
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public  String update(Product product){
        int count =productService.updateProduct(product);
        if(count>0){
            return "redirect:/user/product/find";
        }
        return "productupdate";
    }

    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public String insert(){
        return "productinsert";
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public String insert(Product product){
        int count = productService.addProduct(product);
        if (count>0){
            return "productinsert";
        }
        return "productinsert";
    }

    @RequestMapping(value = "/delete/{id}")
    public  String delete(@PathVariable Integer id){
        int count =productService.deleteProduct(id);
        if (count>0){
            return "productlist";
        }
        return "err";
    }


}
