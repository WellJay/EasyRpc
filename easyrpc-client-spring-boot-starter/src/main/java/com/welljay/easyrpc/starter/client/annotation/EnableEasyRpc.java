package com.welljay.easyrpc.starter.client.annotation;

import com.welljay.easyrpc.client.RpcClient;
import com.welljay.easyrpc.starter.client.properties.EasyRpcClientProperties;
import com.welljay.easyrpc.starter.client.register.RegistryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@EnableConfigurationProperties(EasyRpcClientProperties.class)
@ConditionalOnClass(RpcClient.class)
@Import({RegistryBean.class})
public @interface EnableEasyRpc {

}