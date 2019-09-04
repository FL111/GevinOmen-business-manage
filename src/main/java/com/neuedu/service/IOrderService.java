package com.neuedu.service;

import com.neuedu.dbutils.ServerResponse;
import com.neuedu.pojo.Order;
import com.neuedu.pojoVO.OrderVOPlus;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    ServerResponse createOrder(int shippingid, int userid, Long orderNo);

    ServerResponse selectByUserId(int userid, int pageNum, int pageSize);

    ServerResponse selectCartByUser(int userid);

    ServerResponse getOrderDetail(Long orderNo);

    ServerResponse cancelOrder(Long orderNo, int userid);

    ServerResponse m_getList(int pageNum, int pageSize);

    ServerResponse m_findOrder(Long orderNo);

    ServerResponse m_detailOrder(Long orderNo);

    ServerResponse m_sentOrder(Long orderNo);

    ServerResponse query(Long orderNo, int userid);

    ServerResponse changeStatus(Map<String, String> map);


    List<OrderVOPlus> findAlll(int pageNum,int pageSize);

    int getConn();

    OrderVOPlus getOrderDetailNew(Long orderNo);

    int sendGoods(Long orderNo);
}
