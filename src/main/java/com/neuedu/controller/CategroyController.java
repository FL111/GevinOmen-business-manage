package com.neuedu.controller;

import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user/category")
public class CategroyController {
    @Autowired
    ICategoryService categoryService;

    @RequestMapping("/find")
    public String findAll(HttpSession session) {
        List<Category> categories=categoryService.findAll();
       session.setAttribute("categroylist",categories);

        return "categroylist";
    }
    @RequestMapping("update/{id}")
    public String update(@PathVariable("id") Integer categoryId){


        return "categoryupadte";
    }
}
