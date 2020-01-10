package com.yufan.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/10 16:53
 * @describe
 */
@Getter
@Setter
public class OrderBean {

    private String goodsName;
    private String orderNo;
    private BigDecimal orderPrice;

}
