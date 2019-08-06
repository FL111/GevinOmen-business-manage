package com.neuedu.service.Impl;

import com.neuedu.dao.UserInfoMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo login(UserInfo userInfo) throws MyException {

        if(userInfo==null){
            throw new MyException("参数不可为空");
        }

        if(userInfo.getUsername()==null||userInfo.getUsername().equals("")){
            throw new MyException("用户名不可为空");
        }
        if(userInfo.getPassword()==null||userInfo.getPassword().equals("")){
            throw new MyException("密码不可为空");
        }
        int username_result=userInfoMapper.exsitsUsername(userInfo.getUsername());
        if(username_result==0){
            throw new MyException("用户名不存在");
        }

        UserInfo userInfo1 =userInfoMapper.findByUsernameAndPassword(userInfo);
        if(userInfo1==null){
            throw new MyException("密码错误");
        }
        if(userInfo1.getRole()==0){
            throw new MyException("无权访问");
        }

        return userInfo1;
    }
}
