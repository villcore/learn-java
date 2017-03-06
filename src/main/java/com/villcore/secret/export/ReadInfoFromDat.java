package com.villcore.secret.export;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by WangTao on 2017/2/13.
 */
public class ReadInfoFromDat {
    public static void main(String[] args) throws FileNotFoundException {
        String selectMac = "C4:8E:8F:74:6F:35";
        String[] excludeTitles = new String[]{"百度推广", "SOGOU推广服务", "百度网盟推广"};
        List<String> infoStrs = null;

        String path = "c://2.dat";
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(path))) {
            infoStrs = (List<String>) is.readObject();

            int count = 0;

            Outer:
            for(String info : infoStrs) {
                JSONObject obj = new JSONObject(info);
                String time =  obj.getString("capture_time");
                String mac = obj.getString("lan_mac");
                String title =  obj.getString("title");
                String host = obj.getString("host");
                String url = obj.getString("url");
                String link = host  + url;

                url = "";
                for(String excludeTitle : excludeTitles) {
                    if(excludeTitle.equals(title)) {
                        continue Outer;
                    }
                }
                if(mac.equalsIgnoreCase(selectMac)) {
                    System.out.println(String.format("%s -> %s -> %s -> %s", time, mac, title, link));
                }
                count++;
            }
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
