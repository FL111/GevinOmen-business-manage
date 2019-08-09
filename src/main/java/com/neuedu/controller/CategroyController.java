package com.neuedu.controller;

import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
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
@RequestMapping("/user/category/")
public class CategroyController {

    @Autowired
    ICategoryService categoryService;

    @RequestMapping("find")
    public  String  findAll(HttpSession session,HttpServletResponse response,HttpServletRequest request)throws UnsupportedEncodingException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        List<Category> categoryList=categoryService.findAll();

        session.setAttribute("categorylist",categoryList);
        return "category/list";
    }


    @RequestMapping(value = "update/{id}",method = RequestMethod.GET)
    public  String  update(@PathVariable("id") Integer categoryId, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Category category=categoryService.findCategoryById(categoryId);
        request.setAttribute("category",category);

        return "category/index";
    }

    @RequestMapping(value = "update/{id}",method = RequestMethod.POST)
    public  String  update(Category category, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //
        int count= categoryService.updateCategory(category);

        if(count>0){
            //修改成功
            return "redirect:/user/category/find";
        }

        return "category/index";
    }

    @RequestMapping(value = "insert",method = RequestMethod.GET)
    public String update(){
        return "category/insert";
    }

    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public String update(Category category){
        int count = categoryService.addCategory(category);
        if(count>0){
            return  "redirect:/user/category/find";
        }
        return "category/insert";
    }



    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id")int id){
        int count = categoryService.deleteCategory(id);
        if(count>0){
            return  "redirect:/user/category/find";
        }
        return "category/list";
    }
}