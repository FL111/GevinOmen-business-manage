package com.neuedu.service;

import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.UserInfo;

import java.util.List;

public interface IUserService {

    public UserInfo login(UserInfo userInfo) throws MyException;

    public List<UserInfo> findAll() throws MyException;

    public UserInfo findUserById(int id)throws MyException;

    public int updateUserinfo(UserInfo userInfo);

    public int deleteById(int id);

    public int insertUser(UserInfo userInfo);


}