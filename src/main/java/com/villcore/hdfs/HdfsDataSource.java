//package com.villcore.hdfs;
//
//import com.googlecode.mp4parser.DataSource;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.nio.*;
//import java.nio.channels.WritableByteChannel;
//
///**
// * Created by villcore on 2017/2/28.
// */
//public class HdfsDataSource implements DataSource {
//
//    private final Logger LOG = LoggerFactory.getLogger(getClass());
//
//    private FSDataInputStream fsDataInputStream;
//    private long fileSize;
//
//    public HdfsDataSource(FSDataInputStream fsDataInputStream, long fileSize) {
//        this.fsDataInputStream = fsDataInputStream;
//        this.fileSize = fileSize;
//    }
//
//    @Override
//    public int read(ByteBuffer byteBuffer) throws IOException {
//        return fsDataInputStream.read(byteBuffer);
//    }
//
//    @Override
//    public long size() throws IOException {
//        return fileSize;
//    }
//
//    @Override
//    public long position() throws IOException {
//        return fsDataInputStream.getPos();
//    }
//
//    @Override
//    public void position(long nuPos) throws IOException {
//        fsDataInputStream.seek(nuPos);
//    }
//
//    @Override
//    public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
//        ByteBuffer byteBuffer = ByteBuffer.allocate((int) count);
//        long originPos = fsDataInputStream.getPos();
//        fsDataInputStream.seek(position);
//
//        while(byteBuffer.hasRemaining()) {
//            fsDataInputStream.read(byteBuffer);
//        }
//        fsDataInputStream.seek(originPos);
//        return count;
//    }
//
//    @Override
//    public ByteBuffer map(long startPosition, long size) throws IOException {
//        ByteBuffer byteBuffer = ByteBuffer.allocate((int) size);
//        long originPos = fsDataInputStream.getPos();
//
//        fsDataInputStream.seek(startPosition);
//
//        while(byteBuffer.hasRemaining()) {
//            fsDataInputStream.read(byteBuffer);
//        }
//        fsDataInputStream.seek(originPos);
//        return byteBuffer;
//    }
//
//    @Override
//    public void close() throws IOException {
//        fsDataInputStream.close();
//    }
//}
