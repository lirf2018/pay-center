package com.yufan.cache.dao.impl;

import com.yufan.cache.dao.IInitCacheDao;
import com.yufan.common.IGeneralDao;
import com.yufan.pojo1.TbClient;
import com.yufan.pojo1.TbClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 19:27
 * 功能介绍: dao实现类
 */
@Transactional(transactionManager = "transactionManagerPrimary")
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
}
