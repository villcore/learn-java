//package com.villcore.infludb;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import org.influxdb.InfluxDB;
//import org.influxdb.InfluxDBFactory;
//import org.influxdb.dto.BatchPoints;
//import org.influxdb.dto.Point;
//import org.influxdb.dto.Query;
//
//
//
//import org.influxdb.InfluxDB.ConsistencyLevel;
//public class Test {
//	public static void main(String[] args) {
//		InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.137.104:8086","root","root");
//		String dbName = "aTimeSeries";
//		influxDB.createDatabase(dbName);
////
////		BatchPoints batchPoints = BatchPoints
////		                .database(dbName)
////		                .tag("async", "true")
////		                .retentionPolicy("autogen")
////		                .consistency(ConsistencyLevel.ALL)
////		                .build();
////		Point point1 = Point.measurement("cpu")
////		                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
////		                    .addField("idle", 90L)
////		                    .addField("user", 9L)
////		                    .addField("system", 1L)
////		                    .build();
////		Point point2 = Point.measurement("disk")
////		                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
////		                    .addField("used", 80L)
////		                    .addField("free", 1L)
////		                    .build();
////		batchPoints.point(point1);
////		batchPoints.point(point2);
////		influxDB.write(batchPoints);
////		Query query = new Query("SELECT idle FROM cpu", dbName);
////		influxDB.query(query);
//		influxDB.deleteDatabase(dbName);
//	}
//}
