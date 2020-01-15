package com.yufan.utils;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 22:12
 * 功能介绍:
 */
public class Constants {

    /**
     * 商城接口地址
     */
    public static String STORE_INFO_URL = "http://127.0.0.1:8082/store-info/service";

    /**
     * 接口秘钥
     */
    public static String SECRET_KEY = "pay-center-test";

    /**
     * 商城接口业务
     */
    public static String PAY_NOTICE = "pay_notice";//支付成功通知


    /**
     * tb_trade_record
     * record_type 事项 1 订单退押金 2 订单退款 3 提现 4 订单支付
     */
    public static Integer RECORD_TYPE_1 = 1;
    public static Integer RECORD_TYPE_2 = 2;
    public static Integer RECORD_TYPE_3 = 3;
    public static Integer RECORD_TYPE_4 = 4;

    /**
     * tb_trade_record
     * status 状态 0 等待操作 1成功 2 失败  3异常
     */
    public static Integer TRADE_STATUS_0 = 0;
    public static Integer TRADE_STATUS_1 = 1;
    public static Integer TRADE_STATUS_2 = 2;
    public static Integer TRADE_STATUS_3 = 3;

    /**
     * tb_order
     * order_status 0代付款
     */
    public static Integer ORDER_STATUS_0 = 0;


    /**
     * 支付方式 pay_way 1微信 2支付宝
     */
    public static Integer PAY_WAY_1 = 1;
    public static Integer PAY_WAY_2 = 2;
}
