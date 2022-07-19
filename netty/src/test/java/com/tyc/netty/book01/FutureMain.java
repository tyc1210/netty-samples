package com.tyc.netty.book01;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-07-18 14:45:51
 */
@Slf4j
public class FutureMain {
    public static void main(String[] args) {
        List<RequestFuture> reqs = new ArrayList<>();
        ExecutorService excutors = Executors.newFixedThreadPool(8);
        for (long i = 0; i < 100; i++) {
            RequestFuture requestFuture = new RequestFuture();
            requestFuture.setId(i);
            requestFuture.setRequest("hello server");
//            requestFuture.setGetThread(Thread.currentThread());
            // 缓存未响应的请求
            RequestFuture.addFuture(requestFuture);
            reqs.add(requestFuture);
            // 模拟发送请求
            excutors.execute(()->{
                sendMsg(requestFuture);
            });
            // 模拟线程获取对应请求
            SubThread subThread = new SubThread(requestFuture);
            subThread.start();
        }

        for (RequestFuture req : reqs) {
            Object o = req.getResult();
            log.info("获取到返回结果为：id:{},result:{}",req.getId(),o.toString());
        }
    }

    private static void sendMsg(RequestFuture requestFuture) {
        log.info("模拟发送请求 id:{},request:{}",requestFuture.getId(),requestFuture.getRequest().toString());
    }
}
