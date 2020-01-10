package com.yufan.controller;

import com.alibaba.fastjson.JSONObject;
import com.yufan.bean.OrderBean;
import com.yufan.bean.TestBeanAccount;
import com.yufan.utils.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/7 16:19
 * @describe 测试
 */
@Controller
@RequestMapping("/test/")
public class TestOrderController {

    public static String sysCode = "h5-web";
    public static String businessCode = "pay_order";
    public static String orderNo = "";

    private Logger LOG = Logger.getLogger(TestOrderController.class);

    /**
     * http://lirf-shop.51vip.biz:25139/pay-center/test/recharge
     * 测试充值页面
     *
     * @return
     */
    @RequestMapping("recharge")
    public ModelAndView rechargePage(HttpServletResponse response, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();


        modelAndView.setViewName("rechargePage");
        return modelAndView;
    }


    /**
     * 创建订单
     */
    @RequestMapping("createOrder")
    public void createOrder(HttpServletResponse response, HttpServletRequest request, String payWay, BigDecimal orderPrice) {
        PrintWriter writer;
        try {
            writer = response.getWriter();

            orderNo = CommonMethod.randomStr("");

            OrderBean order = new OrderBean();
            order.setGoodsName("测试商品名称" + System.currentTimeMillis());
            order.setOrderNo(orderNo);
            order.setOrderPrice(orderPrice);
            Constant.orderBean = order;

            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            JSONObject paramData = new JSONObject();
            paramData.put("sys_code", sysCode);
            paramData.put("business_code", businessCode);
            paramData.put("order_no", orderNo);
            paramData.put("pay_way", payWay);
            paramData.put("timestamp", timestamp);

            TestBeanAccount clientSys = new TestBeanAccount();
            String sign = VerifySign.getSign(paramData, clientSys.getSecretKey());
            paramData.put("sign", sign);
            String base64Str = Base64Coder.encodeString(paramData.toJSONString());
            LOG.info("----请求支付参数-----" + paramData);
            JSONObject out = new JSONObject();
            out.put("values", base64Str);
            writer.print(out);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
