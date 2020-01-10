package com.yufan.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/6 16:25
 * @describe 前端系统业务配置表
 */
@Getter
@Setter
public class TestBusinessBean {

    private Integer id;

    @JSONField(name = "client_id")
    private Integer clientId;//客户端标识

    @JSONField(name = "business_code")
    private String businessCode;//业务编码

    @JSONField(name = "quit_url")
    private String quitUrl;//缺省：支付中途退出地址

    @JSONField(name = "quit_url")
    private String returnUrl;//缺省：支付最终结果跳转地址

    public TestBusinessBean() {
        clientId = 1;
        businessCode = "pay_order";
        quitUrl = "http://baidu.com";
        returnUrl = "";
    }
}
