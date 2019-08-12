package com.welljay.easyrpc.starter.server.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wenjie
 * @description
 * @date 2019/8/12 20:36
 */
@ConfigurationProperties(prefix = "easy-rpc.server")
@Getter
@Setter
public class EasyRpcServerProperties {

    public String basePackage = "";


}
