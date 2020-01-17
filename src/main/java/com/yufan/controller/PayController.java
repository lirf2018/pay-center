package com.yufan.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.yufan.dao.db2.IOrderDao;
import com.yufan.pojo1.TbClient;
import com.yufan.pojo1.TbTradeRecord;
import com.yufan.service.IPayCenterService;
import com.yufan.utils.*;
import com.yufan.utils.pay.alipay.AlipayConfig;
import com.yufan.utils.pay.alipay.AlipayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

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

    @Autowired
    private IPayCenterService iPayCenterService;

    @Autowired
    private IOrderDao iOrderDao;

    /**
     * 支付入口
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
     * "sid": "",
     * "business_code": "",
     * "order_no": "",
     * "pay_way": "",1微信(weixin) 2 支付宝(alipay)
     * "record_type": "",
     * "timestamp": ""
     * }
     * <p>
     * 参数内容： paramData 如下
     * {
     * "sid": "",
     * "business_code": "",
     * "order_no": "",
     * "pay_way": "",1微信(weixin) 2 支付宝(alipay)
     * "record_type": ""，
     * "timestamp": ""，
     * "sign":""
     * }
     * <p>
     * paramDataBase64 = Base64(paramData);
     * 请求方式：get
     * 请求地址：
     * http://lirf-shop.51vip.biz/pay-center/pay/payEnter?base64Str=paramDataBase64
     */
    @RequestMapping("payEnter")
    public void payEnter(HttpServletRequest request, HttpServletResponse response, String base64Str) {
        LOG.info("----payEnter-------->base64Str:" + base64Str);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();

            String param = Base64Coder.decodeString(base64Str);
            LOG.info("---payEnter--------->base64Str明文:" + param);
            if (StringUtils.isEmpty(param)) {
                LOG.info("----payEnter------->缺少必要参数");
                response.sendRedirect(request.getContextPath() + "/pay/page505");
                return;
            }
            if (null != param) {
                JSONObject data = JSONObject.parseObject(param);
                String clientCode = data.getString("sid");//系统编码(用于查询系统秘钥)
                String businessCode = data.getString("business_code");//系统业务（sysCode和businessCode查询相关结果跳转）
                String orderNo = data.getString("order_no");//订单号
                Integer payWay = data.getInteger("pay_way");//支付方式 1微信(weixin) 2 支付宝(alipay)
                Integer recordType = data.getInteger("record_type");//事项 1 订单退押金 2 订单退款 3 提现 4 订单支付
                String timestamp = data.getString("timestamp");//请求时间、10位
                String sign = data.getString("sign");//签名
                if (null == payWay || StringUtils.isEmpty(clientCode) || StringUtils.isEmpty(businessCode) || StringUtils.isEmpty(orderNo)
                        || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(sign) || null == recordType) {
                    LOG.info("--payEnter---支付失败,缺少必要参数---");
                    response.sendRedirect(request.getContextPath() + "/pay/page505");
                    return;
                }
                /**
                 * 查询数据库中得到请求相关配置信息sysCode和businessCode
                 */
                JSONObject clientJson = CommonCacheMethod.getClientParam(clientCode, businessCode);
                if (null == clientJson) {
                    LOG.info("--payEnter---clientJson为空-----");
                    response.sendRedirect(request.getContextPath() + "/pay/page505");
                    return;
                }
                String clientId = clientJson.getString("clientId");
                String secretKey = clientJson.getString("secretKey");
                String quitUrl = clientJson.getString("quitUrl");//支付中途退出地址
                String returnUrl = clientJson.getString("returnUrl");//支付结果通知地址

                //支付中心验证签名
                JSONObject json = new JSONObject();
                json.put("business_code", businessCode);
                json.put("order_no", orderNo);
                json.put("pay_way", payWay);
                json.put("record_type", recordType);
                //校验秘钥
                boolean checkSign = VerifySign.checkSign(json, clientCode, secretKey, timestamp, sign);
                if (!checkSign) {
                    LOG.info("--payEnter---支付失败,支付中心签名验证失败---");
                    response.sendRedirect(request.getContextPath() + "/pay/page505");
                    return;
                }

                /**
                 * 查询支付订单信息
                 */
                List<Map<String, Object>> orderList = iOrderDao.queryOrderListMap(orderNo);
                if (null == orderList || orderList.size() == 0) {
                    LOG.info("---payEnter----待付款订单不存在------");
                    response.sendRedirect(request.getContextPath() + "/pay/page505");
                    return;
                }
                String goodsName = "";//最长256
                BigDecimal orderPrice = new BigDecimal(0);
                for (int i = 0; i < orderList.size(); i++) {
                    if (i == 0) {
                        orderPrice = new BigDecimal(orderList.get(i).get("order_price").toString());
                        if (orderPrice.compareTo(new BigDecimal(0)) <= 0) {
                            LOG.info("---payEnter----订单orderPrice不能为0------orderPrice: " + orderPrice);
                            response.sendRedirect(request.getContextPath() + "/pay/page505");
                            return;
                        }
                    }
                    if (i == orderList.size() - 1) {
                        goodsName = goodsName + orderList.get(i).get("goods_name");
                    } else {
                        goodsName = goodsName + orderList.get(i).get("goods_name") + ";";
                    }
                }
                if (goodsName.length() > 256) {
                    goodsName = goodsName.substring(0, 255);
                }

                //生成支付中心流水号（商品唯一订单号）
                String partnerTradeNo = CommonMethod.randomStr("");
                /**
                 * 保存交易记录
                 */
                String tradeNo = "";
                TbTradeRecord tradeRecord = new TbTradeRecord();
                tradeRecord.setOrderNo(orderNo);
                tradeRecord.setTradeNo(tradeNo);
                tradeRecord.setPartnerTradeNo(partnerTradeNo);
                tradeRecord.setRecordType(recordType.byteValue());
                tradeRecord.setStatus(Constants.TRADE_STATUS_0.byteValue());//状态 0 等待操作 1成功 2 失败  3异常
                tradeRecord.setRemark("");
                Timestamp time = new Timestamp(new Date().getTime());
                tradeRecord.setCreateTime(time);
                tradeRecord.setPrice(orderPrice);
                tradeRecord.setPayWay(payWay.byteValue());
                tradeRecord.setTradeAcount("");
                tradeRecord.setSubmitTime(time);
                tradeRecord.setReturnUrl(returnUrl);
                tradeRecord.setClientId(Integer.parseInt(clientId));
                //保存交易记录
                iPayCenterService.saveObj(tradeRecord);
                CacheConstant.payResultMap.put(partnerTradeNo, Constants.TRADE_STATUS_0);//保存支付结果缓存//定时处理移除
                String passTime = DatetimeUtil.convertDateToStr(DatetimeUtil.addDays(new Date(), CacheConstant.addPassDay));
                CacheConstant.payResultRemoveMap.put(partnerTradeNo, passTime);//用于记录移除缓存标识

                LOG.info("---payEnter------orderNo:" + orderNo);
                LOG.info("--payEnter-------goodsName:" + goodsName);
                LOG.info("---payEnter------orderPrice:" + orderPrice);
                LOG.info("----payEnter-----partnerTradeNo:" + partnerTradeNo);

                //组装第三方支付接口必须参数
                JSONObject paramData = new JSONObject();
                paramData.put("goods_name", goodsName);//商品名称
                paramData.put("partner_trade_no", partnerTradeNo);//商品支付流水号
                paramData.put("order_price", orderPrice);//订单支付价格

                //开始调用第三方支付平台
                if (payWay == 2) {
                    LOG.info("-----调用支付宝支付接口-----");
                    //订单创建相关参数
                    paramData.put("quit_url", quitUrl);//用户付款中途退出返回商户网站的地址
                    JSONObject result = AlipayUtils.getInstance().alipayInf(paramData);//请求接口
                    LOG.info("--------" + result);
                    int code = result.getInteger("code");//第三方接口提交状态
                    int tradeStatus = Constants.TRADE_STATUS_0;//状态 0 等待操作 1成功 2 失败  3异常
                    String sellerId = "";
                    String remark = "";
                    if (code == 1) {
                        //更新交易记录
                        tradeStatus = Constants.TRADE_STATUS_0;
                        sellerId = result.getString("sellerId") == null ? "" : result.getString("sellerId");
                        tradeNo = result.getString("tradeNo") == null ? "" : result.getString("tradeNo");
                        //缓存
                        tradeRecord.setTradeAcount(sellerId);
                        tradeRecord.setTradeNo(tradeNo);
                        CacheConstant.payTradeRecordMap.put(partnerTradeNo, tradeRecord);//定时处理移除
                        //
                        iPayCenterService.updateTradeRecord(partnerTradeNo, tradeNo, sellerId, remark, tradeStatus);
                        String body = result.getString("body");
                        response.setContentType("text/html;charset=" + AlipayConfig.charset);
                        writer.write(body);
                        writer.close();
                        return;
                    } else if (code == 2) {
                        LOG.info("---payEnter---支付失败-------");
                        remark = result.getString("msg");
                        tradeStatus = Constants.TRADE_STATUS_2;
                        iPayCenterService.updateTradeRecord(partnerTradeNo, tradeNo, sellerId, remark, tradeStatus);
                    } else {
                        tradeStatus = Constants.TRADE_STATUS_3;
                    }
                    iPayCenterService.updateTradeRecord(partnerTradeNo, tradeNo, sellerId, remark, tradeStatus);
                } else {
                    LOG.info("------未知支付方式-------");
                }
            }
            response.sendRedirect(request.getContextPath() + "/pay/page505");
            return;
        } catch (Exception e) {
            LOG.error("--异常---", e);
            try {
                response.sendRedirect(request.getContextPath() + "/pay/page505");
            } catch (Exception e1) {
                LOG.error("--异常2---", e1);
            }

        }
    }

    /**
     * 支付宝
     * http://lirf-shop.51vip.biz/pay-center/pay/alipayReturnPage
     * alipay支付后同步通知页面地址(所有支付交由支付中心处理)
     *
     * @return
     */
    @RequestMapping("alipayReturnPage")
    public ModelAndView alipayReturnPage(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("------支付宝同步通知页面地址---------");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("505");
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
            //商户订单号(交易流水号)
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //收款支付宝账号对应的支付宝唯一用户号。 以2088开头的纯16位数字
            String sellerId = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"), "UTF-8");
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipayPublicKey, AlipayConfig.charset, "RSA2");
            LOG.info("-----alipayReturnPage:" + params);
            String returnUrl = "";
            if (StringUtils.isNotEmpty(outTradeNo)) {
                //根据交易流水号查询交易记录
                TbTradeRecord tradeRecord = iPayCenterService.loadTradeRecordByPartnerTradeNo(outTradeNo);
                //生成特定base64密文
                returnUrl = tradeRecord == null ? "" : tradeRecord.getReturnUrl() + "?orderNo=" + tradeRecord.getOrderNo();
                //更新交易记录
                if (0 == CacheConstant.payResultMap.get(outTradeNo)) {
                    LOG.info("----alipayReturnPage--同步响应更新--");
                    iPayCenterService.updateTradeRecord(outTradeNo, tradeNo, sellerId, "", Constants.TRADE_STATUS_0);
                }
            }
            if (verify_result) {
                modelAndView.setViewName("payResult");
            } else {
                LOG.info("--验证---verify_result---失败----");
            }
            modelAndView.addObject("returnUrl", returnUrl);
            modelAndView.addObject("partnerTradeNo", outTradeNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * http://lirf-shop.51vip.biz/pay-center/pay/interruptAlipay
     * 中途退出(alipay)页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("interruptAlipay")
    public ModelAndView interruptAlipay(HttpServletRequest request, HttpServletResponse response) {
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
            LOG.info("------interruptDefault-------" + params);
            //商户订单号(交易流水号)
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("interruptDefault");
        return modelAndView;
    }

    /**
     * http://lirf-shop.51vip.biz/pay-center/pay/interruptDefault
     * 中途退出缺省页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("interruptDefault")
    public ModelAndView interruptDefault(HttpServletRequest request, HttpServletResponse response) {
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
            LOG.info("------interruptDefault-------" + params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("interruptDefault");
        return modelAndView;
    }

    /**
     * http://lirf-shop.51vip.biz/pay-center/pay/ajaxPayResult
     * 获取支付结果
     * result 状态 0 等待操作 1成功 2 失败  3异常
     * partnerTradeNo 商户订单号
     */
    @RequestMapping("ajaxPayResult")
    public void ajaxPayResult(HttpServletRequest request, HttpServletResponse response, String partnerTradeNo) {
        PrintWriter writer;
        try {
            LOG.info("-------获取支付结果---------partnerTradeNo:" + partnerTradeNo);
            writer = response.getWriter();
            int result = Constants.TRADE_STATUS_0;//状态 0 等待操作 1成功 2 失败  3异常
            if (StringUtils.isNotEmpty(partnerTradeNo)) {
                result = CacheConstant.payResultMap.get(partnerTradeNo) == null ? Constants.TRADE_STATUS_0 : CacheConstant.payResultMap.get(partnerTradeNo);
            }
            writer.print(result);
            writer.close();
        } catch (Exception e) {
            LOG.info("----->ajaxPayResult异常");
        }
    }

    /**
     * 支付异常跳转页面
     * http://lirf-shop.51vip.biz/pay-center/pay/page505
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("page505")
    public ModelAndView page505(HttpServletRequest request, HttpServletResponse response, String msg) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("msg", msg == null ? "" : msg);
            modelAndView.setViewName("505");
        } catch (Exception e) {
            LOG.info("----->ajaxPayResult异常");
        }
        return modelAndView;
    }

}

