package com.welljay.easyrpc.server;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wenjie
 * @date 2018/4/20 0020 11:43
 */
@Getter
@Setter
public class RpcRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    private String requestId;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;
}
