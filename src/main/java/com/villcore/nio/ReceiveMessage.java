package com.villcore.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by villcore on 2017/1/16.
 */
public class ReceiveMessage {
    private static final int MAX_BUFFER_SIZE = 1 * 1024 * 1024; //1Mb

    private ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
    private ByteBuffer contentBuffer;

    public ReceiveMessage(){
    }

    public boolean isComplete() {
        if(contentBuffer == null) {
            return false;
        }
        return !sizeBuffer.hasRemaining() && !contentBuffer.hasRemaining();
    }

    public int read(ReadableByteChannel channel) throws IOException {
        if(sizeBuffer.hasRemaining()) {
            return channel.read(sizeBuffer);
        }

        if(contentBuffer == null) {
            sizeBuffer.flip();
            int size = sizeBuffer.getInt();
            if(size <= 0 || size >= MAX_BUFFER_SIZE) {
                throw new IllegalStateException("receive byte size is illegal");
            }
            contentBuffer = ByteBuffer.allocate(size);
        }


        if(contentBuffer.hasRemaining()) {
            return channel.read(contentBuffer);
        }
        return 0;
    }

    public ByteBuffer getContentBuffer() {
        if(contentBuffer != null) {
            contentBuffer.flip();
        }
        return contentBuffer;
    }
}
