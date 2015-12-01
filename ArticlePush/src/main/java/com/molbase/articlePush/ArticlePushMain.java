package com.molbase.articlePush;

import java.net.URL;
public class ArticlePushMain {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		URL url = ArticlePushMain.class.getClassLoader().getSystemResource("log4j.properties");		
		String path = url.toString().substring(0, url.toString().lastIndexOf("/"));
		path = path.substring(path.indexOf("file:")+5, path.lastIndexOf("/"));
		System.setProperty ("WORKDIR", path+"/article_push");		
		PushController controller = PushController.getInstance();
		controller.startPush();
		
//		System.out.println(System.getProperty("WORKDIR"));
		
	}

}
