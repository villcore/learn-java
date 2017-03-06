package com.villcore.secret.net.v1;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

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
		return threadLocalHittpClient.get();
		//return newHttpClient();
	}

	private static HttpClient newHttpClient() {
		HttpClient customHttpClient = null;
		HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpProtocolParams.setUserAgent(params, USER_AGENT);

		HttpConnectionParams.setConnectionTimeout(params, 50 * 1000);
		HttpConnectionParams.setSoTimeout(params, 50 * 1000);

		SchemeRegistry scheReg = new SchemeRegistry();
		scheReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		scheReg.register(new Scheme("https", DefaultSSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, scheReg);

		customHttpClient = new DefaultHttpClient(conMgr, params);
		HttpClientParams.setCookiePolicy(customHttpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY); 

		return customHttpClient;	
	}
	
	public static void main(String[] args) {
		System.out.println(createHttpClient());
		System.out.println(createHttpClient());
	}
}

