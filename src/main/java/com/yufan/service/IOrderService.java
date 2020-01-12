package com.yufan.service;

import com.yufan.pojo.TbOrder;

import java.util.List;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 22:30
 * 功能介绍:
 */
public interface IOrderService {

    /**
     * 查询订单信息
     *
     * @param orderNo
     * @return
     */
    public TbOrder loadOrderByOrderNo(String orderNo);


    /**
     * 查询订单详情
     *
     * @param orderNo
     * @return
     */
    public List<Map<String, Object>> queryOrderListMap(String orderNo);

}
