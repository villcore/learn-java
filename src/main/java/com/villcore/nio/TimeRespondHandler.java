package com.villcore.nio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by villcore on 2017/1/17.
 */
public class TimeRespondHandler implements MessageHandler{
    @Override
    public SendMessage handle(ReceiveMessage receiveMessage) {
        try {
            ByteBuffer contentBuffer = receiveMessage.getContentBuffer();
            String receiveMsg = new String(contentBuffer.array(), "utf-8");
            System.out.println(String.format("receive client msg : [%s]", receiveMsg));

            String respMsg = "current time : " + new Date().toString();
            byte[] bytes = respMsg.getBytes("utf-8");
            return new SendMessage(bytes.length, ByteBuffer.allocate(bytes.length).put(bytes));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
