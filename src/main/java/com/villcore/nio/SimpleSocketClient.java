package com.villcore.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.rmi.server.ExportException;

/**
 * Created by villcore on 2017/1/17.
 */
public class SimpleSocketClient {
    public static void main(String[] args) throws IOException {
        Socket sc = new Socket();
        sc.setTcpNoDelay(true);
        sc.setSoTimeout(30 * 1000);
        sc.connect(new InetSocketAddress("127.0.0.1", 19090));

        InputStream is = sc.getInputStream();
        OutputStream os = sc.getOutputStream();
        new ReceiveThread(is).start();

        String msgFormat = "this is %d msg...";
        for(int i = 0; i < 1; i++) {
            String msg = String.format(msgFormat, i);
            byte[] content = msg.getBytes("utf-8");
            int contentLen = content.length;

            ByteBuffer lenBuffer = ByteBuffer.allocate(4);
            lenBuffer.putInt(contentLen);
            os.write(lenBuffer.array());
            os.write(content);
            System.out.println(String.format("client send %d msg...", i));
        }
    }

    public static class ReceiveThread extends Thread {
        private InputStream is;

        public ReceiveThread(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            byte[] bytes = new byte[1024];
            int pos = -1;

            try {
                while (true) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    while ((pos = is.read(bytes)) > 0) {
                        bos.write(bytes, 0, pos);

                        if(bos.size() > 4) {
                            byte[] bytes2 = bos.toByteArray();

                        }
                    }
                    System.out.println("&&&");

                    String content = new String(bytes, "utf-8");
                    System.out.println(String.format("client receive size : %d, msg : [%s]", content, content));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
