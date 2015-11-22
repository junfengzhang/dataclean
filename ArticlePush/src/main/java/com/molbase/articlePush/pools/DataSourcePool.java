package com.molbase.articlePush.pools;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import com.molbase.articlePush.Config;
public class DataSourcePool {

	private static BasicDataSource basicDataSource;
	
	private static DataSourcePool pool;
	
	private static Logger logger = Logger.getLogger(DataSourcePool.class);
	
	private DataSourcePool(){
		basicDataSource = new BasicDataSource();
		
		basicDataSource.setUrl(Config.DATABASE_URL);
		basicDataSource.setUsername(Config.USERNAME);
		basicDataSource.setPassword(Config.PASSWORD);
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setInitialSize(10);
		basicDataSource.setPoolPreparedStatements(true);
		basicDataSource.setMaxOpenPreparedStatements(80);				
	}
	
	
	private synchronized static void init(){
		
		if(pool == null){
			pool = new DataSourcePool();
		}
		
	}
	
	
	public static Connection getConnection(){		
		try {			
			if(pool == null){
				init();
			}			
			return basicDataSource.getConnection();
		} catch (SQLException e) {
			logger.error("获取数据库连接失败", e);
			return null;
		}
	}
	
	
}
