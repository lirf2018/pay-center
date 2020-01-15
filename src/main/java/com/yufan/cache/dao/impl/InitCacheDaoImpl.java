package com.yufan.cache.dao.impl;

import com.yufan.cache.dao.IInitCacheDao;
import com.yufan.common.IGeneralDao;
import com.yufan.pojo1.TbClient;
import com.yufan.pojo1.TbClientConfig;
import com.yufan.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 19:27
 * 功能介绍: dao实现类
 */
//@Transactional(transactionManager = "transactionManagerPrimary")
@Transactional
@Repository
public class InitCacheDaoImpl implements IInitCacheDao {
    @Autowired
    private IGeneralDao iGeneralDao;

    @Override
    public List<TbClient> loadClientList() {
        String hql = " from TbClient where status=1 ";
        return (List<TbClient>) iGeneralDao.queryListByHql(hql);
    }

    @Override
    public List<TbClientConfig> loadClientConfigList() {
        String hql = " from TbClientConfig ";
        return (List<TbClientConfig>) iGeneralDao.queryListByHql(hql);
    }

    @Override
    public List<Map<String, Object>> queryTradeRecord(String date) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT partner_trade_no,DATE_FORMAT(submit_time,'%Y-%m-%d %T') as submit_time from tb_trade_record ");
        sql.append(" where status=").append(Constants.TRADE_STATUS_0).append(" ");
        if (StringUtils.isNotEmpty(date)) {
            sql.append(" and submit_time>'").append(date).append("' ");
        }
        return iGeneralDao.getBySQLListMap(sql.toString());
    }
}
