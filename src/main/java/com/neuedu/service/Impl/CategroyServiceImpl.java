package com.neuedu.service.Impl;

import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategroyServiceImpl implements ICategoryService {
    @Override
    public int addCategory(Category category) throws MyException {
        return 0;
    }

    @Override
    public int deleteCategory(int categroyId) throws MyException {
        return 0;
    }

    @Override
    public int updateCategory(Category category) throws MyException {
        return 0;
    }

    @Override
    public List<Category> findAll() throws MyException {
        return null;
    }
}
