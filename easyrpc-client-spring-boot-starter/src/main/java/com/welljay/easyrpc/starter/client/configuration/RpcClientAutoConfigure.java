package com.welljay.easyrpc.starter.client.configuration;

import com.welljay.easyrpc.client.RpcClient;
import com.welljay.easyrpc.starter.client.register.RegistryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(RpcClient.class)
public class RpcClientAutoConfigure {


    @Bean
    @ConditionalOnMissingBean
    RegistryBean registry(){
        return new RegistryBean();
    }

}