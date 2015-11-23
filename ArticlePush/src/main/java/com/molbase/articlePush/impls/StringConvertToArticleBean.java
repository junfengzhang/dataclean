package com.molbase.articlePush.impls;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.molbase.articlePush.ArticlePushException;
import com.molbase.articlePush.ConfigUtils;
import com.molbase.articlePush.MD5Utils;
import com.molbase.articlePush.interfaces.ArticleDataConvertor;
import com.molbase.articlePush.pojos.ArticleBean;
import com.molbase.articlePush.pojos.ResultMsg;
public class StringConvertToArticleBean implements ArticleDataConvertor {
		
	private static ObjectMapper om = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	public ArticleBean convertor(String value) throws ArticlePushException {
		
		try {
			Map<String, String> map = om.readValue(value, Map.class);
			ArticleBean bean = new ArticleBean();							
			bean.setContent(map.get("content"));
			bean.setTitle(map.get("title"));
			bean.setSummary(map.get("description"));
			bean.setUsername("admin");
			bean.setCatid("14");
			
			String dateLine = ConfigUtils.sdf.format(new Date());
			bean.setDateline(dateLine);
			bean.setToken(MD5Utils.md5sign(dateLine + "molbase2015"));
			bean.setHtmlon("0");
			
			return bean;
		} catch (Exception e) {						
			throw new ArticlePushException("转换到ArticleBean出错",e);
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
