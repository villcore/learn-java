package com.villcore.secret.net.v2;

import org.apache.http.client.HttpClient;

import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public class HttpClientFactory {
	public static final Logger log = Logger.getLogger(HttpClientFactory.class.getName());

	private static final String USER_AGENT = "NewsApp/8.0 Android/4.3 (V9180)";

	private static final ThreadLocal<HttpClient> threadLocalHittpClient = new ThreadLocal<HttpClient>(){
		protected HttpClient initialValue() {
			return newHttpClient();
		};
	};
	
	public static HttpClient createHttpClient() {
		//return threadLocalHittpClient.get();
		return newHttpClient();
	}
	private static HttpClient newHttpClient() {
		return HttpConnectionManager.getHttpClient();
	}
	
	public static void main(String[] args) {
		System.out.println(createHttpClient());
		System.out.println(createHttpClient());

	}
}

