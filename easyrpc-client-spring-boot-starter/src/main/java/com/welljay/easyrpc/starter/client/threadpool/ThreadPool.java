package com.welljay.easyrpc.starter.client.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wenjie
 * @description
 * @date 2019/8/13 14:17
 */
public class ThreadPool {

    public static final ExecutorService REQUEST_THREAD_POOL = Executors.newFixedThreadPool(2);

}
