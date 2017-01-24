package com.villcore.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by villcore on 2017/1/16.
 */
public class SimpleNIOClient {
    public static void main(String[] args) throws InterruptedException {
        String msg[] = new String[]{"hello nio...!",  "just say hello.", "repeat again..."};

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            socketChannel.socket().setKeepAlive(true);
            socketChannel.socket().setTcpNoDelay(true);
            socketChannel.socket().setSoTimeout(30 * 1000);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 19090));

            for(int i = 0; i < 10; i++) {
                byte[] bytes = msg[i % msg.length].getBytes("utf-8");
                SendMessage sendMessage = new SendMessage(bytes.length,
                        ByteBuffer.allocate(bytes.length).put(bytes));

                while (!sendMessage.isComplete()) {
                    sendMessage.write(socketChannel);
                }

                System.out.println("client send a test msg success...");

                ReceiveMessage receiveMessage = new ReceiveMessage();
                while (!receiveMessage.isComplete()) {
                    receiveMessage.read(socketChannel);
                }
                ByteBuffer contentBuffer = receiveMessage.getContentBuffer();
                System.out.println(String.format("client receive msg : [%s]...",
                        new String(contentBuffer.array(), "utf-8")));

                TimeUnit.SECONDS.sleep(1L);
            }
            socketChannel.socket().close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
