package com.yufan.service;

import com.yufan.pojo.TbTradeRecord;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 18:56
 * 功能介绍: 服务层
 */
public interface IPayCenterService {

    /**
     * 保存对象
     *
     * @param obj
     * @return
     */
    public int saveObj(Object obj);

    /**
     * 查询交易记录
     *
     * @param partnerTradeNo 商户订单号(作为调用第三方支付平台的凭证)或交易流水号或退款流水号
     * @return
     */
    public TbTradeRecord loadTradeRecordByPartnerTradeNo(String partnerTradeNo);


    /**
     * 查询交易记录
     *
     * @param orderNo 订单号
     * @return
     */
    public TbTradeRecord loadTradeRecordByOrderNo(String orderNo);


    /**
     * 查询交易记录
     *
     * @param tradeNo 第三方平台返回的交易单号（如微信返回的）
     * @return
     */
    public TbTradeRecord loadTradeRecordByTradeNo(String tradeNo);

    /**
     * 更新交易记录(第三方平台同步响应结果)
     *
     * @param partnerTradeNo 商户号
     * @param tradeNo        第三方平台返回的交易单号（如微信返回的）
     * @param sellerId       收款支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
     * @param remark         失败备注
     * @param tradeStatus    交易状态 0 等待操作 1成功 2 失败  3异常
     * @return
     */
    public int updateTradeRecord(String partnerTradeNo, String tradeNo, String sellerId, String remark, int tradeStatus);

    /**
     * 更新交易记录
     *
     * @param tradeNo     第三方平台返回的交易单号（如微信返回的）
     * @param remark      失败备注
     * @param tradeStatus 交易状态 0 等待操作 1成功 2 失败  3异常
     * @return
     */
    public int finishTradeRecordStatus(String tradeNo, String remark, int tradeStatus);
}
