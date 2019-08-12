package com.neuedu.service.Impl;


import com.neuedu.dao.CategoryMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.PageModul;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategroyServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public int addCategory(Category category) throws MyException {
        int count =categoryMapper.insert(category);
        if(count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteCategory(int categoryId) throws MyException {
        int count=categoryMapper.deleteByPrimaryKey(categoryId);
        if(count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int updateCategory(Category category) throws MyException {
        return categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public List<Category> findAll() throws MyException {


        return categoryMapper.selectAll();


    }

    @Override
    public Category findCategoryById(int categoryId) {

        return  categoryMapper.selectByPrimaryKey(categoryId);

    }
    @Override
    public PageModul findXXX(PageModul pageModul) {
        int x=pageModul.getCurrentPage();
        int y=pageModul.getPageSize();
        x=(x-1)*y;
        List<Category> categories=categoryMapper.selectAllByPage(x,y);
        pageModul.setPageList(categories);

        return pageModul;
    }

    @Override
    public int getCount() {
        return categoryMapper.getCount();
    }

}