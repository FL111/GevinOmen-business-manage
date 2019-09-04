package com.neuedu.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.neuedu.dbutils.ServerResponse;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.PageModul;
import com.neuedu.pojo.Product;
import com.neuedu.pojoVO.ProductVO;

import java.util.List;

public interface IProductService {

    public int addProduct(Product product) throws MyException;
    /**
     * 删除类别
     * */
    public int deleteProduct(int productId) throws MyException;
    /**
     * 修改类别
     * */
    public int updateProduct(Product product) throws MyException;
    /**
     * 查询类别
     * */
    public List<Product> findAll() throws MyException;


    /**
     * 根据类别id查询类别信息
     * */

    public Product findProductById(int productId);

    public int updateStatus(Product product);


    public List<Product> findXXX(PageModul pageModul);

    public int getCount();
    public int getKeyCount(String keyword);

    public List<Product> findProductByName(String name,int pageNum,int pageSize);
    public ServerResponse findProductByIds(Integer id, Integer pageNum, Integer pageSize);
}
