package com.molbase.ArticlePush;
import org.testng.annotations.Test;

import com.molbase.articlePush.pools.DataSourcePool;
public class DataSourcePoolTest {

	@Test
	public void test(){
		
		System.out.println(DataSourcePool.getConnection());
		
	}
	
	
}
