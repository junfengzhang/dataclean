package com.molbase.articlePush.http;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
public class HttpClientPool {
	
	private static PoolingHttpClientConnectionManager pcm;
	
	public static CloseableHttpClient getHttpClient(){	
		
		if(pcm == null){
			pcm = new PoolingHttpClientConnectionManager();
			pcm.setMaxTotal(400);
			pcm.setDefaultMaxPerRoute(pcm.getMaxTotal());		
			pcm.setValidateAfterInactivity(1000);
			
		}
		
		Builder configBuilder = RequestConfig.custom().setConnectionRequestTimeout(3000).setConnectTimeout(3000).setSocketTimeout(3000);		
		RequestConfig config = configBuilder.build();
		HttpClientBuilder builder = HttpClients.custom();
		builder.setDefaultRequestConfig(config);		
		builder.setConnectionManager(pcm);		
		CloseableHttpClient httpClient = builder.build();
		return httpClient;		
	}
}
