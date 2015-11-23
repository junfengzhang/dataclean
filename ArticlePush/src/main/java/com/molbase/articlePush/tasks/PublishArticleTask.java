package com.molbase.articlePush.tasks;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import com.molbase.articlePush.Config;
import com.molbase.articlePush.data.DataRepertory;
import com.molbase.articlePush.http.HttpRequestAssist;
import com.molbase.articlePush.pojos.ArticleBean;
import com.molbase.articlePush.pojos.ResultMsg;
public class PublishArticleTask implements Callable<String> {
	
		
	public String call() throws Exception {		
		DataRepertory dataRep = DataRepertory.getInstance();
		ArticleBean bean = null;
		Map<String, String> map = new HashMap<String, String>();
		ResultMsg rm = null;
		while(true){			
			bean = dataRep.take();//如果没有数据就阻塞			
			map.put("username", bean.getUsername());
			map.put("title", bean.getTitle());
			map.put("content", bean.getContent());
			map.put("author", bean.getAuthor());
			map.put("summary", bean.getSummary());
			map.put("catid", bean.getCatid());
			map.put("dateline", bean.getDateline());
			map.put("token", bean.getToken());
			map.put("htmlon", bean.getHtmlon());
			rm = HttpRequestAssist.post(map, Config.ATICLE_URL);	
			if(!("1".equals(rm.getCode()))){
				
			}
		}
	}
}
