package com.molbase.articlePush;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import com.molbase.articlePush.pools.ThreadPool;
import com.molbase.articlePush.tasks.FetchArticlesTask;
import com.molbase.articlePush.tasks.PublishArticleTask;
public class PushController {
	
	private static PushController controller;
	
	private Set<Integer> siteIds;
	
	private ExecutorService services;
	
	private int consumeThreadSize = 10;
	
	private PushController(){		
		siteIds = new HashSet<Integer>();
		services = ThreadPool.getPool();
	}
	
	private synchronized static void init(){
		
		if(controller == null){
			controller = new PushController();
		}
		
	}
	
	
	public static PushController getInstance(){		
		if(controller == null){
			init();
		}		
		return controller;
	}
	
	public String addSiteID(String siteId){	
		Integer id = null;
		try {
			if(!siteId.matches("\\\\d+")){
				return "输入参数必须是数字";
			}
			
			id = Integer.parseInt(siteId);
			
			if(siteIds.contains(id)){
				return id+" 已经存在！";
			}
			
			siteIds.add(id);
			services.submit(new FetchArticlesTask(id));
		} catch (Exception e) {
			e.printStackTrace();
			siteIds.remove(id);
			return  siteId + " 添加失败,请重试";
		}
		return "成功";
	}
	
	
	public void startPush(){
		
		//init()初始化所有参数
		ConfigUtils.initConfig();
		siteIds.addAll(Config.getSiteIds());
		for(Integer id : siteIds){
			services.submit(new FetchArticlesTask(id));			
		}
		
		for(int i=0;i < consumeThreadSize;i++){
			services.submit(new PublishArticleTask());
		}		
	}
	
	public void stop(){
		if(!services.isShutdown()){
			services.shutdown();
		}
	}
	
	
	public int getConsumeThreadSize() {
		return consumeThreadSize;
	}

	public void setConsumeThreadSize(int consumeThreadSize) {
		this.consumeThreadSize = consumeThreadSize;
	}
	
	
	
}
