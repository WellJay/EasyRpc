package com.welljay.easyrpc.test.client.controller;

import com.welljay.easyrpc.test.jar.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenjie
 * @description
 * @date 2019/8/12 22:17
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

}
