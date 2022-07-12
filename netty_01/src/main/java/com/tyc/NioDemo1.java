package com.tyc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 实现文件拷贝
 *
 * @author tyc
 * @version 1.0
 * @date 2022-07-12 10:03:34
 */
public class NioDemo1 {
    public static void main(String[] args) throws IOException {
        f2();
    }

    public static void f1()throws IOException{
        FileInputStream fileInputStream = new FileInputStream("f://a.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("f://b.txt");
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();
        outChannel.transferFrom(inChannel,0,inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    public static void f2()throws IOException{
        FileInputStream fileInputStream = new FileInputStream("f://a.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("f://b.txt");
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        long size = inputStreamChannel.size();
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.parseInt(String.valueOf(size)));
        FileChannel outputStreamChannel = fileOutputStream.getChannel();
        inputStreamChannel.read(byteBuffer);
        byteBuffer.flip();
        outputStreamChannel.write(byteBuffer);
        inputStreamChannel.close();
        outputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
