package com.yufan.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/10 22:44
 * 功能介绍:
 */
@Getter
@Setter
public class TradeRecord {

    private String returnUrl;//支付结果通知地址
    private String partnerTradeNo;//支付中心流水号/交易号
}
