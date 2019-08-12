package com.welljay.easyrpc.starter.client.annotation;

import com.welljay.easyrpc.client.RpcClient;
import com.welljay.easyrpc.starter.client.properties.EasyRpcProperties;
import com.welljay.easyrpc.starter.client.register.RegistryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(EasyRpcProperties.class)
@ConditionalOnClass(RpcClient.class)
@Import({RegistryBean.class})
public interface EnableEasyRpc {

}