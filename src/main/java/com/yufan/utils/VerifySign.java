package com.yufan.utils;

import com.alibaba.fastjson.JSONObject;
import com.yufan.common.bean.ReceiveJsonBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @功能名称 Sign 检验
 * @作者 lirongfan
 * @时间 2016年9月13日 下午3:55:15
 */
public class VerifySign {

    /**
     * 签名校验
     * @return
     */
    public static boolean checkSign(JSONObject json, String secretKey) {
        //传过来的sign
        try {
            String getSign = bean.getSign();
            MyMap map = new MyMap();
            map.put("appsecret", appsecret);
            map.put("timestamp", bean.getTimestamp());

            for (Object k : json.keySet()) {
                Object v = json.get(k);
                //只取data第一层数据
//                if (null != v && !(v instanceof JSONArray) && v.toString().indexOf("{") == -1 && v.toString().indexOf("[") == -1) {
                map.put(k.toString(), v);
//                }
            }
            String sign = MD5.enCodeStandard(HelpCommon.getSign(map) + secretKey);
            if (null != getSign && getSign.equals(sign)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("name", "496");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("b", "sad");
        obj.put("listr", map);

        List<Map<String, Object>> listmap = new ArrayList<>();
        Map<String, Object> lmaps1 = new HashMap<String, Object>();
        lmaps1.put("dd", "枯叶df");
        Map<String, Object> lmaps2 = new HashMap<String, Object>();
        lmaps2.put("dd", "枯叶df");
        listmap.add(lmaps1);
        listmap.add(lmaps2);

        obj.put("lmaps", listmap);

        List<String> listString = new ArrayList<>();
        listString.add("工枯叶");
        listString.add("工枯叶1");
        obj.put("listString", listString);
        System.out.println(obj);

        ReceiveJsonBean bean = new ReceiveJsonBean();
        bean.setData(obj);
        checkSign(bean, "d");

    }
}
