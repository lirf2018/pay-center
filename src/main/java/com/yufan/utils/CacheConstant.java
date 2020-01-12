package com.yufan.utils;

import com.yufan.pojo1.TbClient;
import com.yufan.pojo1.TbClientConfig;
import com.yufan.pojo1.TbTradeRecord;

import java.util.*;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/10 16:13
 * @describe
 */
public class CacheConstant {

    /**
     * 客户端app信息表
     */
    public static List<TbClient> clients = new ArrayList<>();


    /**
     * 客户端app业务配置表
     */
    public static List<TbClientConfig> clientConfigs = new ArrayList<>();

    /**
     * 清除支付结果数据 key = partnerTradeNo商户订单号(支付流水号) value = 过期时间（当处理完毕时需要移除出缓存）
     */
    public static Map<String, String> payReusltRemoveMap = new HashMap<>();
    public static Integer addPassSeconds = 50;//增加过期时间/s
    public static Integer addPassDay = 3;//增加过期时间/d

    /**
     * 保存支付最终结果 key = partnerTradeNo商户订单号(支付流水号) value = status
     */
    public static Map<String, Integer> payReusltMap = new HashMap<>();

    /**
     * 保存交易记录（当处理完毕时需要移除出缓存）
     *
     * @param args
     */
    public static Map<String, TbTradeRecord> payTradeRecordMap = new HashMap<>();

}
