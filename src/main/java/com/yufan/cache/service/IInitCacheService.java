package com.yufan.cache.service;

import com.yufan.pojo1.TbClient;
import com.yufan.pojo1.TbClientConfig;

import java.util.List;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 19:26
 * 功能介绍:
 */
public interface IInitCacheService {

    /**
     * 查询客户端信息
     *
     * @return
     */
    public List<TbClient> loadClientList();


    /**
     * 查询客户端业务配置
     *
     * @return
     */
    public List<TbClientConfig> loadClientConfigList();


    /**
     * 查询交易记录
     *
     * @param date
     * @return
     */
    public List<Map<String, Object>> queryTradeRecord(String date);

}
