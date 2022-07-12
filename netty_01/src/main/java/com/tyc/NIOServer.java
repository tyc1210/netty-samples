package com.tyc;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args)throws Exception {
        //拿到ServerSocketChannel设置为非阻塞绑定端口
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(6666));
        //拿到Selector将socket注册，并附带其关心的事件
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            if(selector.select(1000)==0){
                System.out.println("阻塞1秒没有事件发生继续下次循环");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //判断是否有新的客户端连接，有则分配channel
                if(selectionKey.isAcceptable()){
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("有客户端连接：分配Channel:"+socketChannel.hashCode());
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(selectionKey.isReadable()){
                    //获取key对应的channel
                    SocketChannel socketChannel1 = (SocketChannel) selectionKey.channel();
                    //获取key对应的Bytebuffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    socketChannel1.read(byteBuffer);
                    System.out.println("接收到来自客户端数据："+new String(byteBuffer.array()));
                    byteBuffer.clear();
                }
                //移除防止重复操作
                iterator.remove();
            }
        }
    }
}
