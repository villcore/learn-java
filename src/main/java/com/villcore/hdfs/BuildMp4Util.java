//package com.villcore.hdfs;
//
//import com.coremedia.iso.boxes.Container;
//import com.googlecode.mp4parser.FileDataSourceImpl;
//import com.googlecode.mp4parser.authoring.Movie;
//import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
//import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.nio.channels.FileChannel;
//
///**
// * Created by wangtao on 2017/2/28.
// */
//public class BuildMp4Util {
//    public static void main(String[] args) {
//        try {
//            H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl("video.h264"));
//
//            Movie movie = new Movie();
//            movie.addTrack(h264Track);
//
//            Container mp4file = new DefaultMp4Builder().build(movie);
//
//            FileChannel fc = new FileOutputStream(new File("output.mp4")).getChannel();
//            mp4file.writeContainer(fc);
//            fc.close();
//        }
//
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
