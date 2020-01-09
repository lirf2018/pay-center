package com.yufan.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

/**
 * @功能名称 Sign 检验
 * @作者 lirongfan
 * @时间 2016年9月13日 下午3:55:15
 */
public class VerifySign {

    private static Logger LOG = Logger.getLogger(VerifySign.class);

    /**
     * 签名校验
     *
     * @param json      签名必要参数
     * @param secretKey 客户端秘钥
     * @param clientSign   客户端签名
     * @return
     */
    public static boolean checkSign(JSONObject json, String secretKey, String clientSign) {
        LOG.info("---------json:" + json);
        LOG.info("---------secretKey:" + secretKey);
        LOG.info("---------clientSign:" + clientSign);
        //传过来的sign
        try {
            MyMap map = new MyMap();
            map.put("secretKey", secretKey);

            for (Object k : json.keySet()) {
                Object v = json.get(k);
                //只取data第一层数据
//                if (null != v && !(v instanceof JSONArray) && v.toString().indexOf("{") == -1 && v.toString().indexOf("[") == -1) {
                map.put(k.toString(), v);
//                }
            }
            String sign = MD5.enCodeStandard(HelpCommon.getSign(map) + secretKey);
            if (null != clientSign && clientSign.equals(sign)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public static String getSign(JSONObject json, String secretKey) {
        LOG.info("---------json:" + json);
        LOG.info("---------secretKey:" + secretKey);
        //传过来的sign
        try {
            MyMap map = new MyMap();
            map.put("secretKey", secretKey);

            for (Object k : json.keySet()) {
                Object v = json.get(k);
                //只取data第一层数据
//                if (null != v && !(v instanceof JSONArray) && v.toString().indexOf("{") == -1 && v.toString().indexOf("[") == -1) {
                map.put(k.toString(), v);
//                }
            }
            String sign = MD5.enCodeStandard(HelpCommon.getSign(map) + secretKey);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
