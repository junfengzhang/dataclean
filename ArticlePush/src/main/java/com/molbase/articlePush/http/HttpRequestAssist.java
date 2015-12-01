package com.molbase.articlePush.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import com.molbase.articlePush.impls.StringConvertToArticleBean;
import com.molbase.articlePush.pojos.ResultMsg;

public class HttpRequestAssist {

	private static Logger logger = Logger.getLogger(HttpRequestAssist.class);

	public static ResultMsg post(Map<String, String> paramsMap, String url) {
		CloseableHttpClient client = HttpClientPool.getHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			if (paramsMap != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (String key : paramsMap.keySet()) {
					nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
				}
				post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				CloseableHttpResponse response = client.execute(post);
				
				StatusLine status = response.getStatusLine();
				if(status.getStatusCode() == 200){
					HttpEntity entity = response.getEntity();					
					return StringConvertToArticleBean
							.msgConvertor(consumeEntity(entity));
				}else{
					logger.error(String.format("请求URL:%s时状态码不正确", url));
				}
			} else {
				ResultMsg msg = new ResultMsg();
				msg.setMsg("参数为空");
				return msg;
			}
		} catch (Exception e) {
			logger.error("POST请求时出错", e);
		}
		return null;
	}

	public static String httpPost(Map<String, String> paramsMap, String url) {
		CloseableHttpClient client = HttpClientPool.getHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (String key : paramsMap.keySet()) {
				nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			CloseableHttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					entity.getContent(),"utf-8"));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("POST请求时出错", e);
		}
		return null;
	}

	public static String httpGet(Map<String, String> paramsMap, String url) {
		CloseableHttpClient client = HttpClientPool.getHttpClient();		
		try {		
			StringBuilder sb = new StringBuilder(url);
			int size = paramsMap.size();
			if(size > 0){
				sb.append("?");
			}
			int i = 1;
			for(String key : paramsMap.keySet()){								
				if(i == size){
					sb.append(key+"="+paramsMap.get(key));
					break;
				}else{
					sb.append(key+"="+paramsMap.get(key)+"&");
				}
				i++;
			}
			HttpGet get = new HttpGet();
			get.setURI(new URI(sb.toString()));
			CloseableHttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					entity.getContent(),"utf-8"));
			StringBuilder result = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				result.append(temp);
			}
			return result.toString();
		} catch (Exception e) {
			logger.error("GET请求时出错", e);
		}
		return null;

	}

	private static String consumeEntity(HttpEntity entity) {
		InputStream inputStream = null;
		try {
			inputStream = entity.getContent();
			byte[] buff = new byte[1024];
			int b = inputStream.read(buff);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int index = 0;
			while (b != -1) {
				output.write(buff, index, b);
				index = index + b;
				b = inputStream.read(buff);
			}
			return new String(output.toByteArray(), "utf-8");
		} catch (Exception e) {
			logger.error("提取响应内容时出错", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return null;
	}

}
