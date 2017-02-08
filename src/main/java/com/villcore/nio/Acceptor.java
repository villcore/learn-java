package com.villcore.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by villcore on 2017/1/16.
 */
public class Acceptor extends Thread {

    private static final long SELECT_INTERVAL = 500L;
    private static final long RECEIVE_BUFFER_SIZE = 8192;
    private static final long SEND_BUFFER_SIZE = 8192;

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int port;

    private Processer[] processers;

    private AtomicBoolean isRuning = new AtomicBoolean(false);

    private int connectionCount = 0;

    public Acceptor(int port, Processer[] processers) {
        this.port = port;
        this.processers = processers;
    }

    @Override
    public void run() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(this.port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("can not init selector or server socket chanel...");
        }

        int ready = -1;
        while(isRuning.get()) {
            try {
                ready = selector.select(SELECT_INTERVAL);
                if(ready <= 0) {
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();

                while(isRuning.get() && it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    it.remove();
                    if(selectionKey.isValid()) {
                        doAccept(selectionKey);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("select error...");
            }

        }
    }

    public void close() {
        try {
            isRuning.compareAndSet(true, false);
            selector.wakeup();
            serverSocketChannel.socket().close();
            serverSocketChannel.close();
            selector.close();

            for(Processer processer : processers) {
                processer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startup() {
        isRuning.compareAndSet(false, true);
        this.start();
    }

    private void doAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverChanel = (ServerSocketChannel) selectionKey.channel();
        //serverChanel.socket().setReceiveBufferSize(receiveBufferSize);

        SocketChannel clientChannel = serverChanel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.socket().setTcpNoDelay(true);
        //clientChannel.socket().setSendBufferSize(sendBufferSize);
        connectionCount++;
        if(connectionCount >= Integer.MAX_VALUE) {
            connectionCount = 0;
        }

        Processer processer = getProcesser(this.processers, connectionCount);
        processer.addNewSocketChannel(clientChannel);
    }

    private Processer getProcesser(Processer[] processers, int count) {
        return processers[count % processers.length];
    }
}
