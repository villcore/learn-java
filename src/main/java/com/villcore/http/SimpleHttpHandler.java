package com.villcore.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/6.
 */
public class SimpleHttpHandler implements HttpHandler {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private static final String ERROR_MSG = "参数错误！";
    private static final String EMPTY_MSG = "查找结果为空！";

    private static final String CHARSET = "utf-8";

    private boolean isInit = false;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        Headers responseHeaders = exchange.getResponseHeaders();


        try {
            if(!isInit) {
                Thread.sleep(10 * 1000L);
                isInit = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseHeaders.set("Location", "http://192.168.0.232/1.mp4");
        exchange.sendResponseHeaders(302, 0);

    }
}
