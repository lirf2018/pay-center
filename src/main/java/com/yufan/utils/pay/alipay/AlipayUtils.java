package com.yufan.utils.pay.alipay;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.apache.log4j.Logger;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/7 11:02
 * @describe 阿里支付工具类
 */
public class AlipayUtils {

    private Logger LOG = Logger.getLogger(AlipayUtils.class);

    private static volatile AlipayUtils alipayUtils;

    public static AlipayUtils getInstance() {
        if (null == alipayUtils) {
            synchronized (AlipayUtils.class) {
                if (null == alipayUtils) {
                    alipayUtils = new AlipayUtils();
                }
            }
        }
        return alipayUtils;
    }

    /**
     * 提交订单到支付宝
     *
     * @return
     */
    public JSONObject alipayInf(JSONObject param) {
        LOG.info("-----调用支付宝支付接口参数-----param:" + param);
        JSONObject out = new JSONObject();
        out.put("code", -1);//code: -1 异常; 1提交成功; 2提交失败
        out.put("msg", "");
        try {

            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.serverUrl, AlipayConfig.APPID, AlipayConfig.privateKey, AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipayPublicKey, AlipayConfig.signType);
            AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
            //请求内容
            JSONObject paramData = new JSONObject();
            paramData.put("subject", param.getString("goods_name"));//商品的标题/交易标题/订单标题/订单关键字等。如：大乐透
            paramData.put("out_trade_no", param.getString("partner_trade_no"));//商户网站唯一订单号. 如：70501111111S001111119
            paramData.put("total_amount", param.getString("order_price"));//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]	如：9.00
            paramData.put("quit_url", param.getString("quit_url"));//用户付款中途退出返回商户网站的地址	如： http://www.taobao.com/product/113714.html
            paramData.put("product_code", "QUICK_WAP_WAY");//销售产品码，商家和支付宝签约的产品码	QUICK_WAP_WAY

            String bizContent = paramData.toJSONString();
            request.setBizContent(bizContent);
//            request.setNotifyUrl(AlipayConfig.notify_url);        //异步回调地址（后台）
            request.setReturnUrl(AlipayConfig.return_url);        //同步回调地址（APP）
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                LOG.info("------调用成功--------response:" + JSONObject.toJSONString(response));
                out.put("code", 1);
                String tradeNo = response.getTradeNo();//该交易在支付宝系统中的交易流水号。最长64位。
                String sellerId = response.getSellerId();//收款支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
                out.put("tradeNo", tradeNo);
                out.put("sellerId", sellerId);
                String body = response.getBody();
                out.put("body", body);
            } else {
                LOG.info("------调用失败--------");
                out.put("code", 2);
                String msg = response.getMsg();
                String subCode = response.getSubCode();
                String subMsg = response.getSubMsg();
                out.put("msg", msg);
                out.put("subCode", subCode);
                out.put("subMsg", subMsg);
            }
            return out;
        } catch (Exception e) {
            LOG.error("----toPayInf-----", e);
        }
        return out;
    }


}
