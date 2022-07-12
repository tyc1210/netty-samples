package com.tyc;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {
    public static void main(String[] args)throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        boolean isConnect = socketChannel.connect(inetSocketAddress);
        if(!isConnect){
            while (!socketChannel.finishConnect()){
                System.out.println("在连接时客户端不会阻塞可以做其他事情");
            }
        }
        while (true){
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNext()) {
                String msg = scanner.next();
                socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            }
        }
    }
}

