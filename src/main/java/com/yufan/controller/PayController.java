package com.yufan.controller;

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
     * http://127.0.0.1:8087/pay-center/pay/payEnter
     *
     * @param request
     * @param response
     * @param payType
     * @param rechargeValue
     */
    @RequestMapping("payEnter")
    public ModelAndView payEnter(HttpServletRequest request, HttpServletResponse response, Integer payType, String rechargeValue) {
        ModelAndView modelAndView = new ModelAndView();
        LOG.info("------------>payType:" + payType + "  rechargeValue:" + rechargeValue);
        modelAndView.addObject("payType", payType);
        modelAndView.addObject("rechargeValue", rechargeValue);

        //支付成功后的跳转地址
        String paySuccessUrl = "http://127.0.0.1:8080/h5-web/recharge/orderDetail?orderNo="+"DD";

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
