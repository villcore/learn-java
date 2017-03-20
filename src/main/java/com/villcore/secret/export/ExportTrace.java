package com.villcore.secret.export;


import com.villcore.secret.net.v1.NetUtil;
import com.villcore.secret.net.v2.HttpClientFactory;
import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExportTrace {
    private static final String CHARSET = "utf-8";
    private static final String URL_CHANEL_LIST = "http://192.168.0.1/policy/audit-record-webtitle-data-detail?terminal_type=all&start_time=2017-02-06+00%3A00%3A00&end_time=2017-02-21+00%3A00%3A00&keyword_ip_name=&src_ip=&user_id=&groupby=src_ip&keytype=&keyword=&content_field=5195";

    private HttpClient httpClient;

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = HttpClientFactory.createHttpClient();

        //login info
        String username = "admin";
        String password = "atadgibolg";
        String loginUrl = "http://192.168.0.1/auth/login";

        Map<String, String> loginHeaderMap = new HashMap<>();
        loginHeaderMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        //loginHeaderMap.put("Accept-Encoding", "gzip, deflate");
        loginHeaderMap.put("Accept-Language", "zh-CN,zh;q=0.8");
        loginHeaderMap.put("Cache-Control", "max-age=0");
        loginHeaderMap.put("Connection", "keep-alive");
        loginHeaderMap.put("Content-Type", "application/x-www-form-urlencoded");
        loginHeaderMap.put("Host", "192.168.0.1");
        loginHeaderMap.put("Origin", "http://192.168.0.1");
        loginHeaderMap.put("Referer", "http://192.168.0.1/auth/login");
        loginHeaderMap.put("Upgrade-Insecure-Requests", "application/x-www-form-urlencoded");
        loginHeaderMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

        Map<String, String> loginFormMap = new HashMap<>();
        loginFormMap.put("username", username);
        loginFormMap.put("password", password);
        //loginFormMap.put("_token", "j4TmnPmeq5iAAApxOTWUIZbmAe8nVhmMw0qPaxP0");
        loginFormMap.put("commit", "登录");

        String resp = NetUtil.doPost(httpClient, loginUrl, CHARSET, loginHeaderMap, loginFormMap);

        if (resp.contains("Redirecting to ")) {
            System.out.println("login success !");
        } else {
            System.out.println(resp);
            System.out.println("login failed ! exited...");
        }

        Map<String, String> filedNameToNo = new HashMap<>();
        filedNameToNo.put("搜索引擎", "5195");
        filedNameToNo.put("other", "5199");
        filedNameToNo.put("购物", "5141");
        filedNameToNo.put("聊天室或即时通讯", "5184");
        filedNameToNo.put("下载", "5133");
        filedNameToNo.put("新闻", "5147");
        filedNameToNo.put("招聘", "5140");
        filedNameToNo.put("HTTP", "4100");
        filedNameToNo.put("交友约会", "5157");
        filedNameToNo.put("银行", "5143");
        filedNameToNo.put("网页邮件", "5132");
        filedNameToNo.put("广告", "5200");
        filedNameToNo.put("播客", "5136");
        filedNameToNo.put("图片库", "5183");
        filedNameToNo.put("社交网络", "5137");
        filedNameToNo.put("旅游", "5174");
        filedNameToNo.put("论坛博客", "5131");
        filedNameToNo.put("电影", "5126");
        filedNameToNo.put("武术", "5177");
        filedNameToNo.put("Internet服务提供商", "5188");

        Calendar startCalendar = getCalendar(2017, 3, 14, 0, 0, 0);
        Calendar endCalendar = getCalendar(2017, 3, 18, 0, 0, 0);

        Calendar curCalendar = startCalendar;


        FileOutputStream fos = new FileOutputStream("c://" + System.currentTimeMillis() + ".dat", true);
        PrintStream ps = new PrintStream(fos);

        while (curCalendar.before(endCalendar)) {
            Calendar tmp = Calendar.getInstance();
            tmp.setTime(curCalendar.getTime());

            curCalendar.add(Calendar.DAY_OF_MONTH, 1);

            Calendar next = Calendar.getInstance();
            next.setTime(curCalendar.getTime());

            System.out.println(getTimeStr(tmp) + " -> " + getTimeStr(next));

            //查询
            String startTime = getTimeStr(tmp);
            String endTime =  getTimeStr(next);


            for (Map.Entry<String, String> entry : filedNameToNo.entrySet()) {
                String filedName = entry.getKey();
                String filed = entry.getValue();
                System.out.println(filedName + " -> " + filed);
            }
            int allowedMaxEmptyPage = 5;

            for (Map.Entry<String, String> entry : filedNameToNo.entrySet()) {
                String filedName = entry.getKey();
                String filed = entry.getValue();

                int page = 1;
                int emptyPage = 0;
                int row = 2;

                java.util.List<String> records = new ArrayList<>();
                int itemCount = 0;

                System.out.println("query info for : " + filedName);

                while (true) {
                    if (emptyPage >= allowedMaxEmptyPage) {
                        System.out.println("exceed max empty page for " + filedName);
                        break;
                    }

                    String queryUrl = "http://192.168.0.1/policy/audit-record-webtitle-data-detail?terminal_type=all";

                    Map<String, String> queryHeaderMap = new HashMap<>();
                    queryHeaderMap.put("Accept", "application/json, text/javascript, */*; q=0.01");
                    //queryHeaderMap.put("Accept-Encoding", "gzip, deflate");
                    queryHeaderMap.put("Accept-Language", "zh-CN,zh;q=0.8");
                    queryHeaderMap.put("Connection", "keep-alive");
                    queryHeaderMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    queryHeaderMap.put("Accept", "application/json, text/javascript, */*; q=0.01");
                    queryHeaderMap.put("Host", "192.168.0.1");
                    queryHeaderMap.put("Origin", "http://192.168.0.1");
                    queryHeaderMap.put("Referer", "http://192.168.0.1/");
                    queryHeaderMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
                    queryHeaderMap.put("X-Requested-With", "XMLHttpRequest");

                    Map<String, String> queryFormMap = new HashMap<>();
                    //queryFormMap.put("terminal_type", "all");
                    queryFormMap.put("start_time", startTime);
                    queryFormMap.put("end_time", endTime);
                    queryFormMap.put("keyword_ip_name", "");
                    queryFormMap.put("src_ip", "");
                    queryFormMap.put("user_id", "");
                    queryFormMap.put("groupby", "src_ip");
                    queryFormMap.put("keytype", "");
                    queryFormMap.put("keyword", "");
                    queryFormMap.put("content_field", filed);
                    queryFormMap.put("page", String.valueOf(page));
                    queryFormMap.put("rows", String.valueOf(row));

                    StringBuilder sb = new StringBuilder();
                    sb.append(queryUrl);

                    for (Map.Entry<String, String> entry2 : queryFormMap.entrySet()) {
                        String paramName = entry2.getKey();
                        String paramValue = entry2.getValue();

                        sb.append("&")
                                .append(paramName)
                                .append("=")
                                .append(URLEncoder.encode(paramValue, "utf-8"));
                    }

                    //System.out.println(sb.toString());

                    //System.out.println(resp);
                    try {
                        resp = NetUtil.doPost(httpClient, sb.toString(), CHARSET, loginHeaderMap, loginFormMap);
                        JSONObject obj = new JSONObject(resp);
                        if (!obj.has("rows")) {
                            emptyPage++;
                        }
                        JSONArray rows = obj.getJSONArray("rows");

                        if (rows.length() == 0) {
                            emptyPage++;
                        }
                        Iterator<Object> it = rows.iterator();

                        while (it.hasNext()) {
                            JSONObject rowItem = (JSONObject) it.next();
                            //records.add(rowItem.toString());
                            if (rowItem.getString("lan_mac").trim().equals("C4:8E:8F:74:6F:35")) {
                                System.out.println(rowItem.getString("capture_time") + " : " + rowItem.getString("lan_mac") + " -> " + rowItem.getString("title"));
                                ps.println(rowItem.getString("capture_time") + " : " + rowItem.getString("lan_mac") + " -> " + rowItem.getString("title"));
                            }
                        }
                        page++;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(resp);
                    }
                }
            }
        }
    }

    public static Calendar getCalendar (int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar;
    }

    public static String getTimeStr (Calendar calendar) {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestamp = calendar.getTimeInMillis();
        return dataFormat.format(new Date(timestamp));
    }
}
