package com.neuedu.service.Impl;

import com.neuedu.dao.ProductMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.Product;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    ProductMapper productMapper;
    @Override
    public int addProduct(Product product) throws MyException {
       if (product==null){
           throw new MyException("参数不能为空");
       }
       /**
        * 此处忽略逻辑处理，默认所有参数都传进来了
        *
        * */
        int count=productMapper.insert(product);
        if (count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteProduct(int productId) throws MyException {
        if (productId==0){
            throw new MyException("未知错误");
        }
        int count=productMapper.deleteByPrimaryKey(productId);
        if (count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int updateProduct(Product product) throws MyException {
        if (product==null){
            throw new MyException("参数不能为空");
        }
        int count=productMapper.updateByPrimaryKey(product);
        if (count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public List<Product> findAll() throws MyException {
        return productMapper.selectAll();
    }

    @Override
    public Product findProductById(int productId) {
        Product product=productMapper.selectByPrimaryKey(productId);
        return product;
    }
}
