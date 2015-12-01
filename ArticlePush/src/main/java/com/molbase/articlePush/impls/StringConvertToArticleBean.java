package com.molbase.articlePush.impls;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.molbase.articlePush.ArticlePushException;
import com.molbase.articlePush.ConfigUtils;
import com.molbase.articlePush.MD5Utils;
import com.molbase.articlePush.interfaces.ArticleDataConvertor;
import com.molbase.articlePush.pojos.ArticleBean;
import com.molbase.articlePush.pojos.ResultMsg;
public class StringConvertToArticleBean implements ArticleDataConvertor {
		
	private static ObjectMapper om = new ObjectMapper();	
	private Logger logger = Logger.getLogger(StringConvertToArticleBean.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArticleBean convertor(String value){
		
		try {
			Map<String, Object> map = om.readValue(value, Map.class);			
			if(!map.containsKey("article")){
				return null;
			}
			Map<String, String> articleMap = (Map)map.get("article");
			
			ArticleBean bean = new ArticleBean();							
			bean.setContent(articleMap.get("content"));
			bean.setTitle(articleMap.get("title"));
			bean.setSummary(articleMap.get("description"));
			bean.setUsername("admin");
			bean.setCatid("14");
			
			String dateLine = ConfigUtils.sdf.format(new Date());
			bean.setDateline(dateLine);
			bean.setToken(MD5Utils.md5sign(dateLine + "molbase2015"));
			bean.setHtmlon("0");			
			return bean;
		} catch (Exception e) {						
			logger.error("转换到ArticleBean出错",e);
			return null;
		}		
	}

	public static ResultMsg msgConvertor(String values) throws ArticlePushException{		
		try {
			return om.readValue(values, ResultMsg.class);
		} catch (Exception e) {
			throw new ArticlePushException("转换到ResultMsg出错",e);
		}
	}
	
	
}
