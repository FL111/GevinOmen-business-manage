package com.neuedu.service.Impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.dao.*;
import com.neuedu.dbutils.ServerResponse;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.*;
import com.neuedu.pojoVO.*;
import com.neuedu.service.IOrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ShippingMapper shippingMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    PayInfoMapper payInfoMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse createOrder(int shippingid, int userid, Long orderNo) {
        if (shippingid==0||orderNo==null){
            return ServerResponse.createServerResponseByFailure(9,"参数不能为空");
        }
        Order order=new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userid);
        order.setShippingId(shippingid);
        List<OrderItem> itemList=orderMapper.findUserCheckedProduct(userid);
        for(OrderItem orderItem:itemList){
            int x=orderMapper.findProductStatus(orderItem.getProductId());
            int y=orderMapper.findProductStock(orderItem.getProductId());
            if (x!=1){
                return ServerResponse.createServerResponseByFailure(4,"该商品已下架");
            }
            if (y<orderItem.getQuantity()){
                return ServerResponse.createServerResponseByFailure(3,"商品库存不足");
            }
        }
        BigDecimal total=new BigDecimal(0);
        for(OrderItem orderItem:itemList){
            orderItem.setOrderNo(orderNo);
            BigDecimal x=new BigDecimal(orderItem.getQuantity());
            orderItem.setTotalPrice(orderItem.getCurrentUnitPrice().multiply(x));
            total=total.add(orderItem.getTotalPrice());
            int count=orderItemMapper.insert(orderItem);
            if (count<=0){
                return ServerResponse.createServerResponseByFailure(1,"创建订单失败");
            }
        }
        order.setPayment(total);
        int count=orderMapper.insert(order);
        if (count<=0){
            return ServerResponse.createServerResponseByFailure(1,"创建订单失败");
        }
        Order order1=orderMapper.findByOrderNo(orderNo);
        OrderVO orderVO=new OrderVO();
        orderVO=change(order1,orderVO);
        orderVO.setShippingId(shippingid);
        orderVO.setShippingVO(null);
        List<OrderItem> orderItems= orderMapper.findOrderItem(orderNo);
        List<OrderItemVO> orderItemVOS=new ArrayList<>();
        for(OrderItem orderItem:orderItems){
            Product product=productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQuantity());
            productMapper.updateByPrimaryKey(product);
            OrderItemVO orderItemVO=new OrderItemVO();
            orderItemVO=change2(orderItem,orderItemVO);
            orderItemVOS.add(orderItemVO);
        }
        orderVO.setOrderItemVoList(orderItemVOS);
        return ServerResponse.createServerResponseBySuccess(orderVO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse selectByUserId(int userid,int pageNum,int pageSize) {
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<Order> orders=orderMapper.findOrderByUserid(userid);
        if (orders.size()==0){
            return ServerResponse.createServerResponseByFailure(5,"未查询到订单信息");
        }
        List<OrderVOPlus> orderVOPluses=new ArrayList<>();
        for (Order order:orders){
            OrderVOPlus plus=new OrderVOPlus();
            plus=change1(order,plus);
            String recevierName=orderMapper.findRecevierNameById(order.getShippingId());
            plus.setReceiverName(recevierName);
            List<OrderItem> orderItems=orderMapper.findOrderItem(order.getOrderNo());
            List<OrderItemVO> orderItemVOS=new ArrayList<>();
            for (OrderItem orderItem:orderItems){
                OrderItemVO orderItemVO=new OrderItemVO();
                orderItemVO=change2(orderItem,orderItemVO);
                orderItemVOS.add(orderItemVO);
            }
            plus.setOrderItemVoList(orderItemVOS);
            if (plus.getPaymentType()==1){
                plus.setPaymentTypeDesc("在线支付");
            }
            if(plus.getStatus()==10){
                plus.setStatusDesc("未支付");
            }else if (plus.getStatus()==0){
                plus.setStatusDesc("已取消");
            }else if (plus.getStatus()==20){
                plus.setStatusDesc("已付款");
            }else if (plus.getStatus()==40){
                plus.setStatusDesc("已发货");
            }else if (plus.getStatus()==50){
                plus.setStatusDesc("交易成功");
            }else if (plus.getStatus()==60){
                plus.setStatusDesc("交易关闭");
            }
            orderVOPluses.add(plus);
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(orderVOPluses);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse selectCartByUser(int userid) {
        List<OrderItem> itemList=orderMapper.findUserCheckedProduct(userid);
        List<OrderItemVO> orderItemVOS=new ArrayList<>();
        BigDecimal total=new BigDecimal(0);
        OrderVOSub orderVOSub=new OrderVOSub();
        for(OrderItem orderItem:itemList){
            OrderItemVO orderItemVO=new OrderItemVO();
            orderItem.setOrderNo(null);
            BigDecimal x=new BigDecimal(orderItem.getQuantity());
            orderItem.setTotalPrice(orderItem.getCurrentUnitPrice().multiply(x));
            orderItemVO=change2(orderItem,orderItemVO);
            total=total.add(orderItem.getTotalPrice());
            orderItemVOS.add(orderItemVO);
        }
        orderVOSub.setProductTotalPrice(total);
        orderVOSub.setOrderItemVoList(orderItemVOS);
        return ServerResponse.createServerResponseBySuccess(orderVOSub);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse getOrderDetail(Long orderNo) {
        Order order=orderMapper.findByOrderNo(orderNo);
        if (order==null){
            return ServerResponse.createServerResponseByFailure(5,"未查询到订单信息");
        }
        OrderVOPlus orderVOPlus=new OrderVOPlus();
        orderVOPlus=change1(order,orderVOPlus);
        Shipping shipping=shippingMapper.selectByPrimaryKey(order.getShippingId());
        orderVOPlus.setReceiverName(shipping.getReceiverName());
        ShippingVO shippingVO=new ShippingVO();
        shippingVO=change3(shipping,shippingVO);
        if (orderVOPlus.getPaymentType()==1){
            orderVOPlus.setPaymentTypeDesc("在线支付");
        }
        if(orderVOPlus.getStatus()==10){
            orderVOPlus.setStatusDesc("未支付");
        }else if (orderVOPlus.getStatus()==0){
            orderVOPlus.setStatusDesc("已取消");
        }else if (orderVOPlus.getStatus()==20){
            orderVOPlus.setStatusDesc("已付款");
        }else if (orderVOPlus.getStatus()==40){
            orderVOPlus.setStatusDesc("已发货");
        }else if (orderVOPlus.getStatus()==50){
            orderVOPlus.setStatusDesc("交易成功");
        }else if (orderVOPlus.getStatus()==60){
            orderVOPlus.setStatusDesc("交易关闭");
        }
        List<OrderItem> orderItems=orderMapper.findOrderItem(orderNo);
        List<OrderItemVO> orderItemVOS=new ArrayList<>();
        for (OrderItem orderItem:orderItems){
            OrderItemVO orderItemVO=new OrderItemVO();
            orderItemVO=change2(orderItem,orderItemVO);
            orderItemVOS.add(orderItemVO);
        }
        orderVOPlus.setShippingVO(shippingVO);
        orderVOPlus.setOrderItemVoList(orderItemVOS);
        return ServerResponse.createServerResponseBySuccess(orderVOPlus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse cancelOrder(Long orderNo, int userid) {
        if (orderNo==null){
            return ServerResponse.createServerResponseByFailure(9,"参数不能为空");
        }
        Order order=orderMapper.findByOrderNo(orderNo);
        if (order==null){
            return ServerResponse.createServerResponseByFailure(5,"未查询到订单信息");
        }
        Order order1=orderMapper.findByOrderNoAndUserid(orderNo,userid);
        if(order1==null){
            return ServerResponse.createServerResponseByFailure(1,"此用户没有该订单");
        }
        if (order1.getStatus()==0||order1.getStatus()==60){
            return ServerResponse.createServerResponseByFailure(6,"该订单不可取消");
        }
        if (order1.getStatus()==20){
            return ServerResponse.createServerResponseByFailure(1,"此订单已付款，无法被取消");
        }
        order1.setStatus(0);
        Date date=new Date();
        order1.setCloseTime(date);
        orderMapper.updateByPrimaryKey(order1);
        return ServerResponse.createServerResponseBySuccess(0,"取消成功");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse m_getList(int pageNum, int pageSize) {
        Page page=PageHelper.startPage(pageNum,pageSize);
        //List<Order> orders=orderMapper.selectAll();
        Order[] orders=new Order[6];
        List<OrderVOPlus> orderVOPluses=new ArrayList<>();
        for (Order order:orders){
            OrderVOPlus plus=new OrderVOPlus();
            plus=change1(order,plus);
            String recevierName=orderMapper.findRecevierNameById(order.getShippingId());
            plus.setReceiverName(recevierName);
            List<OrderItem> orderItems=orderMapper.findOrderItem(order.getOrderNo());
            List<OrderItemVO> orderItemVOS=new ArrayList<>();
            for (OrderItem orderItem:orderItems){
                OrderItemVO orderItemVO=new OrderItemVO();
                orderItemVO=change2(orderItem,orderItemVO);
                orderItemVOS.add(orderItemVO);
            }
            plus.setOrderItemVoList(orderItemVOS);
            if (plus.getPaymentType()==1){
                plus.setPaymentTypeDesc("在线支付");
            }
            if(plus.getStatus()==10){
                plus.setStatusDesc("未支付");
            }else if (plus.getStatus()==0){
                plus.setStatusDesc("已取消");
            }else if (plus.getStatus()==20){
                plus.setStatusDesc("已付款");
            }else if (plus.getStatus()==40){
                plus.setStatusDesc("已发货");
            }else if (plus.getStatus()==50){
                plus.setStatusDesc("交易成功");
            }else if (plus.getStatus()==60){
                plus.setStatusDesc("交易关闭");
            }
            orderVOPluses.add(plus);
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(orderVOPluses);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse m_findOrder(Long orderNo) {
        Order order=orderMapper.findByOrderNo(orderNo);
        if (order==null){
            return ServerResponse.createServerResponseByFailure(1,"没有找到订单");
        }
        OrderVOPlus orderVOPlus=new OrderVOPlus();
        orderVOPlus=change1(order,orderVOPlus);
        Shipping shipping=shippingMapper.selectByPrimaryKey(order.getShippingId());
        orderVOPlus.setReceiverName(shipping.getReceiverName());
        if (orderVOPlus.getPaymentType()==1){
            orderVOPlus.setPaymentTypeDesc("在线支付");
        }
        if(orderVOPlus.getStatus()==10){
            orderVOPlus.setStatusDesc("未支付");
        }else if (orderVOPlus.getStatus()==0){
            orderVOPlus.setStatusDesc("已取消");
        }else if (orderVOPlus.getStatus()==20){
            orderVOPlus.setStatusDesc("已付款");
        }else if (orderVOPlus.getStatus()==40){
            orderVOPlus.setStatusDesc("已发货");
        }else if (orderVOPlus.getStatus()==50){
            orderVOPlus.setStatusDesc("交易成功");
        }else if (orderVOPlus.getStatus()==60){
            orderVOPlus.setStatusDesc("交易关闭");
        }
        List<OrderItem> orderItems=orderMapper.findOrderItem(orderNo);
        List<OrderItemVO> orderItemVOS=new ArrayList<>();
        for (OrderItem orderItem:orderItems){
            OrderItemVO orderItemVO=new OrderItemVO();
            orderItemVO=change2(orderItem,orderItemVO);
            orderItemVOS.add(orderItemVO);
        }
        orderVOPlus.setOrderItemVoList(orderItemVOS);
        return ServerResponse.createServerResponseBySuccess(orderVOPlus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse m_detailOrder(Long orderNo) {
        Order order=orderMapper.findByOrderNo(orderNo);
        if (order==null){
            return ServerResponse.createServerResponseByFailure(1,"没有找到订单");
        }
        OrderVOPlus orderVOPlus=new OrderVOPlus();
        orderVOPlus=change1(order,orderVOPlus);
        Shipping shipping=shippingMapper.selectByPrimaryKey(order.getShippingId());
        ShippingVO shippingVO=new ShippingVO();
        shippingVO=change3(shipping,shippingVO);
        orderVOPlus.setReceiverName(shipping.getReceiverName());
        if (orderVOPlus.getPaymentType()==1){
            orderVOPlus.setPaymentTypeDesc("在线支付");
        }
        if(orderVOPlus.getStatus()==10){
            orderVOPlus.setStatusDesc("未支付");
        }else if (orderVOPlus.getStatus()==0){
            orderVOPlus.setStatusDesc("已取消");
        }else if (orderVOPlus.getStatus()==20){
            orderVOPlus.setStatusDesc("已付款");
        }else if (orderVOPlus.getStatus()==40){
            orderVOPlus.setStatusDesc("已发货");
        }else if (orderVOPlus.getStatus()==50){
            orderVOPlus.setStatusDesc("交易成功");
        }else if (orderVOPlus.getStatus()==60){
            orderVOPlus.setStatusDesc("交易关闭");
        }
        List<OrderItem> orderItems=orderMapper.findOrderItem(orderNo);
        List<OrderItemVO> orderItemVOS=new ArrayList<>();
        for (OrderItem orderItem:orderItems){
            OrderItemVO orderItemVO=new OrderItemVO();
            orderItemVO=change2(orderItem,orderItemVO);
            orderItemVOS.add(orderItemVO);
        }
        orderVOPlus.setShippingVO(shippingVO);
        orderVOPlus.setOrderItemVoList(orderItemVOS);
        return ServerResponse.createServerResponseBySuccess(orderVOPlus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse m_sentOrder(Long orderNo) {
        Order order=orderMapper.findByOrderNo(orderNo);
        if (order.getStatus()!=20){
            return ServerResponse.createServerResponseByFailure(1,"发货失败");
        }
        order.setStatus(40);
        Date date = new Date();
        order.setSendTime(date);
        int count=orderMapper.updateByPrimaryKey(order);
        if (count<=0){
            return ServerResponse.createServerResponseByFailure(1,"发货失败");
        }
        return ServerResponse.createServerResponseBySuccess("发货成功");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse query(Long orderNo, int userid) {
        Order order=orderMapper.findByOrderNoAndUserid(orderNo,userid);
        if (order==null){
            return ServerResponse.createServerResponseByFailure(1,"该用户并没有该订单,查询无效");
        }
        if (order.getStatus()==10||order.getStatus()==60){
            return ServerResponse.createServerResponseBySuccess(0,"false");
        }else {
            return ServerResponse.createServerResponseBySuccess("true");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = MyException.class,timeout = 10,isolation = Isolation.REPEATABLE_READ)
    public ServerResponse changeStatus(Map<String,String> map){
        Long orderNo=Long.parseLong(map.get("out_trade_no"));
        String tradeNo=map.get("trade_no");
        String trade_status=map.get("trade_status");
        String paymen_time=map.get("gmt_payment");
        Order order=orderMapper.findByOrderNo(orderNo);
        if (order == null) {
            return ServerResponse.createServerResponseByFailure(1,"订单"+orderNo+"不是本平台订单");
        }
        if (order.getOrderNo()>=20){
            return ServerResponse.createServerResponseByFailure(1,"支付宝重复调用");
        }
        if (trade_status.equals("TRADE_SUCCESS")){
            order.setStatus(20);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date= null;
            try {
                date = simpleDateFormat.parse(paymen_time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            order.setPaymentTime(date);
            orderMapper.updateByPrimaryKey(order);
        }
        PayInfo payInfo=new PayInfo();
        payInfo.setOrderNo(orderNo);
        payInfo.setPayPlatform(1);
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(trade_status);
        payInfo.setUserId(order.getUserId());
        int count=payInfoMapper.insert(payInfo);
        if (count>0){
            return ServerResponse.createServerResponseBySuccess();
        }
        return ServerResponse.createServerResponseByFailure(1,"支付宝更新信息失败");
    }

    @Override
    public List<OrderVOPlus> findAlll(int pageNum,int pageSize) {
        PageModul pageModul=new PageModul(pageNum,pageSize);
        List<Order> orders=orderMapper.selectAll(pageModul.getCurrentPage(),pageModul.getPageSize());
        List<OrderVOPlus> orderVOPlusList=new ArrayList<>();
        for (Order order:orders){
            OrderVOPlus orderVOPlus=new OrderVOPlus();
            orderVOPlus=change1(order,orderVOPlus);
            Shipping shipping=shippingMapper.selectByPrimaryKey(order.getShippingId());
            String result=shipping.getReceiverProvince()+shipping.getReceiverCity()+shipping.getReceiverDistrict()+shipping.getReceiverDistrict();
            orderVOPlus.setStatusDesc(result);
            orderVOPlus.setReceiverName(shipping.getReceiverName());
            orderVOPlus.setPhone(shipping.getReceiverPhone());
            orderVOPlusList.add(orderVOPlus);
        }
        return orderVOPlusList;
    }

    @Override
    public int getConn() {
        return orderMapper.getCount();
    }

    @Override
    public OrderVOPlus getOrderDetailNew(Long orderNo) {
        Order order=orderMapper.findByOrderNo(orderNo);
        OrderVOPlus orderVOPlus=new OrderVOPlus();
        orderVOPlus=change1(order,orderVOPlus);
        Shipping shipping=shippingMapper.selectByPrimaryKey(order.getShippingId());
        ShippingVO shippingVO=new ShippingVO();
        shippingVO=change3(shipping,shippingVO);
        orderVOPlus.setReceiverName(shipping.getReceiverName());
        if (orderVOPlus.getPaymentType()==1){
            orderVOPlus.setPaymentTypeDesc("在线支付");
        }
        if(orderVOPlus.getStatus()==10){
            orderVOPlus.setStatusDesc("未支付");
        }else if (orderVOPlus.getStatus()==0){
            orderVOPlus.setStatusDesc("已取消");
        }else if (orderVOPlus.getStatus()==20){
            orderVOPlus.setStatusDesc("已付款");
        }else if (orderVOPlus.getStatus()==40){
            orderVOPlus.setStatusDesc("已发货");
        }else if (orderVOPlus.getStatus()==50){
            orderVOPlus.setStatusDesc("交易成功");
        }else if (orderVOPlus.getStatus()==60){
            orderVOPlus.setStatusDesc("交易关闭");
        }
        List<OrderItem> orderItems=orderMapper.findOrderItem(orderNo);
        List<OrderItemVO> orderItemVOS=new ArrayList<>();
        for (OrderItem orderItem:orderItems){
            OrderItemVO orderItemVO=new OrderItemVO();
            orderItemVO=change2(orderItem,orderItemVO);
            orderItemVOS.add(orderItemVO);
        }
        orderVOPlus.setPhone(shipping.getReceiverPhone());
        orderVOPlus.setShippingVO(shippingVO);
        orderVOPlus.setOrderItemVoList(orderItemVOS);
        return orderVOPlus;
    }

    @Override
    public int sendGoods(Long orderNo) {
        Order order=orderMapper.findByOrderNo(orderNo);
        order.setStatus(40);
        Date date = new Date();
        order.setSendTime(date);
        int count=orderMapper.updateByPrimaryKey(order);
        return count;
    }


    /**
     * order->orderVO
     * */
    public OrderVO change(Order order, OrderVO orderVO){
        orderVO.setEndTime(order.getEndTime());
        orderVO.setCreateTime(order.getCreateTime());
        orderVO.setCloseTime(order.getCloseTime());
        orderVO.setOrderNo(order.getOrderNo());
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentTime(order.getPaymentTime());
        orderVO.setPaymentType(order.getPaymentType());
        orderVO.setPostage(order.getPostage());
        orderVO.setSendTime(order.getSendTime());
        orderVO.setStatus(order.getStatus());
        return orderVO;
    }
    /**
     * order->orderVOPlus
     * */
    public OrderVOPlus change1(Order order, OrderVOPlus orderVOPlus){
        orderVOPlus.setEndTime(order.getEndTime());
        orderVOPlus.setCreateTime(order.getCreateTime());
        orderVOPlus.setCloseTime(order.getCloseTime());
        orderVOPlus.setOrderNo(order.getOrderNo());
        orderVOPlus.setPayment(order.getPayment());
        orderVOPlus.setPaymentTime(order.getPaymentTime());
        orderVOPlus.setPaymentType(order.getPaymentType());
        orderVOPlus.setPostage(order.getPostage());
        orderVOPlus.setSendTime(order.getSendTime());
        orderVOPlus.setStatus(order.getStatus());
        return orderVOPlus;
    }
    /**
     * order_item->order_itemVO
     * */
    public OrderItemVO change2(OrderItem orderItem, OrderItemVO orderItemVO){
        orderItemVO.setCreateTime(orderItem.getCreateTime());
        orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVO.setOrderNo(orderItem.getOrderNo());
        orderItemVO.setProductId(orderItem.getProductId());
        orderItemVO.setProductImage(orderItem.getProductImage());
        orderItemVO.setProductName(orderItem.getProductName());
        orderItemVO.setQuantity(orderItem.getQuantity());
        orderItemVO.setTotalPrice(orderItem.getTotalPrice());
        return orderItemVO;
    }
    /**
     * shipping->shippingVO
     * */
    public ShippingVO change3(Shipping shipping, ShippingVO shippingVO){
        shippingVO.setReceiverAddress(shipping.getReceiverAddress());
        shippingVO.setReceiverCity(shipping.getReceiverCity());
        shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVO.setReceiverMobile(shipping.getReceiverMobile());
        shippingVO.setReceiverName(shipping.getReceiverName());
        shippingVO.setReceiverPhone(shipping.getReceiverPhone());
        shippingVO.setReceiverProvince(shipping.getReceiverProvince());
        shippingVO.setReceiverZip(shipping.getReceiverZip());
        return shippingVO;
    }
}
