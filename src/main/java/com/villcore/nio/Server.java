package com.villcore.nio;

import java.util.concurrent.CountDownLatch;

/**
 * Created by villcore on 2017/1/16.
 */
public class Server {
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private int port;
    private int processerNum;

    private Acceptor acceptor;
    private Processer[] processers;
    private MessageHandler handler;

    public Server(int port, int processerNum, MessageHandler handler) {
        this.port = port;
        this.processerNum = processerNum;

        this.handler = handler;

        this.processers = new Processer[5];
        for(int i = 0; i < processers.length; i++) {
            this.processers[i] = new Processer(this.handler);
        }

        this.acceptor = new Acceptor(port, processers);
    }

    public void start(){
        acceptor.startup();

        for(int i = 0; i < processers.length; i++) {
            this.processers[i].startup();
        }

        System.out.println(String.format("server started, listen port[%s]...", port));

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                acceptor.close();
                for(Processer processer : processers) {
                    processer.close();
                }
                close();
            }
        });
    }

    public void close() {
        countDownLatch.countDown();
    }

    public void awaitShutdown() throws InterruptedException {
        countDownLatch.await();
    }

    public static void main(String[] args){
        int port = 19090;
        int processerNum = 5;

        MessageHandler handler = new TimeRespondHandler();

        Server server = new Server(port, processerNum, handler);
        server.start();
        try {
            server.awaitShutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
