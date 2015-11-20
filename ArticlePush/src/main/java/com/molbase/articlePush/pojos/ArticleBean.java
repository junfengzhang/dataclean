package com.molbase.articlePush.pojos;

public class ArticleBean {

	private String username = "admin";
	
	private String title;
	
	private String content;
	
	private String author;
	
	private String summary;//文章摘要
	
	private String catid;//文章所属频道
	
	private String dateline;//文章发布时间格式
	
	private String token;//Md5加密(dateline的值+molbase2015)
	
	private String htmlon;//值为:0不允许运行html代码1允许运行html代码,默认为0

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHtmlon() {
		return htmlon;
	}

	public void setHtmlon(String htmlon) {
		this.htmlon = htmlon;
	}
	
}
