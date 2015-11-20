package com.molbase.articlePush.interfaces;
import com.molbase.articlePush.ArticlePushException;
import com.molbase.articlePush.pojos.ArticleBean;
public interface ArticleDataConvertor {	
	
	public ArticleBean convertor(String value) throws ArticlePushException;
	
}
