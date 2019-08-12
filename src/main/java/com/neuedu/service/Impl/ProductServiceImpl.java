package com.neuedu.service.Impl;

import com.neuedu.dao.ProductMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.PageModul;
import com.neuedu.pojo.Product;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int count= 0;
        if(product.getMainImage().equals("")&&!product.getSubImages().equals("")){
            count=productMapper.updateByMain(product);
        }else if (!product.getMainImage().equals("")&&product.getSubImages().equals("")){
            count=productMapper.updateBySub(product);
        }else if(product.getMainImage().equals("")&&product.getSubImages().equals("")){
            count=productMapper.updateByAll(product);
        }else {
            count=productMapper.updateByPrimaryKey(product);
        }
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

    @Override
    public int updateStatus(Product product) {
        int count=productMapper.updateStatus(product);
        if(count>0){
            return 1;
        }
        return 0;
    }
    @Override
    public PageModul findXXX(PageModul pageModul) {
        int x=pageModul.getCurrentPage();
        int y=pageModul.getPageSize();
        x=(x-1)*y;
        List<Product> products=productMapper.selectAllByPage(x,y);
        pageModul.setPageList(products);

        return pageModul;
    }

    @Override
    public int getCount() {
        return productMapper.getCount();
    }
}
