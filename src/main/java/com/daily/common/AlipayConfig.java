package com.daily.common;

/**
 * Created by xuqipei on 17-7-25.
 */
public class AlipayConfig {

    public static final String HOSTNAME = "http://119.29.118.31:8089";
    // 商户appid
    public static String APPID = "2016080500174105";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzjCc2NS0W/0LiwiUxT8lQTdHGXaIT9VQ6IwHiscyzLJqEU6TXtIgfDjjd7GMnRHiOfmk8TV7XoNBG3e+paD6eDRz8dFZsYFL9CXRqLQSLWrv1bZxvKgBXl2Ap90LodW5YTD4mMECUClEGsuSyLlwC5MmtpiBf0MCazK2rJJcjTX+6MWuIH3jK/1De5wDzbPgaYqEtsLlk6oFjrMXJybmiiCjMCGgxkfPZ4aLZcO3PWEHU48u6CZKngW5Nf/kux4p0XG5goiJOdr6PdU56HQS/w9dGp1gUD39LjIalHyRE4sUUfK7PCJSpn3Xvp5lF9IVqPrhx2Qt/VeiHJGW1LQ+7AgMBAAECggEAEiRROfr5iV9VhNAVLfxf3Qtf7Ok9HUMmtZEf+aX5hwk0u7Yv69PEBU9Mk6/0/NzASbWgMIWo1aTcJqz6MGXmHdY5XZDrwpuhBwxThP7zfJk4cL4YlmrqCe+zucnQKujis21Qwk8R12OLeF/PF+nzRh+T+UXV85iGPZ381lQmKVzZ+9BB5Gfn54IQl/kdaKaa6pQ5UppZacMwSsnG6JVlAkYXC7uqPa9B9Le3CoZ7DXF/UpJyXh1lpVd7VKgPIELarA0gFGm7WTI2xwTU2nUf/KSesFmQOUHXuARB05syAfCIwukTDATbIV95eRLpQM9xdxXg+TX2vz0reLQF8QIKQQKBgQDaT1jXt9jgbwHeS10JUQI5eIcMYb6Q2kfZUfgvFLuXh8Fm9hqcxbvgdJ7QFZQf/mNMycBHHAyuH1+KvI1lYHHBHzw7FM4H46YjxoogKpMP1KEMJjCr6h0tyAYk7Sul0RAol3yq1w3pxKYvCRsJXMfBZX7n/mPfmlBzrmOKrvFosQKBgQDSi56ZqgLaQcfI8Gi+lTHuwW/Nv4KqRuGrKavdDX7NJRgzCT2We8h7T9tNxM8OxC2aCZnu1JVfhCVD0mHYrxlxO3bKoBzKltNjnAMTvaP+5shbcSyAmiHomUCR0M7cMgENZOIYVp7E8RbbaEYc31QcJALYwlz4+QzGMK74XYaaKwKBgHAI4n4qUnmzXlEXehKK6UpXKW9YRjUOYGsN3wFCcZctSzqsuDGWZHmnP81oFZFpusowi6WypmtBnIWViDbrjNVgU0e6wJVZ5excOoyRbNFl/JRAkR/Y0Mqq6URQFuNM7C74tzjxjqlWizZ+RMrEO5trm/oxGk4pYnpLl7gXWbqxAoGABDzJIWCZlBj002d7RDKugZalEGgFUjYA/rfk/Jrz7V8MwKIz+h5dp5Ww3TzTygFKxanhzju7EeQP9/B5Bapoga3nsyZLzgwBDyUprrEHZu7wI18AvUnc2tiSwxyHvUjIWqOoJUNBCMetZRcRKi2YfMSAxioLQvuINUjvjJIdTmcCgYEAybDvPTQBaEicfFlmo4b72oV8C3XhVQVGAm0xvU/hErMctx8kXOWO6itWHcGmIB31yI8KOTb6NiQM2rJWRTQYPLMQPFTMDzSM2lTItp/hi1DH5i7OKYqrrmAwyju1nOFkQNeoR2oWVd9tuC3nxesU9fo5BxI05UbTkor8nuuYr2c=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = HOSTNAME + "/order/notify_url";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = HOSTNAME + "/order/return_url";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnUHAfN6KG+2c2YOaod3tQDL21swbYKt9U5j5+ziEvHfODQYs2qzWBk3qUOnYJRq1joFfo4cg8Ut2qL/QkJ3jYPCqTn8JJcMYogAUIQTxgOipNbn9Zuc/qXLwBUPQ1cUWrCthwYaEN6pnoTb9IWEck0Y8j46SQOSeLnuYx9gRiHdBhhBmiJlLW6yavAgjWsc4d34DlfAN/L3JHyAjCxr/uLrkjaTmjuLQZge2Bn/qw4FNkZfcShemOd9aGlB/k+TfCQ4jqtjPBNnmyKSi/bh/QCrTDx0miX7XtytJ228PBumGMEiP5A4eDy+6URk0kDj4RCT0FcZSzf39TcavJDq9sQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
