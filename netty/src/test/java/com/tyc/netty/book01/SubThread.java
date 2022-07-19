package com.tyc.netty.book01;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-07-18 14:49:34
 */
@Data
public class SubThread extends Thread{
    private RequestFuture requestFuture;

    public SubThread(RequestFuture requestFuture){
        this.requestFuture = requestFuture;
    }


    @Override
    public void run() {
        // 模拟从服务器端获取结果
        Response response = new Response();
        response.setId(requestFuture.getId());
        response.setResult("server response:"+currentThread().getId());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RequestFuture.received(response);
    }
}
