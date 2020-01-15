package com.yufan.cache.service.impl;

import com.yufan.cache.dao.IInitCacheDao;
import com.yufan.cache.service.IInitCacheService;
import com.yufan.pojo1.TbClient;
import com.yufan.pojo1.TbClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 19:26
 * 功能介绍: 服务实现类
 */
@Service
public class InitCacheServiceImpl implements IInitCacheService {

    @Autowired
    private IInitCacheDao iInitCacheDao;

    @Override
    public List<TbClient> loadClientList() {
        return iInitCacheDao.loadClientList();
    }

    @Override
    public List<TbClientConfig> loadClientConfigList() {
        return iInitCacheDao.loadClientConfigList();
    }

    @Override
    public List<Map<String, Object>> queryTradeRecord(String date) {
        return iInitCacheDao.queryTradeRecord(date);
    }
}
