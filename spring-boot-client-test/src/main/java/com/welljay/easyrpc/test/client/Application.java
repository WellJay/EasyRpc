package com.welljay.easyrpc.test.client;

import com.welljay.easyrpc.starter.client.annotation.EnableEasyRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wenjie
 * @description
 * @date 2019/8/12 22:16
 */
@SpringBootApplication
@EnableEasyRpc
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
