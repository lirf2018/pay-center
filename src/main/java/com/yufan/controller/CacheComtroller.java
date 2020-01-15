package com.yufan.controller;

import com.alibaba.fastjson.JSONObject;
import com.yufan.cache.service.IInitCacheService;
import com.yufan.pojo1.TbClient;
import com.yufan.pojo1.TbClientConfig;
import com.yufan.utils.CacheConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/15 16:00
 * 功能介绍:
 */
@Controller
@RequestMapping("init")
public class CacheComtroller {


    private Logger LOG = Logger.getLogger(CacheComtroller.class);
    @Autowired
    private IInitCacheService iInitCacheService;

    /**
     * 初始化交易记录
     */
    @RequestMapping("cache")
    public void initTradeRecord(HttpServletResponse response, HttpServletRequest request, String key) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();

            JSONObject json = initClientParam();
            if ("ok".equals(key)) {
                writer.print(json);
            } else {
                writer.print(1);
            }
            writer.close();

        } catch (Exception e) {
            LOG.error("-initTradeRecord--", e);
        }
    }


    /**
     * 初始化客户端系统参数
     */
    private JSONObject initClientParam() {
        try {
            List<TbClient> clients = iInitCacheService.loadClientList();
            List<TbClientConfig> clientConfigs = iInitCacheService.loadClientConfigList();
            CacheConstant.clients = clients;
            CacheConstant.clientConfigs = clientConfigs;
            LOG.info("-----clients:" + clients.size());
            LOG.info("-----clientConfigs:" + clientConfigs.size());
            JSONObject out = new JSONObject();
            out.put("clients", JSONObject.toJSONString(CacheConstant.clients));
            out.put("clientConfigs", JSONObject.toJSONString(CacheConstant.clientConfigs));
            return out;
        } catch (Exception e) {
            LOG.error("-initClientParam--", e);
        }
        return new JSONObject();
    }

}
