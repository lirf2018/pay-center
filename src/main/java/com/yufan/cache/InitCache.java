package com.yufan.cache;

import com.yufan.cache.service.IInitCacheService;
import com.yufan.pojo.TbClient;
import com.yufan.pojo.TbClientConfig;
import com.yufan.utils.CacheConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 19:24
 * 功能介绍:初始话缓存
 */
@Component
public class InitCache implements InitializingBean {

    private Logger LOG = Logger.getLogger(InitCache.class);

    @Autowired
    private IInitCacheService iInitCacheService;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            List<TbClient> clients = iInitCacheService.loadClientList();
            List<TbClientConfig> clientConfigs = iInitCacheService.loadClientConfigList();
            CacheConstant.clients = clients;
            CacheConstant.clientConfigs = clientConfigs;
            LOG.info("-----clients:" + clients.size());
            LOG.info("-----clientConfigs:" + clientConfigs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
