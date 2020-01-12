package com.yufan.service.impl;

import com.yufan.dao.db2.IOrderDao;
import com.yufan.pojo2.TbOrder;
import com.yufan.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 22:31
 * 功能介绍:
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao iOrderDao;


    @Override
    public TbOrder loadOrderByOrderNo(String orderNo) {
        return iOrderDao.loadOrderByOrderNo(orderNo);
    }

    @Override
    public List<Map<String, Object>> queryOrderListMap(String orderNo) {
        return iOrderDao.queryOrderListMap(orderNo);
    }
}
