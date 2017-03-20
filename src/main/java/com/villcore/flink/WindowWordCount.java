//package com.villcore.flink;
//
//import org.apache.flink.api.common.JobExecutionResult;
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.api.common.functions.ReduceFunction;
//import org.apache.flink.api.common.time.Time;
//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
//
///**
//* Created by villcore on 2017/3/2.
//*/
//public class WindowWordCount {
//    public static void main(String[] args) throws Exception {
//        StreamExecutionEnvironment ev = StreamExecutionEnvironment.getExecutionEnvironment();
//        DataStream<Long> randomIntStream = ev.generateSequence(0, 1 * 10L);
//
//        DataStream<Long> oddOrEvenSteram = randomIntStream.map(new MapFunction<Long, Long>() {
//            @Override
//            public Long map(Long longVal) throws Exception {
//                if (longVal.intValue() % 2 == 0) {
//                    return Long.valueOf(0);
//                } else {
//                    return Long.valueOf(1);
//                }
//            }
//        });
//
//        DataStream<Tuple2<Long, Long>> oddOrEventCount = oddOrEvenSteram.map(new MapFunction<Long, Tuple2<Long, Long>>() {
//            @Override
//            public Tuple2<Long, Long> map(Long aLong) throws Exception {
//                return new Tuple2<Long, Long>(aLong, Long.valueOf(1));
//            }
//        });
//
//        oddOrEventCount.keyBy("f0").reduce(new ReduceFunction<Tuple2<Long, Long>>() {
//            @Override
//            public Tuple2<Long, Long> reduce(Tuple2<Long, Long> t1, Tuple2<Long, Long> t2) throws Exception {
//                return new Tuple2<Long, Long>(t1.f0, t1.f1 + t2.f1);
//            }
//        });
////        KeyedStream<Integer, Tuple2<Integer, Integer>> keyedStream = oddOrEvenSteram.keyBy(new KeySelector<Integer, Tuple2<Integer, Integer>>() {
////            @Override
////            public Tuple2<Integer, Integer> getKey(Integer integer) throws Exception {
////                return new Tuple2<Integer, Integer>(integer, 1);
////            }
////        });
////
////        keyedStream.reduce(new ReduceFunction<Integer>() {
////            @Override
////            public Integer reduce(Integer integer, Integer t1) throws Exception {
////                return integer + t1;
////            }
////        }).print();
//
//        JobExecutionResult result = ev.execute();
//    }
//}
