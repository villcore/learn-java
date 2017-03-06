package com.villcore.secret.net.v2;

import okhttp3.*;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetUtil {
	public static final Logger LOG = Logger.getLogger(NetUtil.class.getName());

	public static final String DEFAULT_CHARSET = "utf-8";

	public static String decode(String str) {
		final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
		Matcher m = reUnicode.matcher(str);
		StringBuffer sb = new StringBuffer(str.length());
		while (m.find()) {
			m.appendReplacement(sb, Character.toString((char) Integer.parseInt(m.group(1), 16)));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String doGet(OkHttpClient httpClient, String url, Map<String, String> headerMap, String charset) throws IOException{
		//		long time = -System.currentTimeMillis();
		//		System.out.println(">>> start get [" + url + "]...");
		Request.Builder reqBuilder = new Request.Builder();
		reqBuilder.url(url);
		if(headerMap != null) {
			for(Entry<String, String> entry : headerMap.entrySet()) {
				reqBuilder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		Request req = reqBuilder.build();
		Response resp = httpClient.newCall(req).execute();
		String respStr = "";
		if(resp.isSuccessful()) {
			//			System.out.println(time+= System.currentTimeMillis());
			//			System.out.println(">>> end get [" + url + "]...");
			ResponseBody respBody = resp.body();
			respStr = new String(respBody.bytes(), charset);
			respBody.close();
		}
		return respStr;
	}

	public static String doPost(OkHttpClient httpClient, String url, Map<String, String> headerMap, Map<String, String> paramMap, String charset) throws IOException {
		//		long time = -System.currentTimeMillis();
		//		System.out.println(">>> start post [" + url + "]...");
		Request.Builder reqBuilder = new Request.Builder();
		reqBuilder.url(url);
		if(headerMap != null) {
			for(Entry<String, String> entry : headerMap.entrySet()) {
				reqBuilder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if(paramMap != null) {
			FormBody.Builder formEncodingBuilder = new FormBody.Builder();
			for(Entry<String, String> entry : paramMap.entrySet()) {
				formEncodingBuilder.add(entry.getKey(), entry.getValue());
			}
			reqBuilder.post(formEncodingBuilder.build());
		}
		Request req = reqBuilder.build();
		Response resp = httpClient.newCall(req).execute();
		String respStr = "";
		if(resp.isSuccessful()) {
			//			System.out.println(time+= System.currentTimeMillis());
			//			System.out.println(">>> end get [" + url + "]...");
			ResponseBody respBody = resp.body();
			respStr = new String(respBody.bytes(), charset);
			respBody.close();
		}
		return respStr;
	}

	public static void doAsyncGet(OkHttpClient httpClient, String url, Map<String, String> headerMap, String charset, Callback callback, Object tag) {
		Request.Builder reqBuilder = new Request.Builder();
		reqBuilder.url(url);
		if(headerMap != null) {
			for(Entry<String, String> entry : headerMap.entrySet()) {
				reqBuilder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if(tag != null) {
			reqBuilder.tag(tag);
		}
		Request req = reqBuilder.build();
		httpClient.newCall(req).enqueue(callback);
	}

	public static void doAsyncPost(OkHttpClient httpClient, String url, Map<String, String> headerMap, Map<String, String> paramMap, String charset, Callback callback, Object tag) {
		//		long time = -System.currentTimeMillis();
		//		System.out.println(">>> start post [" + url + "]...");
		Request.Builder reqBuilder = new Request.Builder();
		reqBuilder.url(url);
		if(headerMap != null) {
			for(Entry<String, String> entry : headerMap.entrySet()) {
				reqBuilder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if(paramMap != null) {
			FormBody.Builder formEncodingBuilder = new FormBody.Builder();
			for(Entry<String, String> entry : paramMap.entrySet()) {
				formEncodingBuilder.add(entry.getKey(), entry.getValue());
			}
			reqBuilder.post(formEncodingBuilder.build());
		}
		if(tag != null) {
			reqBuilder.tag(tag);
		}
		Request req = reqBuilder.build();
		httpClient.newCall(req).enqueue(callback);
	}

	public static void doAsyncNetAccess(OkHttpClient httpClient, Request req, Callback callback) {
		httpClient.newCall(req).enqueue(callback);
	}

	public static String replaceUrlEscape(String str) {
		return str.replace("\\/", "/");
	}

	public static String processHttpRespStr(String respStr) {
		return replaceUrlEscape(decode(respStr));		
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		long cacheByteCount = 1024L * 1024L * 100L;

		//		Cache cache = new Cache(new File("cache"), cacheByteCount);
		OkHttpClient httpClient  = new OkHttpClient();
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("pd", "newsplus");
		paramMap.put("sv", "5950");
		paramMap.put("did", "E719062C62DF801B541889D16C24AC59");
		paramMap.put("cuid", "E719062C62DF801B541889D16C24AC59|081681520098368");
		paramMap.put("ver", "8");
		//paramMap.put("bduss", "mVTeW9qWVE1RThOeUc0Tkx1dVgxbjlQcWExNjhKV0xyWXBxSGdBMDBwNEh-MU5YQVFBQUFBJCQAAAAAAAAAAAEAAAA3AoUSu7bUvtau0MQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdyLFcHcixXb");
		paramMap.put("type", "tag");
		//System.out.println(NetUtil.doPost(httpClient, "http://api.baiyue.baidu.com/sn/api/getuserdata", Collections.singletonMap("Host", "api.baiyue.baidu.com"), paramMap, "utf-8"));
		NetUtil.doAsyncPost(httpClient, "http://api.baiyue.baidu.com/sn/api/getuserdata", Collections.singletonMap("Host", "api.baiyue.baidu.com"), paramMap, "utf-8", new Callback() {
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if(response.isSuccessful()) {
					System.out.println(response.body().string());
					System.out.println(response.request().toString());
				}
			}
			
			@Override
			public void onFailure(Call call, IOException e) {
				// TODO Auto-generated method stub
				
			}
		}, null);
	}
}
