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

    @JSONField(name = "return_success_url")
    private String returnSuccessUrl;//支付成功完成后的跳转地址

    @JSONField(name = "return_fail_url")
    private String returnFailUrl;//支付失败完成后的跳转地址

    @JSONField(name = "exceptional_url")
    private String exceptionalUrl;//支付异常完成后的跳转地址

    @JSONField(name = "quit_url")
    private String quitUrl;//用户付款中途退出返回商户网站的地址

    public TestBusinessBean() {
        clientId = 1;
        businessCode = "pay_order";
        returnSuccessUrl = "http://127.0.0.1:8080/h5-web/pay/success";
        returnFailUrl = "http://127.0.0.1:8080/h5-web/pay/fail";
        exceptionalUrl = "http://127.0.0.1:8080/h5-web/pay/timeOut";
        quitUrl = "http://baidu.com";
    }
}
