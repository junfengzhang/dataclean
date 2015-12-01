package com.molbase.ArticlePush;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.testng.annotations.Test;

import com.molbase.articlePush.http.HttpRequestAssist;
public class HttpClientTest {

	@Test
	public void test(){
		
		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();		
		manager.setMaxTotal(200);
	    // 将每个路由基础的连接增加到20
		manager.setDefaultMaxPerRoute(20);
	    //将目标主机的最大连接数增加到50
	    HttpHost localhost = new HttpHost("", 80);
	    manager.setMaxPerRoute(new HttpRoute(localhost), 50);
	    
		
		
		
		
		
	}
	
	
	@Test
	public void test2(){
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("a", "b");
		
		HttpRequestAssist.post(map, "http://localhost:8081/TestWeb/test");
		
		
		
		
		
	}
	
	
}
