package com.villcore.nio;

/**
 * Created by villcore on 2017/1/17.
 */
public interface MessageHandler {
    public SendMessage handle(ReceiveMessage receiveMessage);
}
