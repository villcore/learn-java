package com.villcore.nio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by villcore on 2017/1/16.
 */
public class Processer extends Thread{

    private static final long SELECT_INTERVAL = 500L;

    private Selector selector;
    private BlockingQueue<SocketChannel> newSocketChannel;
    private AtomicBoolean isRuning = new AtomicBoolean(false);

    private MessageHandler handler;

    public Processer(MessageHandler handler) {
        newSocketChannel = new LinkedBlockingQueue<>();
        this.handler = handler;
    }

    public void startup() {
        isRuning.compareAndSet(false, true);
        this.start();
    }
    @Override
    public void run(){
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("can not init selector...");
        }

        List<SocketChannel> newSocketChanelList = new ArrayList<>();

        int ready = -1;
        while(isRuning.get()) {
            if(newSocketChannel.drainTo(newSocketChanelList) > 0) {
                for(SocketChannel socketChannel : newSocketChanelList) {
                    try {
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                        throw new RuntimeException("can not register socketChannel...");
                    }
                }
            }

            try {
                ready = selector.select(/*SELECT_INTERVAL*/);
                if(ready <= 0) {
                    continue;
                }
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeySet.iterator();

                while(isRuning.get() && it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    it.remove();

                    if(!selectionKey.isValid()) {
                        closeKey(selectionKey);
                    }

                    if(selectionKey.isReadable()) {
                        doRead(selectionKey);
                    }

                    if(selectionKey.isWritable()) {
                        doWrite(selectionKey);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("select error...");
            }
        }
    }

    private void doRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ReceiveMessage receiveMessage = (ReceiveMessage) selectionKey.attachment();

        if(receiveMessage == null) {
            receiveMessage = new ReceiveMessage();
            selectionKey.attach(receiveMessage);
        }

        int hasRead = receiveMessage.read(socketChannel);

        if(hasRead < 0) {
            closeKey(selectionKey);
            return;
        }
        else if(receiveMessage.isComplete()) {
            selectionKey.attach(null);
            SendMessage sendMessage = handler.handle(receiveMessage);

            if(sendMessage != null) {
                selectionKey.attach(sendMessage);
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                selector.wakeup();
            }
        }
        else {
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }

    private void closeKey(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        socketChannel.socket().close();
        socketChannel.close();
        selectionKey.cancel();
    }

    private void doWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        SendMessage sendMessage = (SendMessage) selectionKey.attachment();

        int hasWrite = sendMessage.write(socketChannel);
        if(hasWrite < 0) {
            closeKey(selectionKey);
            return;
        }
        else if(sendMessage.isComplete()) {
            selectionKey.attach(null);
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
        else {
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    public void close() {
        isRuning.compareAndSet(true, false);
    }

    public void addNewSocketChannel(SocketChannel socketChannel) throws IOException {
        System.out.println(String.format("%s processer server for connection %s",
                Thread.currentThread().getName(),
                socketChannel.getLocalAddress().toString()));

        newSocketChannel.add(socketChannel);
        selector.wakeup();
    }

    public SendMessage processMessage(ReceiveMessage receiveMessage) throws UnsupportedEncodingException {
        ByteBuffer contentByteBuffer = receiveMessage.getContentBuffer();
        byte[] bytes = contentByteBuffer.array();
        System.out.println(String.format("receive msg : %s", new String(bytes, "utf-8")));
        return null;
    }
}
