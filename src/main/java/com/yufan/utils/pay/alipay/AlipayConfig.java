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
    public static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCU5VbPg6xTQEGJ5cWp7WlHsLVqJGRetL8Ff+O/FseUDyMs2Z1Qy4qY3HG45XbBDgRCdSq2J6wqRKSv2/3AvM1h7p+W29WIJuDhBVDsPcTCJev7HJaxopdyS3sdwnOzGIqESMSPQiIoX297sjMxaYWCs/WndLj3PYqAjBR4uEv4IFUrWtLt/ff+2xJ4hp0jDo1Bj91n12CEnsAoJFYWRlx9dm6ajjqPzwy6mds3u1gO7bzqN97I7iTuE+WdDMaAbtJk2kr5g2Wk4eJ1EMijiaeGoVlNVLSZP2QrNbVZjp7NU9J1bx3Xo0t83epiVpyvdv4L1zpgd4UD2ylkpAD4yAf3AgMBAAECggEAElUOVCxtGi7uItFUffCKJtZ975ydcv+kCUROwSdH2VXnyczdTqZQ4TM7GT2/WlOkeJ4+bQEDdIzRDgny+zkDvIwJwCQ+7S2EQJBL/1o3kIPkwx0o/KP9K4hZlzdq0WEzJMhk0xKcbAYcDsnI/ct1MbuAgmafo2lDVUJp/tcKG3mHiwTf9wSvCOlCUVuMwGUwCivCbQnoumQxb8Aexi7AgERCuHSmOPIbAQw6lPqYwaigZvgTPl+duNkHyiHI46wBbW1vIGVoNHXdFM3C816DjGLHFxUv3Ky4KvEISAlzrz25rTjOKAjGGcWiWf5osfdV4AAoJqFkjIN0MDGH91V3GQKBgQDIiYIdAkceiux3Rg4h+RaTHHDt7hlvRRBBBufkAIjjLFWxwvtAdYUAFtIrKywWyRK2cadE5XXohGJj+uKVPr5ub6s6/UtKwku3dXQc8T1Qq0syLtQ8WDzDDPOVIlm7bKqwsYoY2n1qy+qlHjZ8iphdVVjugUMzTUrTenqOvDoE3QKBgQC+E4JBvgH5y3zL6MZFS1jCrQr+C1r2qfgmnXV5Fjsm7EcABArxzaDCVO7BLLl1nP7n5kqK5kQbRpykVkHT71SqmT7H+MaZy3S2ENALIA6B2WQQycieA0CYkBYNBN2Fy2goruh/VK7mShoRZ8MEwJTmmGpuTT7xG/4uhZEHGdUY4wKBgQC+7EQHUFLbnlJRoE32REsc1lgh/q0ShcNa1bNTHLs25c/UhRlmnjx5AEEqPlkkfF0ne/9PldLzA48VNxggFXOCeyif6eWUEHW7/cQFhhvlQKA/79u6dButbBhAZUrfKS89OFqNHP+I/QmyGJixa9ZyDb60nDaZkj3qJ9KlMElgtQKBgHfZPzmKwB4sQ10jQdkxgB8IEzVKHaoIVBKtGAQok1Wgh8gnxGrg+CtweDbPp4TUYBoCqB/avzjflic8yDcE4kLLa72VTExu8AFYqoVJSjxvwVugj+GqNVG9EFpXJgKV1fBJEQowcDN+p98vqNTXRUv88KbqB/TpbKUSzPZIQIh3AoGBAJJjqaYnPcelnMbl31mC8ZoeyA1ZTKLR/91T1MaUrE39GObG/v6TilXm/AVMuuXePX8+tt7WX1hs3ljlSal+vTksyKy/vbzlMDdEpFlEzzkme9UPm741i9EctEj42vqvFt1nO2XnZD/323qnyrwUQkFozpYDQKSX8YFz8tcwE8jv";

    //返回格式
    public static String format = "json";

    //编码
    public static String charset = "UTF-8";

    //支付宝公钥
    public static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkED5YaI1Eo0im70PMZim2XpNRYbTGs46QpEfalfggmsvtxCUEezmfbtWrr8iwSbRxX4QKnK84JQGkwBB1nH62G7a1qieQdKr7lmuCQn9bScHVt0sv7HviOzeocT5Yd03T2+ej0QNvELuKMhA6kuunHQyB3cPagdwqgblRGhXBIbE2XsQhWpfc8jEtlqrjGLxVadZ+heu7tcB6rkeb7imrsMGrqyRWK8RKA3BavUPQ0NNKB5gRAjh8Mxfez4QzVVFwPwtSf5zTzbHy4Wg5JX4Oo21Yedqh4Q9jq8s3ZwhOqZWNkbdCn94NmS0TogTPILqbrG+eEIu7HBvoY4jEHeGJwIDAQAB";

    //RSA2
    public static String signType = "RSA2";

}
