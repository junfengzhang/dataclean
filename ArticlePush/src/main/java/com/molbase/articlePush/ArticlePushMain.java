package com.molbase.articlePush;
public class ArticlePushMain {
	
	public static void main(String[] args) {
		PushController controller = PushController.getInstance();
		controller.startPush();		
	}

}
