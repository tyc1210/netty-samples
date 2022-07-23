package com.tyc.nio.multithread;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("Boss");
        ServerSocketChannel boss = ServerSocketChannel.open();
        boss.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey selectionKey = boss.register(selector, 0, null);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        boss.bind(new InetSocketAddress(8080));
        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey sKey = iterator.next();
                iterator.remove();
                if(sKey.isAcceptable()){
                    SocketChannel socketChannel = boss.accept();
                    socketChannel.configureBlocking(false);
                }
            }
        }
    }

    class Worker{

    }
}
