package com.tyc.nio;


import java.nio.ByteBuffer;

public class TestByteBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("123456\n789".getBytes());
        splitSource(buffer);
        buffer.put("Hello world\nI am lisi\n ho".getBytes());
        splitSource(buffer);
        buffer.put("w are you?\n".getBytes());
        splitSource(buffer);
    }

    private static void splitSource(ByteBuffer buffer) {
        buffer.flip(); //切换到读模式
        for (int i = 0; i < buffer.limit(); i++) {
            if(buffer.get(i) == '\n'){ // get(index) 方法不会导致position移动
                int length = i - buffer.position() + 1;
                ByteBuffer buf = ByteBuffer.allocate(length);
                for (int i1 = 0; i1 < length; i1++) {
                    buf.put(buffer.get());
                }
                System.out.println(new String(buf.array()));
            }
        }
        buffer.compact();
        System.out.println(1);
    }
}
