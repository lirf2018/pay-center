package com.yufan.controller;

import com.alibaba.fastjson.JSONObject;
import com.yufan.bean.TestBeanAccount;
import com.yufan.bean.TestBusinessBean;
import com.yufan.utils.Base64Coder;
import com.yufan.utils.VerifySign;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/2 18:02
 * @describe 支付入口
 */
@Controller
@RequestMapping("/pay/")
public class PayController {

    private Logger LOG = Logger.getLogger(PayController.class);


    /**
     * 支付入口
     * http://127.0.0.1:8087/pay-center/pay/payEnter
     * 接口请求说明
     * appsecret
     * 下面是参数   sid(应用ID)   appkey(接口密钥)  timestamp(13位毫秒级long值) sign数字签名
     * 数字签名如下(备注：放到map中的有系统参数sid,appkey,timestamp,和data中，如果包括数对象和数组，则不用加入签名中)
     * 1.把post请求的各参数先放到一个键值对集合里 ,如 req = array( "key2"=>"value2", "key1"=>"value1" );
     * 2.先对req的排序,以键的字母从小到大排序序排.得到req = array( "key1"=>"value1", "key2"=>"value2" );
     * 3.然后遍历req,以如下格式把req里的所有键值对给拼接成一个字符串
     * 格式:"keyLen-key:valueLen-value",多个键值对以分号分割,其中,key的字符串
     * 长度应固定为2位, 1要写成01.value的长度是4位 1写成0001.
     * 4.对req格式化求得字符串为 "04-key1:0006-value1;04-key2:0006-value2"
     * 5.把求得的字符串和appsecret进行拼接然后求其md5值(其中的appsecret第三方不需要传,由接口方提供给第三方,接口方通过传过来的系统参数查询对应分配的appsecret)
     * sign=md5(packData(req)+appsecret)=md5("04-key1:0006-value1;04-key2:0006-value212345")=2b65a10ed4154187f82880cfd841c09f
     * <p>
     */
    @RequestMapping("payEnter")
    public ModelAndView payEnter(HttpServletRequest request, HttpServletResponse response, String base64Str) {
        ModelAndView modelAndView = new ModelAndView();
        LOG.info("------------>base64Str:" + base64Str);

        String param = Base64Coder.decodeString(base64Str);
        if (null != param) {
            JSONObject data = JSONObject.parseObject(param);
            String sysCode = data.getString("sys_code");//系统编码(用于查询系统秘钥)
            String businessCode = data.getString("business_code");//系统业务（sysCode和businessCode查询相关结果跳转）
            String orderNo = data.getString("order_no");//订单号
            String timestamp = data.getString("timestamp");//请求时间、13位
            String sign = data.getString("sign");//签名

            //查询请求系统相关配置
            TestBeanAccount clientSys = new TestBeanAccount();
            TestBusinessBean clientSysBusiness = new TestBusinessBean();
            String secretKey = clientSys.getSecretKey();
            String returnSuccessUrl = clientSysBusiness.getReturnSuccessUrl();
            String returnFailUrl = clientSysBusiness.getReturnFailUrl();
            String exceptionalUrl = clientSysBusiness.getExceptionalUrl();//异常或者超时跳转地址

            //
            boolean checkSign = VerifySign.checkSign(data, secretKey)


        }

        modelAndView.addObject("returnSuccessUrl", returnSuccessUrl);
        modelAndView.addObject("returnFailUrl", returnFailUrl);
        modelAndView.addObject("exceptionalUrl", exceptionalUrl);

        //支付成功后的跳转地址
//        String paySuccessUrl = "http://127.0.0.1:8080/h5-web/recharge/orderDetail?orderNo=" + "DD";

        modelAndView.setViewName("payResult");
        return modelAndView;
    }


    /**
     * 获取支付结果
     */
    @RequestMapping("ajaxPayResult")
    public void ajaxPayResult(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer;
        try {
            LOG.info("-------获取支付结果---------");
            writer = response.getWriter();
            writer.print(testResultValue);
            writer.close();
        } catch (Exception e) {
            LOG.info("----->ajaxPayResult异常");
        }
    }


    //-----------------------------测试--------------------------

    private static int testResultValue = 0;//0失败  1成功

    @RequestMapping("setTestResult")
    public void setTestResult(HttpServletRequest request, HttpServletResponse response, Integer value) {
        PrintWriter writer;
        try {
            writer = response.getWriter();
            testResultValue = value;
            writer.print(testResultValue);
            writer.close();
        } catch (Exception e) {
            LOG.info("----->setTestResult异常");
        }
    }

    @RequestMapping("getTestResult")
    public void getTestResult(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.print(testResultValue);
            writer.close();
        } catch (Exception e) {
            LOG.info("----->getTestResult异常");
        }
    }

}
