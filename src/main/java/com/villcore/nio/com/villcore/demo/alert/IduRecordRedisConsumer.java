package com.villcore.nio.com.villcore.demo.alert;

import java.util.Map;

/**
 * Created by WangTao on 2017/2/22.
 * </p>
 * 从Redis消费IDU数据，并计算最新状态
 */
public class IduRecordRedisConsumer implements Runnable{
    static class Bundle {
        final SimpleIduRecord first;
        final SimpleIduRecord second;

        Bundle(SimpleIduRecord first, SimpleIduRecord second) {
            this.first = first;
            this.second = second;
        }

        boolean isEmpty() {
            return first == null || second == null;
        }
    }

    private SimpleIduRecord first;
    private SimpleIduRecord second;

    @Override
    public void run() {

    }

    public void startup() {

    }

    public void stop() {

    }

    public Bundle getLatestBundle() {
        return new Bundle(this.first, this.second);
    }

    private SimpleIduRecord parseIduRecordFrom(Map<String, String> infoMap) {
        return null;
    }
}
