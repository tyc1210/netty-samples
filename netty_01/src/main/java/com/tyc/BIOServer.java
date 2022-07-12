package com.tyc;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * bio编写服务端
 *
 * @author tyc
 * @version 1.0
 * @date 2022-07-12 10:35:09
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8012);
        while (true){
            // 阻塞等待接收数据
            System.out.println(Thread.currentThread().getName()+":"+"等待连接...");
            Socket socket = serverSocket.accept();
            //接收到数据 开线程处理
            System.out.println("开启线程处理客户端连接...");
            new Thread(()->{
                try(InputStream inputStream = socket.getInputStream()){
                    final byte[] bytes = new byte[1024];
                    while (true){
                        System.out.println(Thread.currentThread().getName()+":"+"等待数据...");
                        final int read = inputStream.read(bytes);
                        if(read != -1){
                            System.out.println(Thread.currentThread().getName()+"收到"+":"+new String(bytes,0,read,"gb2312"));
                        }else {
                            System.out.println("连接断开");
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
