package com.tyc.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-07-13 14:42:42
 */
public class TestBuffer {
    public static void main(String[] args){
        String msg = "利用 buffer channel 将字符串输出到文件1111";

         ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
//        ByteBuffer buffer = Charset.forName("UTF-8").encode(msg);
        try (FileChannel channel = new FileOutputStream("f://b.txt").getChannel()){
            channel.write(buffer);
        }catch (IOException e){
        }
    }
}
