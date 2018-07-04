package com.welljay.easyrpc.starter.client.configuration;

import com.welljay.easyrpc.client.RpcClient;
import com.welljay.easyrpc.starter.client.register.RegistryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(RpcClient.class)
@Import({RegistryBean.class})
public class RpcClientAutoConfigure {

}