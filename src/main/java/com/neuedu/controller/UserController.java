package com.neuedu.controller;

import com.neuedu.consts.Const;
import com.neuedu.dbutils.MD5Utils;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.PageModul;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public  String login(){
        return "login";
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public  String login(UserInfo userInfo, HttpSession session, HttpServletResponse response){
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        UserInfo loginUserInfo= userService.login(userInfo);
        session.setAttribute("userinfo",loginUserInfo);
        System.out.println(loginUserInfo);

        if(loginUserInfo!=null){
            session.setAttribute(Const.CURRENT_USER,loginUserInfo);
            Cookie cookie=new Cookie("username",loginUserInfo.getUsername());
            Cookie cookie1=new Cookie("password",loginUserInfo.getPassword());
            cookie.setMaxAge(7*24*60*60);
            cookie1.setMaxAge(7*24*60*60);
            cookie.setPath("/user");
            cookie1.setPath("/user");
            response.addCookie(cookie);
            response.addCookie(cookie1);
            return "redirect:/user/category/find";

        }
        return "login";
    }


    @RequestMapping("userlist")
    public  String  home(){

        return "Userlist";
    }
    @RequestMapping("/restful/{id}/{username}")
    public  String testRestful(@PathVariable("id")int id ,@PathVariable("username")String username){
        System.out.println("id:"+id);
        return "login";
    }

    @RequestMapping("find/{pageNum}/{pageSize}")
    public  String  findAll(@PathVariable("pageSize")int pageSize,
                            @PathVariable("pageNum") int currentPage,
                            HttpSession session,HttpServletResponse response,HttpServletRequest request)throws UnsupportedEncodingException {
        PageModul pageModul=new PageModul();
        pageModul.setPageSize(pageSize);
        pageModul.setCurrentPage(currentPage);
        pageModul=userService.findXXX(pageModul);
        int counn=userService.getCount();
        counn=counn/pageSize+1;
        pageModul.setPageCount(counn);
        List<UserInfo> userList=pageModul.getPageList();
        for(UserInfo userInfo:userList){
            System.out.println(userInfo);
        }
        session.setAttribute("currentPage",currentPage);
        session.setAttribute("size",pageSize);
        session.setAttribute("conn",counn);
        session.setAttribute("userlist",userList);
        return "user/list";
    }

    @RequestMapping(value = "update/{id}",method = RequestMethod.GET)
    public  String  update(@PathVariable("id") Integer userId, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        UserInfo userInfo=userService.findUserById(userId);
        request.setAttribute("userInfo",userInfo);

        return "user/index";
    }

    @RequestMapping(value = "update/{id}",method = RequestMethod.POST)
    public  String  update(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //
        int count= userService.updateUserinfo(userInfo);

        if(count>0){
            //修改成功
            return "redirect:/user/find";
        }

        return "user/index";
    }
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id")int id){
        int count = userService.deleteById(id);
        if(count>0){
            return  "redirect:/user/find";
        }
        return "user/index";
    }

    @RequestMapping(value = "insert",method = RequestMethod.GET)
    public String insert(){

        return "user/index";
    }
    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public String insert(UserInfo userInfo){
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        int count =userService.insertUser(userInfo);
        if (count>0){
            return "redirect:/user/find";
        }
        return "user/index";
    }

    @RequestMapping(value = "quit/{id}",method = RequestMethod.GET)
    public String quit(@PathVariable("id") int id,HttpServletRequest request,HttpServletResponse response){
        UserInfo userInfo=userService.findUserById(id);
        HttpSession session=request.getSession();
        if(userInfo!=null){
            session.setAttribute("username",null);
            session.setAttribute("password",null);
            Cookie[] cookies=request.getCookies();
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("username")){
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    cookie.setPath("/user");
                    response.addCookie(cookie);
                }else if(cookie.getName().equals("password")){
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    cookie.setPath("/user");
                    response.addCookie(cookie);
                }
            }
        }
        return "login";
    }

}