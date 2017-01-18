package com.villcore.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by villcore on 2017/1/16.
 */
public class SendMessage {
    private ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
    private ByteBuffer contentBuffer;

    public SendMessage(int size, ByteBuffer contentBuffer){
        this.sizeBuffer.putInt(size).flip();
        this.contentBuffer = contentBuffer;
        if(this.contentBuffer == null) {
            this.contentBuffer = ByteBuffer.allocate(size);
            contentBuffer.flip();
        }
        contentBuffer.flip();
    }

    public boolean isComplete() {
        return !sizeBuffer.hasRemaining() && !contentBuffer.hasRemaining();
    }

    public int write(WritableByteChannel channel) throws IOException {
        if(sizeBuffer.hasRemaining()) {
            return channel.write(sizeBuffer);
        }
        if(contentBuffer.hasRemaining()) {
           return channel.write(contentBuffer);
        }
        return 0;
    }
}
