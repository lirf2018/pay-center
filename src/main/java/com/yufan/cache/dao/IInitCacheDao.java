package com.yufan.cache.dao;

import com.yufan.pojo1.TbClient;
import com.yufan.pojo1.TbClientConfig;

import java.util.List;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 19:26
 * 功能介绍: dao类
 */
public interface IInitCacheDao {

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

}
