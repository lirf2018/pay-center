package com.yufan.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/6 16:10
 * @describe 测试客户端账号(前端系统配置表)
 */
@Getter
@Setter
public class TestBeanAccount {

    @JSONField(name = "client_id")
    private Integer clientId;

    @JSONField(name = "secret_key")
    private String secretKey;//秘钥

    @JSONField(name = "sys_name")
    private String sysName;//秘钥

    @JSONField(name = "sys_code")
    private String sysCode;//秘钥

    public TestBeanAccount() {
        clientId = 1;
        secretKey = "test";
        sysName = "商城系统";
        sysCode = "h5-web";
    }


}
