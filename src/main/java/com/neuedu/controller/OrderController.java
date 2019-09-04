package com.neuedu.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

import com.neuedu.dbutils.ServerResponse;
import com.neuedu.pojo.Order;
import com.neuedu.pojo.UserInfo;
import com.neuedu.pojoVO.OrderVOPlus;
import com.neuedu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;

    @RequestMapping("/query_order_pay_status.do")
    public ServerResponse query(@RequestParam("orderNo")Long orderNo,
                                HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        return orderService.query(orderNo,userInfo.getId());
    }




    @RequestMapping("/create.do")
    public ServerResponse create(@RequestParam("shippingId")int shippingid,
                                  HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        int userid=userInfo.getId();
        //
        long time=new Date().getTime();
        time=time+userid;
        //
        return orderService.createOrder(shippingid,userid,time);
    }

    @RequestMapping("/get_order_cart_product.do")
    public ServerResponse getOrderCartProduct(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if(userInfo==null){
            return ServerResponse.createServerResponseByFailure(1,"用户未登录");
        }
        return orderService.selectCartByUser(userInfo.getId());
    }
    @RequestMapping("/detail.do")
    public ServerResponse detail(@RequestParam("orderNo")Long orderNo){
        return orderService.getOrderDetail(orderNo);
    }
    @RequestMapping("/cancel.do")
    public ServerResponse cancel(@RequestParam("orderNo")Long orderNo,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        return orderService.cancelOrder(orderNo,userInfo.getId());
    }




    @RequestMapping("/manage/list.do")
    public ServerResponse m_list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录,请登录");
        }
        if (userInfo.getRole()==1){
            return ServerResponse.createServerResponseByFailure(1,"无权访问");
        }
        return orderService.m_getList(pageNum,pageSize);
    }
    @RequestMapping("/manage/search.do")
    public ServerResponse m_search(@RequestParam("orderNo")Long orderNo){
        return orderService.m_findOrder(orderNo);
    }
    @RequestMapping("/manage/detail.do")
    public ServerResponse m_detail(@RequestParam("orderNo")Long orderNo){
        return orderService.m_detailOrder(orderNo);
    }
    @RequestMapping("/manage/send_goods.do")
    public ServerResponse m_send(@RequestParam("orderNo")Long orderNo){
        return orderService.m_sentOrder(orderNo);
    }


    @RequestMapping("/find/{pageNum}/{pageSize}")
    public String list(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, HttpSession session) {
        List<OrderVOPlus> orders = orderService.findAlll(pageNum,pageSize);
        session.setAttribute("orderList", orders);
        session.setAttribute("currentPage", pageNum);
        session.setAttribute("size", pageSize);
        int counn = orderService.getConn();
        counn = counn / pageSize + 1;
        session.setAttribute("conn", counn);
        return "order/list";
    }
    @RequestMapping("/detail/{orderNo}")
    public String detail132(@PathVariable("orderNo")Long orderNo,HttpSession session){
        OrderVOPlus orderVOPlus=orderService.getOrderDetailNew(orderNo);
        session.setAttribute("orderDetail",orderVOPlus);
        return "order/detail";
    }
    @RequestMapping("/send/{orderNo}")
    public String sentGoods(@PathVariable("orderNo")Long orderNo,HttpSession session){
        int count=orderService.sendGoods(orderNo);
        if (count>0){
            return "redirect:/order/find/1/3";
        }
        return "common/error";
    }

}
