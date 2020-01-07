package com.yufan.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/7 13:52
 * @describe 第三方平台支付结果通知
 */
@Controller
@RequestMapping("/notice/")
public class PayResultNotice {

    private Logger LOG = Logger.getLogger(PayResultNotice.class);

    /**
     * http://lirf-shop.51vip.biz:25139/pay-center/notice/alipay
     * http://127.0.0.1:8087/pay-center/notice/alipay
     * 微信支付结果通知
     */
    @RequestMapping("alipay")
    public void payResultNoticeAlipay(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("-----支付宝支付结果通知------：");
        String message = null;
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            message = request.getParameter("message");

            if (null == message || "".equals(message)) {
                message = readStreamParameter(request.getInputStream());
            }
        } catch (Exception e) {
            LOG.error("---", e);
        }

    }

    /**
     * http://127.0.0.1:8087/pay-center/notice/weixin
     * 微信支付结果通知
     */
    @RequestMapping("weixin")
    public void payResultNoticeWX(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("-----微信支付结果通知------：");
        String message = null;
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            message = request.getParameter("message");

            if (null == message || "".equals(message)) {
                message = readStreamParameter(request.getInputStream());
            }
        } catch (Exception e) {
            LOG.error("---", e);
        }

    }

    /**
     * 从流中读取数据
     *
     * @param in
     * @return
     */
    public String readStreamParameter(ServletInputStream in) {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }

}
