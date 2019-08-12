package com.welljay.easyrpc.starter.server.configuration;

import com.welljay.easyrpc.server.RpcServer;
import com.welljay.easyrpc.starter.server.properties.EasyRpcServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * @author wenjie
 * @description
 * @date 2019/8/12 21:58
 */
public class EasyRpcServerConfiguration implements CommandLineRunner {

    @Autowired
    private EasyRpcServerProperties easyRpcServerProperties;


    @Override
    public void run(String... strings) {
        System.out.println("EasyRpc启动中...");

        String basePackage = easyRpcServerProperties.getBasePackage();

        try {
            RpcServer.start(basePackage);
        } catch (Exception e) {
            System.err.println("EasyPrc启动失败...");
            e.printStackTrace();
        }

        System.out.println("EasyRpc启动完成...");
    }
}
