package com.welljay.easyrpc.starter.server.annotation;

import com.welljay.easyrpc.client.RpcClient;
import com.welljay.easyrpc.starter.server.configuration.EasyRpcServerConfiguration;
import com.welljay.easyrpc.starter.server.properties.EasyRpcServerProperties;
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
@EnableConfigurationProperties(EasyRpcServerProperties.class)
@ConditionalOnClass(RpcClient.class)
@Import({EasyRpcServerConfiguration.class})
public @interface EnableEasyRpcServer {

}