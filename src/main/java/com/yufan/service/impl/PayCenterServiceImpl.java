package com.yufan.service.impl;

import com.yufan.dao.IPayCenterDao;
import com.yufan.pojo.TbTradeRecord;
import com.yufan.service.IPayCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 18:56
 * 功能介绍: 服务层实现类
 */
@Service
public class PayCenterServiceImpl implements IPayCenterService {

    @Autowired
    private IPayCenterDao iPayCenterDao;

    @Override
    public int saveObj(Object obj) {
        return iPayCenterDao.saveObj(obj);
    }

    @Override
    public TbTradeRecord loadTradeRecordByPartnerTradeNo(String partnerTradeNo) {
        return iPayCenterDao.loadTradeRecordByPartnerTradeNo(partnerTradeNo);
    }

    @Override
    public TbTradeRecord loadTradeRecordByOrderNo(String orderNo) {
        return iPayCenterDao.loadTradeRecordByOrderNo(orderNo);
    }

    @Override
    public TbTradeRecord loadTradeRecordByTradeNo(String tradeNo) {
        return iPayCenterDao.loadTradeRecordByTradeNo(tradeNo);
    }

    @Override
    public int updateTradeRecord(String partnerTradeNo, String tradeNo, String sellerId, String remark, int tradeStatus) {
        return iPayCenterDao.updateTradeRecord(partnerTradeNo, tradeNo, sellerId, remark, tradeStatus);
    }

    @Override
    public int finishTradeRecordStatus(String tradeNo, String remark, int tradeStatus) {
        return iPayCenterDao.finishTradeRecordStatus(tradeNo, remark, tradeStatus);
    }
}
