package com.neuedu.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MyExceptionHandlerResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        ex.printStackTrace();
        MyException myException = (MyException) ex;
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/common/error");
        Map<String,Object> map=modelAndView.getModel();
        map.put("msg",ex.getMessage());
        map.put("url",myException.getDirector());
        return modelAndView;


    }
}
