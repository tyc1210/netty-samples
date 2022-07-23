package com.tyc.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestFileChannel {
    public static void main(String[] args) {
        try(
                FileChannel in = new FileInputStream("f://data.txt").getChannel();
                FileChannel out = new FileOutputStream("to.txt").getChannel();
        ){
            long size = in.size();
            for(long left = 0;size - left > 0;){
                // 一次最多2G
                long position = in.transferTo(left, size, out);
                left = position;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
