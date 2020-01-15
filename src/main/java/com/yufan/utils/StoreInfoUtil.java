package com.yufan.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/15 17:21
 * 功能介绍: 商城接口
 */
public class StoreInfoUtil {

    private static Logger LOG = Logger.getLogger(StoreInfoUtil.class);
    private static volatile StoreInfoUtil storeInfoUtil;

    public static StoreInfoUtil getInstance() {
        if (null == storeInfoUtil) {
            synchronized (StoreInfoUtil.class) {
                if (null == storeInfoUtil) {
                    storeInfoUtil = new StoreInfoUtil();
                }
            }
        }
        return storeInfoUtil;
    }


    /**
     * 支付结果通知到商城接口
     *
     * @param tradeNo 第三方平台交易号
     * @param payTime 支付成功时间
     * @param orderNo 订单号
     * @param payWay  支付方式
     */
    public void payResultNotice(String tradeNo, String payTime, String orderNo, Integer payWay) {
        try {
            JSONObject data = new JSONObject();
            data.put("order_no", orderNo);
            data.put("pay_way", payWay);
            data.put("pay_time", payTime);
            data.put("pay_code", tradeNo);
            JSONObject result = CommonMethod.infoResult(data, Constants.PAY_NOTICE);
            if (null != result) {
                LOG.info("---result----" + result);
            }
        } catch (Exception e) {
            LOG.error("-------", e);
        }

    }

}
