package com.yufan.job.service.impl;

import com.yufan.job.service.IJobService;
import com.yufan.utils.CacheConstant;
import com.yufan.utils.DatetimeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/12 16:28
 * 功能介绍:
 */
@Service
public class JobServiceImpl implements IJobService {

    private Logger LOG = Logger.getLogger(JobServiceImpl.class);

    /**
     * 清除支付缓存
     */
    @Override
    public void clearPayCache() {
        try {
            if (null != CacheConstant.payResultRemoveMap && CacheConstant.payResultRemoveMap.size() > 0) {
                for (Map.Entry<String, String> m : CacheConstant.payResultRemoveMap.entrySet()) {
                    String partnerTradeNo = m.getKey();//支付中心流水号（商品唯一订单号）
                    String passTime = m.getValue();//过期时间
                    String format = "yyyy-MM-dd HH:mm:ss";
                    String now = DatetimeUtil.getNow(format);
                    if (DatetimeUtil.compareDate(now, passTime, format) > 0) {
                        //数据过期(清除缓存)
                        CacheConstant.payResultRemoveMap.remove(partnerTradeNo);
                        CacheConstant.payResultMap.remove(partnerTradeNo);//保存支付最终结果
                        CacheConstant.payTradeRecordMap.remove(partnerTradeNo);//保存交易记录（当处理完毕时需要移除出缓存）
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("---clearPayCache---", e);
        }
    }
}
