package com.villcore.nio.com.villcore.demo.alert;

/**
 * Created by WangTao on 2017/2/22.
 */
public class IduRecordAlertControler {
    private IduRecordRedisConsumer consumer;

    public IduRecordProcessResult queryAlertStatus() {
        IduRecordRedisConsumer.Bundle latestBundle = consumer.getLatestBundle();
        return processIduRecord(latestBundle);
    }

    private IduRecordProcessResult processIduRecord(IduRecordRedisConsumer.Bundle bundle) {
        if(bundle.isEmpty()) {
            return new IduRecordProcessResult(bundle.first, bundle.second, true);
        }

//        if() {
//            return new IduRecordProcessResult(bundle.first, bundle.second, false);
//        }
        return new IduRecordProcessResult(bundle.first, bundle.second, true);
    }
}
