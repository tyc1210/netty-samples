package com.tyc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 处理可写事件
 */
public class TestServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(8080));

        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if(selectionKey.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey key = socketChannel.register(selector, 0, null);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 3000; i++) {
                        sb.append(i);
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    socketChannel.write(buffer);
                    // 若没有写完则关注可写事件
                    if(buffer.hasRemaining()){
                        key.interestOps(key.interestOps() + SelectionKey.OP_WRITE);
                        key.attach(buffer);
                    }
                }else if(selectionKey.isWritable()){
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    channel.write(buffer);
                    // 写完则取消可写事件
                    if(!buffer.hasRemaining()){
                        selectionKey.attach(null);
                        selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }


    }
}
