package com.molbase.articlePush.interfaces;
import com.molbase.articlePush.ArticlePushException;
import com.molbase.articlePush.pojos.ArticleBean;
import com.molbase.articlePush.pojos.ResultMsg;
public interface ArticlePush {
		
	public ResultMsg push(ArticleBean article) throws ArticlePushException;
		
}
