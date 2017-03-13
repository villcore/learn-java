package com.villcore.hdfs;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;

/**
 * Created by villcore on 2017/2/28.
 */
public class HdfsFileManager {
    public static void main(String[] args) throws IOException {
        System.setProperty("HADOOP_HOME", "E:\\workspace\\hadoop-2.6.5");
        Configuration conf= new Configuration();
        //TODO 链接信息需要配置文件读取
        FileSystem hdfs = FileSystem.get(URI.create("hdfs://192.168.56.103:9000/"), conf);
        RemoteIterator<LocatedFileStatus> fileStatusRemoteIterator = hdfs.listFiles(new Path("/"), true);
        while(fileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus status = fileStatusRemoteIterator.next();
            System.out.println(status.getPath() + " -> " + status.getLen());
        }

        String hdfsPath = "/1488174427136.h264";
        FileStatus status = hdfs.getFileStatus(new Path(hdfsPath));
        FSDataInputStream fsDataInputStream = hdfs.open(new Path(hdfsPath));
        System.out.println(fsDataInputStream.getPos());

        try {
            H264TrackImpl h264Track = new H264TrackImpl(new HdfsDataSource(fsDataInputStream, status.getLen()));

            Movie movie = new Movie();
            movie.addTrack(h264Track);

            Container mp4file = new DefaultMp4Builder().build(movie);

            File dst = new File("e://output.mp4");
            FileChannel fc = new FileOutputStream(dst).getChannel();
            mp4file.writeContainer(fc);
            fc.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
