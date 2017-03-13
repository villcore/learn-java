package com.villcore.http;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by villcore on 2017/3/6.
 */
public class SimpleHttpServer {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleHttpServer.class);

    public static void main(String[] args) {
        String maxUser = "50";
        InetSocketAddress address = new InetSocketAddress(8090);

        try {
            HttpServer server = HttpServer.create(address, Integer.valueOf(maxUser));

            server.createContext("/", new SimpleHttpHandler());
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}