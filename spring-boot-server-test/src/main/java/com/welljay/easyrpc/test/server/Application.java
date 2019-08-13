package com.welljay.easyrpc.test.server;

import com.welljay.easyrpc.starter.server.annotation.EnableEasyRpcServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author wenjie
 * @description
 * @date 2019/8/12 22:16
 */
@SpringBootApplication
@EnableEasyRpcServer
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Application.class).web(false).run(args);
    }

}
