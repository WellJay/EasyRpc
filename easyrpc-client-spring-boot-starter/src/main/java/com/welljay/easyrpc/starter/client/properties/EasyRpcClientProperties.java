package com.welljay.easyrpc.starter.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wenjie
 * @description
 * @date 2019/8/12 20:36
 */
@ConfigurationProperties(prefix = "easy-rpc.client")
public class EasyRpcClientProperties {

    public static String IP = "127.0.0.1";

    public static Integer PORT = 9999;

}
