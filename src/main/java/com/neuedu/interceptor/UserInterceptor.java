package com.neuedu.interceptor;

import com.mysql.cj.Session;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class UserInterceptor implements HandlerInterceptor{

    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String uri = request.getRequestURI();
        if (uri.indexOf("/login") >= 0) {
               return true;
        }
        HttpSession session=request.getSession();
        UserInfo userInfo=(UserInfo) session.getAttribute("user");
        if (userInfo!=null){
            return true;
        }
        Cookie[] cookies=request.getCookies();
        String x=null;
        String y=null;
        for(Cookie cookie:cookies){
            if("username".equals(cookie.getName())){
                x=cookie.getValue();
            }
            if("password".equals(cookie.getName())){
                y=cookie.getValue();
            }
        }
        UserInfo userInfo2=new UserInfo(x,y);
        UserInfo userInfo1=userService.login(userInfo2);
        if(userInfo1!=null){
            session.setAttribute("userinfo",userInfo1);
            return true;
        }


        request.getRequestDispatcher("/user/login").forward(request,response);

        return false;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
