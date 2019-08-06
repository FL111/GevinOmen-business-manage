package com.neuedu.service;

import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICategoryService {

    public int addCategory(Category category)throws MyException;
    public int deleteCategory(int categroyId)throws MyException;
    public int updateCategory(Category category)throws MyException;
    public List<Category> findAll()throws MyException;


}
