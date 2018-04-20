package com.welljay.easyrpc.annotation;

import java.lang.annotation.*;

/**
 * @author wenjie
 * @date 2018/4/20 0020 12:03
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcService {
}
