package com.villcore.nio.sftp;

import com.springml.sftp.client.SFTPClient;

/**
 * Created by villcore on 2017/2/16.
 */
public class SftpClientTest {
    public static void main(String[] args) throws Exception {
        SFTPClient sftpClient = new SFTPClient(null, "root", "199295", "192.168.0.103");
        sftpClient.copy("c:\\1.dat", "/home/1.dat");
    }
}
