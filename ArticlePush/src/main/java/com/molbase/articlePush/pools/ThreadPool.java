package com.molbase.articlePush.pools;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class ThreadPool{

	private static ThreadPool pool;
	
	private ExecutorService service;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ThreadPool(){
		service = new ThreadPoolExecutor(20,
				100,
				5, TimeUnit.SECONDS,
				new ArrayBlockingQueue(100),				
				new ThreadPoolExecutor.AbortPolicy());
		
	}
	
	
	private static synchronized void init(){		
		if(pool == null){
			pool = new ThreadPool();
		}		
	}
	
	public static ExecutorService getPool(){		
		if(pool == null){
			init();
		}		
		return pool.service;
	}
}
