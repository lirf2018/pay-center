package com.yufan.utils;

import com.alibaba.fastjson.JSONObject;
import com.yufan.pojo.TbClient;
import com.yufan.pojo.TbClientConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Random;

/**
 * 创建人: lirf
 * 创建时间:  2019/8/27 10:14
 * 功能介绍:
 */
public class CommonCacheMethod {

    private static Logger LOG = Logger.getLogger(CommonCacheMethod.class);

    /**
     * 获取客户端请求系统相关参数
     *
     * @param clientCode
     * @param businessCode
     * @return
     */
    public static JSONObject getClientParam(String clientCode, String businessCode) {
        try {
            TbClient client = null;
            for (int i = 0; i < CacheConstant.clients.size(); i++) {
                TbClient client_ = CacheConstant.clients.get(i);
                if (clientCode.equals(client_.getClientCode())) {
                    client = client_;
                    break;
                }
            }
            if (null == client) {
                LOG.info("---client=null-------");
                return null;
            }
            TbClientConfig clientConfig = null;
            int clientId = client.getClientId();
            for (int i = 0; i < CacheConstant.clientConfigs.size(); i++) {
                TbClientConfig clientConfig_ = CacheConstant.clientConfigs.get(i);
                int clientId_ = clientConfig_.getClientId();
                if (clientId == clientId_ && businessCode.equals(clientConfig_.getBusinessCode())) {
                    clientConfig = clientConfig_;
                    break;
                }
            }
            if (null == clientConfig) {
                LOG.info("---clientConfig=null-------");
                return null;
            }
            String secretKey = client.getSecretKey();
            String quitUrl = StringUtils.isEmpty(clientConfig.getQuitUrl()) ? client.getQuitUrl() : clientConfig.getQuitUrl();
            String returnUrl = StringUtils.isEmpty(clientConfig.getReturnUrl()) ? client.getReturnUrl() : clientConfig.getReturnUrl();
            if (StringUtils.isEmpty(secretKey) || StringUtils.isEmpty(quitUrl) || StringUtils.isEmpty(returnUrl)) {
                LOG.info("-------必须参数不能为空-------");
                return null;
            }
            JSONObject out = new JSONObject();
            out.put("secretKey", secretKey);
            out.put("quitUrl", quitUrl);
            out.put("returnUrl", returnUrl);
            return out;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
