package com.yufan.dao.impl;

import com.yufan.common.IGeneralDao2;
import com.yufan.dao.IOrderDao;
import com.yufan.pojo.TbOrder;
import com.yufan.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 22:32
 * 功能介绍:
 */
@Transactional(transactionManager = "transactionManagerDb2")
@Repository
public class OrderDaoImpl implements IOrderDao {

    @Autowired
    private IGeneralDao2 iGeneralDao2;

    @Override
    public TbOrder loadOrderByOrderNo(String orderNo) {
        String hql = " from TbOrder where orderNo=?1 ";
        return iGeneralDao2.queryUniqueByHql(hql, orderNo);
    }

    @Override
    public List<Map<String, Object>> queryOrderListMap(String orderNo) {
        String sql = " SELECT o.order_status,o.order_price,d.goods_name from tb_order o JOIN tb_order_detail d on o.order_id=d.order_id where o.order_no=? and o.order_status=? ";
        return iGeneralDao2.getBySQLListMap(sql, orderNo, Constant.ORDER_STATUS_0);
    }
}
