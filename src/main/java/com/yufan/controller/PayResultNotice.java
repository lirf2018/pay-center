package com.yufan.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.yufan.pojo1.TbTradeRecord;
import com.yufan.service.IPayCenterService;
import com.yufan.utils.CacheConstant;
import com.yufan.utils.Constants;
import com.yufan.utils.DatetimeUtil;
import com.yufan.utils.StoreInfoUtil;
import com.yufan.utils.pay.alipay.AlipayConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/7 13:52
 * @describe 第三方平台支付最终结果通知
 */
@Controller
@RequestMapping("/notice/")
public class PayResultNotice {

    private Logger LOG = Logger.getLogger(PayResultNotice.class);

    @Autowired
    private IPayCenterService iPayCenterService;

    /**
     * http://lirf-shop.51vip.biz:25139/pay-center/notice/alipay
     * 支付宝支付结果最终通知（25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h））
     */
    @RequestMapping("alipay")
    public void payResultNoticeAlipay(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("--payResultNoticeAlipay---支付宝支付最终结果通知------：");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            //商户订单号(支付流水号)
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            //收款支付宝账号对应的支付宝唯一用户号。 以2088开头的纯16位数字
            String sellerId = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"), "UTF-8");
            //本次交易支付的订单金额，单位为人民币（元）
            String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            //付款时间
            String gmtPayment = DatetimeUtil.getNow();
            if (StringUtils.isNotEmpty(request.getParameter("gmt_payment"))) {
                LOG.info("-----gmt_payment--");
                gmtPayment = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8");
            }

            LOG.info("--payResultNoticeAlipay-params:" + params);
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipayPublicKey, AlipayConfig.charset, "RSA2");
            if (verify_result) {//验证成功
                if (tradeStatus.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序
                    doTradeRecord(Constants.PAY_WAY_2, tradeNo, outTradeNo, sellerId, totalAmount, gmtPayment);
                    //注意：
                    //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                } else if (tradeStatus.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序
                    doTradeRecord(Constants.PAY_WAY_2, tradeNo, outTradeNo, sellerId, totalAmount, gmtPayment);
                    //注意：
                    //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                }

                out.println("success");    //请不要修改或删除
            } else {//验证失败
                out.println("fail");
            }
        } catch (Exception e) {
            LOG.error("---", e);
            out.println("fail");
        }

    }

    /**
     * http://127.0.0.1:8087/pay-center/notice/weixin
     * 微信支付结果通知
     */
    @RequestMapping("weixin")
    public void payResultNoticeWX(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("-----微信支付最终结果通知------：");
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
     * 处理交易订单最终结果
     *
     * @param payWay         支付方式 1微信2支付宝
     * @param partnerTradeNo 商户流水号
     * @param tradeNo        支付宝交易号
     * @param sellerId       收款支付宝账号
     * @param totalAmount    收款金额
     * @param payTime        付款时间
     */
    void doTradeRecord(Integer payWay, String tradeNo, String partnerTradeNo, String sellerId, String totalAmount, String payTime) {
        try {
            TbTradeRecord tradeRecord = iPayCenterService.loadTradeRecordByPartnerTradeNo(partnerTradeNo);
            if (null == tradeRecord) {
                LOG.info("--doTradeRecord----tradeRecord查询不存在-------partnerTradeNo:" + partnerTradeNo);
                return;
            }
            int tradeStatus = tradeRecord.getStatus();
            if (tradeStatus != Constants.TRADE_STATUS_0) {
                LOG.info("--doTradeRecord---tradeNo已处理-------" + tradeNo);
                return;
            }
            String orderNo = tradeRecord.getOrderNo();//订单号
            if (Constants.PAY_WAY_2 == payWay) {
                if (StringUtils.isNotEmpty(tradeRecord.getTradeNo()) && tradeRecord.getTradeAcount().equals(sellerId) && tradeRecord.getPrice().compareTo(new BigDecimal(totalAmount)) == 0) {
                    LOG.info("-doTradeRecord--alipay--更新交易最终结果1-----");
                    iPayCenterService.finishTradeRecordStatus(tradeNo, Constants.TRADE_STATUS_1);
                } else {
                    LOG.info("-doTradeRecord--alipay--更新交易最终结果2-----");
                    iPayCenterService.finishTradeRecordStatus(partnerTradeNo, tradeNo, sellerId, Constants.TRADE_STATUS_1);//交易成功
                }
                CacheConstant.payResultMap.put(partnerTradeNo, Constants.TRADE_STATUS_1);
                StoreInfoUtil.getInstance().payResultNotice(tradeNo, payTime, orderNo, payWay);
            }
        } catch (Exception e) {
            LOG.error("---doTradeRecord---处理异常-----");
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
