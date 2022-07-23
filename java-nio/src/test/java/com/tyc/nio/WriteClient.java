package com.tyc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
        while (true){
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            sc.read(buffer);
            buffer.clear();
        }
    }
}
