package com.yufan.dao.db1.impl;

import com.yufan.common.IGeneralDao;
import com.yufan.dao.db1.IPayCenterDao;
import com.yufan.pojo1.TbTradeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 18:57
 * 功能介绍:
 */
//@Transactional(transactionManager = "transactionManagerPrimary")
@Transactional
@Repository
public class PayCenterDaoImpl implements IPayCenterDao {


    @Autowired
    private IGeneralDao iGeneralDao;

    @Override
    public int saveObj(Object obj) {
        return iGeneralDao.save(obj);
    }

    @Override
    public TbTradeRecord loadTradeRecordByPartnerTradeNo(String partnerTradeNo) {
        String hql = " from TbTradeRecord where partnerTradeNo = ?1 ";
        return iGeneralDao.queryUniqueByHql(hql, partnerTradeNo);
    }

    @Override
    public TbTradeRecord loadTradeRecordByOrderNo(String orderNo) {
        String hql = " from TbTradeRecord where orderNo = ?1 ";
        return iGeneralDao.queryUniqueByHql(hql, orderNo);
    }

    @Override
    public TbTradeRecord loadTradeRecordByTradeNo(String tradeNo) {
        String hql = " from TbTradeRecord where tradeNo = ?1 ";
        return iGeneralDao.queryUniqueByHql(hql, tradeNo);
    }

    @Override
    public int updateTradeRecord(String partnerTradeNo, String tradeNo, String sellerId, String remark, int tradeStatus) {
        if (remark.length() > 200) {
            remark = remark.substring(0, 150);
        }
        String sql = " update tb_trade_record set trade_no=?,remark=?,status=?,update_time=NOW(),trade_acount=? where partner_trade_no=? ";
        return iGeneralDao.executeUpdateForSQL(sql, tradeNo, remark, tradeStatus, sellerId, partnerTradeNo);
    }

    @Override
    public int finishTradeRecordStatus(String tradeNo, String remark, int tradeStatus) {
        if (remark.length() > 200) {
            remark = remark.substring(0, 150);
        }
        String sql = " update tb_trade_record set finish_time=NOW(),update_time=NOW(),status=?,remark=? where trade_no=? ";
        return iGeneralDao.executeUpdateForSQL(sql, tradeStatus, remark, tradeNo);
    }
}
