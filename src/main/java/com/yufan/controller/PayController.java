package com.yufan.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.yufan.bean.TestBeanAccount;
import com.yufan.bean.TestBusinessBean;
import com.yufan.utils.Base64Coder;
import com.yufan.utils.CommonMethod;
import com.yufan.utils.VerifySign;
import com.yufan.utils.pay.alipay.AlipayConfig;
import com.yufan.utils.pay.alipay.AlipayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
     * 签名参数 ：
     * {
     * "sysCode": "",
     * "businessCode": "",
     * "orderNo": "",
     * "timestamp": ""
     * }
     * <p>
     * 参数内容： paramData 如下
     * {
     * "sys_code": "",
     * "business_code": "",
     * "order_no": "",
     * "pay_way": "",微信(weixin) 2 支付宝(alipay)
     * "timestamp": ""，
     * "sign":""
     * }
     * <p>
     * paramDataBase64 = Base64(paramData);
     * 请求方式：get
     * 请求地址：
     * http://lirf-shop.51vip.biz:25139/pay-center/pay/payEnter?base64Str=paramDataBase64
     */
    @RequestMapping("payEnter")
    public void payEnter(HttpServletRequest request, HttpServletResponse response, String base64Str) {
        LOG.info("------------>base64Str:" + base64Str);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();

            String orderNo = "";
            String viewName = "failPage";
            String msg = "";
            String returnSuccessUrl = "";//支付成功跳转地址
            String returnFailUrl = "";//支付失败时跳转地址
            String exceptionalUrl = "";//支付异常或者超时跳转地址
            String quitUrl = "";//用户付款中途退出返回商户网站的地址
            String param = Base64Coder.decodeString(base64Str);
            LOG.info("------------>base64Str明文:" + param);
            if (StringUtils.isEmpty(param)) {
                msg = "缺少必要参数";
                return;
            }
            if (null != param) {
                JSONObject data = JSONObject.parseObject(param);
                String sysCode = data.getString("sys_code");//系统编码(用于查询系统秘钥)
                String businessCode = data.getString("business_code");//系统业务（sysCode和businessCode查询相关结果跳转）
                orderNo = data.getString("order_no");//订单号
                String payWay = data.getString("pay_way");//支付方式
                String timestamp = data.getString("timestamp");//请求时间、13位
                String sign = data.getString("sign");//签名
                if (null == payWay || StringUtils.isEmpty(sysCode) || StringUtils.isEmpty(businessCode) || StringUtils.isEmpty(orderNo)
                        || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(sign)) {
                    LOG.info("-----支付失败,缺少必要参数---");
                    msg = "缺少必要参数";
                    return;
                }

                //查询请求系统相关配置
                TestBeanAccount clientSys = new TestBeanAccount();
                TestBusinessBean clientSysBusiness = new TestBusinessBean();
                String secretKey = clientSys.getSecretKey();
                returnSuccessUrl = clientSysBusiness.getReturnSuccessUrl();//支付成功跳转地址
                returnFailUrl = clientSysBusiness.getReturnFailUrl();//支付失败时跳转地址
                exceptionalUrl = clientSysBusiness.getExceptionalUrl();//支付异常或者超时跳转地址
                quitUrl = clientSysBusiness.getQuitUrl();//用户付款中途退出返回商户网站的地址

                //
                JSONObject json = new JSONObject();
                json.put("sys_code", sysCode);
                json.put("business_code", businessCode);
                json.put("order_no", orderNo);
                json.put("timestamp", timestamp);
                json.put("pay_way", payWay);
                //校验秘钥
                boolean checkSign = VerifySign.checkSign(json, secretKey, sign);
                if (!checkSign) {
                    LOG.info("-----支付失败,签名验证失败---");
                    msg = "签名验证失败";
                    return;
                }
                //创建支付订单(查询和生成订单相关信息)
                String goodsName = "测试商品名称";
                String partnerTradeNo = CommonMethod.randomStr("");
                BigDecimal orderPrice = new BigDecimal("0.01");
                LOG.info("---------goodsName:" + goodsName);
                LOG.info("---------partnerTradeNo:" + partnerTradeNo);
                LOG.info("---------orderPrice:" + orderPrice);

                JSONObject paramData = new JSONObject();
                paramData.put("goods_name", goodsName);//商品名称
                paramData.put("partner_trade_no", partnerTradeNo);//商品支付流水号
                paramData.put("order_price", orderPrice);//订单支付价格

                //开始调用第三方支付平台
                if ("alipay".equals(payWay)) {
                    LOG.info("-----调用支付宝支付接口-----");
                    viewName = "alipay";
                    //订单创建相关参数
                    paramData.put("quit_url", quitUrl);//用户付款中途退出返回商户网站的地址
                    JSONObject result = AlipayUtils.getInstance().alipayInf(paramData);//请求接口
                    LOG.info("--------" + result);
                    int code = result.getInteger("code");//第三方接口提交状态
                    if (code == 1) {
                        String body = result.getString("body");
                        response.setContentType("text/html;charset=" + AlipayConfig.charset);
                        writer.write(body);
                        writer.close();
                    }
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * http://lirf-shop.51vip.biz:25139/pay-center/pay/alipayReturnPage
     * 支付后同步通知页面地址(所有支付交由支付中心处理)
     *
     * @return
     */
    @RequestMapping("alipayReturnPage")
    public ModelAndView alipayReturnPage(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("------支付同步通知页面地址---------");
        ModelAndView modelAndView = new ModelAndView();
        try {
            //获取支付宝GET过来反馈信息
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
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipayPublicKey, AlipayConfig.charset, "RSA2");
            modelAndView.addObject("orderNo", out_trade_no);
            if (verify_result) {
                modelAndView.setViewName("payResult");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
