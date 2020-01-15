package com.yufan.utils.pay.alipay;

/**
 * @author lirf
 * @version 1.0
 * @date 2020/1/7 14:56
 * @describe
 */
public class AlipayConfig {

    // 商户appid
    public static String APPID = "2016092900624822";

    //请求网关地址
    public static String serverUrl = "https://openapi.alipaydev.com/gateway.do";

    //私钥 pkcs8格式的(应用私钥：用支付宝工具生成)
    public static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCK/JM0e/UaTfJtDtG+ga5xUt28yLjWJGWA5lA94Bzx2Tw8YWolY/jNxWBbWsBxCea9aoYQBing5na+p3qy6Yzu2p7ETsn5nNsdtbo0saLkeF1iBILGIk8TqrMis/7WkZ05cYOlT38Pmi+Js1++VzsHMSj35eIDRQc6D0Lj22BzVXxT+pFiiUUUrDmZXUwaBl8cLXH7dYsRMQxnotcV1MQiqsYHrUqONB47Ayw6PXy1ImURPXidaU9w1a1O7CwJwSbCgdDiIzY+PlcLSrw7o46QMOyMYNDwe8NkwPX1P00SncI/NCPEb3hRtpvNFGqS+/Tl+Zg/XoH7kYnG9oxi0xB7AgMBAAECggEAMCwo7WkFZA+eNfSL+CbEipy5JCTCA97rm4i4SSnzflPYD+mHu9vxsYh2xFMpHkTbR82zF7y7KStX/u7XlUljwqndm5bfZHfcbmi9WGQ1XFDntQBxPzFHDT7+BcozSrV/tBhHE7YBCSxcy7JzPerIF88PLxTriEyVm6cMfSNdQ7vbITk9pWg8r0WdADLq/1sU7ivkxhoAVxMh+b87W/wXbQFIO1NczsBpRCqxrSDbS4tUfQe/8DMmgEpQy8GeyXAmVor/jUn20oAgFRDrV0YEJ+4IgMf7SZTOWjrM7DOM+MUWhyvg0pYVCFK+buCBpFIWiyPgNo48zkaKBy3vfHOtwQKBgQDO64osDh1699RSxb+TqS2okgglzegKTFP5+OlmfydvIbkMb8B0MJYiDyFeCG1uW/XD+3ltNE9fjGTK3px1ST6STTSLS9APylIgEzs2dpc6Ac5XU1OcX/KhtZG0rZ17QFN18OTODzdcrg3UnBv9q0C0dBhHadzQ1ypsRBRS8dM5IQKBgQCr9Aax9YRYknRiPHZuRO/4pahv2Qp41YEP/P7fpD2+B03Jop0fXzzQbPp//lMwo4E43dGDPvYiqLvuWbLrFvDqpN58XiviTCDt+1Q1iSqKYfqT4kFUJfb1lYVYcJfx8AUhVIPgQdCHZjehEaEFIdfSq/BberiSvbHSPdUQj3jKGwKBgQCmrVccy7X1kuKGVmJOmI195zwG78kFWpyelGG2PaDPRTPhj7dQIL8e6l8vdsxnhE4BM3WJgQSa6+VZAV64n2p2wRgdSNFtlsT2r2cYJQkGIeRLtYR5R1ST7EVVbDCZ0qQoMgas8dbfohjj9WgauzMg6Q+RnvXPiDLVCTXx5ngF4QKBgQCrBuwh1LDt570aeEjTJH4KMbp2hMmFpdXYsSpQBmvitiEIjFT6/x7dr4b4B1124ymEBhx9DgKCNpG1YEI00XyN5a3kxUWgr1eRXIlFNki5mvpAXKndqGUZnr4iWNrIKJLxs733L37ZLTHnYkFleWPQhnNqiYVLbqGPGzDwZ8McQQKBgGFlx3OcjiThGNZsBx9aOBTVQKc/GLU1aXoKQxTSAWbqX5n/pKjuE7CVzg8x8ktW7D6Wn4SQoeXqgcz6VzhVJ3DAen3ypZFF+Csg8wxcxgJrZ+QNWsKX0q6jy3mUsZKwyT/k/XXHPn/OKnBsLwjEbXF0X6WRGYuY/W8DXRB5dZj9";

    //返回格式
    public static String format = "json";

    //编码
    public static String charset = "UTF-8";

    //支付宝公钥
    public static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkED5YaI1Eo0im70PMZim2XpNRYbTGs46QpEfalfggmsvtxCUEezmfbtWrr8iwSbRxX4QKnK84JQGkwBB1nH62G7a1qieQdKr7lmuCQn9bScHVt0sv7HviOzeocT5Yd03T2+ej0QNvELuKMhA6kuunHQyB3cPagdwqgblRGhXBIbE2XsQhWpfc8jEtlqrjGLxVadZ+heu7tcB6rkeb7imrsMGrqyRWK8RKA3BavUPQ0NNKB5gRAjh8Mxfez4QzVVFwPwtSf5zTzbHy4Wg5JX4Oo21Yedqh4Q9jq8s3ZwhOqZWNkbdCn94NmS0TogTPILqbrG+eEIu7HBvoY4jEHeGJwIDAQAB";

    //RSA2
    public static String signType = "RSA2";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://lirf-shop.51vip.biz/pay-center/notice/alipay";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://lirf-shop.51vip.biz/pay-center/pay/alipayReturnPage";

    //支付中途退出地址缺省情况下
    public static String quitUrl = "http://lirf-shop.51vip.biz/pay-center/pay/interruptAlipay";

}
