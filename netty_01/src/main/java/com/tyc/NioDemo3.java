package com.tyc;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读取文件内容打印
 *
 * @author tyc
 * @version 1.0
 * @date 2022-07-12 10:13:28
 */
public class NioDemo3 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("f://b.txt");
        FileChannel inChannel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        inChannel.read(buffer);
        fileInputStream.close();
        inChannel.close();
        buffer.flip();
        System.out.println(new String(buffer.array()));
    }
}
