package com.molbase.articlePush.tasks;
import java.util.concurrent.Callable;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import com.molbase.articlePush.Config;
import com.molbase.articlePush.data.DataRepertory;
import com.molbase.articlePush.http.HttpClientPool;
import com.molbase.articlePush.pojos.ArticleBean;
public class PublishArticleTask implements Callable<String> {
	
		
	public String call() throws Exception {
		
		DataRepertory dataRep = DataRepertory.getInstance();
		ArticleBean bean = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		while(true){
			
			bean = dataRep.take();			
			client = HttpClientPool.getHttpClient();
			HttpPost post = new HttpPost(Config.ATICLE_URL);
			response = client.execute(post);
			
			
			break;			
		}
		
		
		
		
		
		return null;
	}
}
