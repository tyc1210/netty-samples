package com.tyc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 利用 buffer channel 将字符串输出到文件
 *
 * @author tyc
 * @version 1.0
 * @date 2022-07-12 10:07:57
 */
public class NioDemo2 {
    public static void main(String[] args) throws IOException {
        String msg = "利用 buffer channel 将字符串输出到文件";
        ByteBuffer buffer = ByteBuffer.allocate(msg.getBytes().length);
        buffer.put(msg.getBytes());
        FileOutputStream fileOutputStream = new FileOutputStream("f://b.txt");
        FileChannel outChannel = fileOutputStream.getChannel();
        buffer.flip();
        outChannel.write(buffer);
        buffer.clear();
        outChannel.close();
        fileOutputStream.close();
    }
}
