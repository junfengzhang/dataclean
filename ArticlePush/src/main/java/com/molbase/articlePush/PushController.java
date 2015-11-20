package com.molbase.articlePush;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import com.molbase.articlePush.pools.ThreadPool;
import com.molbase.articlePush.tasks.FetchArticlesTask;
public class PushController {
	
	private static PushController controller;
	
	private Set<Integer> siteIds;
	
	private ExecutorService services;
	
	private PushController(){		
		siteIds = new HashSet<Integer>();
		services = ThreadPool.getPool();
	}
	
	private synchronized static void init(){
		
		if(controller == null){
			controller = new PushController();
		}
		
	}
	
	
	public PushController getInstance(){		
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
		siteIds.addAll(Config.getSiteIds());
		for(Integer id : siteIds){
			services.submit(new FetchArticlesTask(id));			
		}		
	}
	
	
	
}
