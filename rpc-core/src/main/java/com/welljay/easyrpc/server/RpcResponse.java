package com.welljay.easyrpc.server;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wenjie
 * @date 2018/4/20 0020 11:46
 */
@Getter
@Setter
public class RpcResponse implements Serializable {

    private String requestId;

    private Object result;

}
