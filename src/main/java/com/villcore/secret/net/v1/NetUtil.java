package com.villcore.secret.net.v1;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetUtil {
	public static final Logger LOG = Logger.getLogger(NetUtil.class.getName());

	public static final String DEFAULT_CHARSET = "utf-8";


	public static String inputStream2Str(InputStream input, String charset){
		StringBuilder sb = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(input, charset))){
			String tmp = null;
			while((tmp = br.readLine()) != null){
				sb.append(tmp);
				sb.append("\n");
			}
		} catch (IOException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}
		return sb.toString();
	}

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

	//	public static HttpResponse doGet (HttpClient httpClient, String url, Map<String, String> headerMap) throws ClientProtocolException, IOException {
	//		HttpResponse response = null;
	//		HttpGet get = new HttpGet(url);
	//		if(headerMap != null) {
	//			setHeader(get, headerMap);
	//		}
	//		response = httpClient.execute(get);
	//		return response;
	//	}

	public static String doGet(HttpClient httpClient, String url, Map<String, String> headerMap) throws ClientProtocolException, IOException {
		int retryCount = 3;
		String respStr = "";

		while(retryCount > 0) {
			try {
				HttpResponse response = null;
				HttpGet get = new HttpGet(url);
				if(headerMap != null) {
					setHeader(get, headerMap);
				}
				setHeader(get, Collections.singletonMap("Connection", "Keep-Alive"));
				response = httpClient.execute(get);
				respStr = advancedRespToString(response, DEFAULT_CHARSET);
				break;
			}
			catch (ClientProtocolException e) {
				if(--retryCount > 0) {
					continue;
				} else {
					throw e;
				}
			}
			catch (IOException e) {
				if(--retryCount > 0) {
					continue;
				} else {
					throw e;
				}
			}
			catch (Exception e) {
				if(--retryCount > 0) {
					continue;
				} else {
					throw e;
				}
			}
		}
		return respStr;
	}

	//	public static HttpResponse doPost(HttpClient httpClient, String url, String charset, Map<String, String> headerMap, Map<String, String> paramMap) {
	//		HttpResponse response = null;
	//		HttpPost post = new HttpPost(url);
	//		if(headerMap != null) {
	//			setHeader(post, headerMap);
	//		}
	//		try {
	//			List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
	//			for(Entry<String, String> entry : paramMap.entrySet()) {
	//				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	//			}
	//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
	//			post.setEntity(entity);
	//			response = httpClient.execute(post);
	//		}catch (IOException e) {
	//			LOG.log(Level.WARNING, e.getMessage(), e);
	//		}
	//		return response;
	//	}

	public static String doPost(HttpClient httpClient, String url, String charset, Map<String, String> headerMap, Map<String, String> paramMap) throws IOException {
		String respStr = "";
		HttpResponse response = null;
		int retryCount = 3;

		while(retryCount > 0) {
			try {
				HttpPost post = new HttpPost(url);
				if(headerMap != null) {
					setHeader(post, headerMap);
				}
				setHeader(post, Collections.singletonMap("Connection", "Keep-Alive"));
				List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

				if(paramMap != null) {
					for(Entry<String, String> entry : paramMap.entrySet()) {
						params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
					}
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
				post.setEntity(entity);
				response = httpClient.execute(post);
				respStr = advancedRespToString(response, charset);
				break;
			} catch (ClientProtocolException e) {
				if(--retryCount > 0) {
					continue;
				} else {
					throw e;
				}
			}
			catch (IOException e) {
				if(--retryCount > 0) {
					continue;
				} else {
					throw e;
				}
			}
			catch (Exception e) {
				if(--retryCount > 0) {
					continue;
				} else {
					throw e;
				}
			}
		}
		return respStr;
	}

	private static void setHeader(HttpRequest request, Map<String, String> headerMap) {
		for(Entry<String, String> entry : headerMap.entrySet()) {
			request.setHeader(entry.getKey(), entry.getValue());
		}
	}

	public static String replaceUrlEscape(String str) {
		return str.replace("\\/", "/");
	}

	public static String respToString(HttpResponse response, String charset) {
		try {
			return inputStream2Str(response.getEntity().getContent(), charset);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String advancedRespToString(HttpResponse response, String charset) {
		try {
			return replaceUrlEscape(decode(inputStream2Str(response.getEntity().getContent(), charset)));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addGzipProcessor(HttpClient httpClient) {
		DefaultHttpClient httpClient2 = (DefaultHttpClient) httpClient;
		httpClient2.addRequestInterceptor(new HttpRequestInterceptor() {  

			public void process(  
					final HttpRequest request,   
					final HttpContext context) throws HttpException, IOException {  
				if (!request.containsHeader("Accept-Encoding")) {  
					request.addHeader("Accept-Encoding", "gzip");  
				}  
			}  

		});  

		httpClient2.addResponseInterceptor(new HttpResponseInterceptor() {  

			public void process(  
					final HttpResponse response,   
					final HttpContext context) throws HttpException, IOException {  
				HttpEntity entity = response.getEntity();  
				Header ceheader = entity.getContentEncoding();  
				if (ceheader != null) {  
					HeaderElement[] codecs = ceheader.getElements();  
					for (int i = 0; i < codecs.length; i++) {  
						if (codecs[i].getName().equalsIgnoreCase("gzip")) {  
							response.setEntity(  
									new GzipDecompressingEntity(response.getEntity()));   
							return;  
						}  
					}  
				}  
			}  
		});  
	}
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClientFactory.createHttpClient();
		String addr = "http://hao.360.cn";
		String test = doGet(httpClient, addr, null);
		System.out.println(test);
	}

	
}
