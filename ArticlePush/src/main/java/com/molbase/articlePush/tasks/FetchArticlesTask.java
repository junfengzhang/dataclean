package com.molbase.articlePush.tasks;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import com.molbase.articlePush.pools.DataSourcePool;
public class FetchArticlesTask implements Callable<String> {

	private Logger logger = Logger.getLogger(FetchArticlesTask.class);

	private int site_id;
	
	public FetchArticlesTask(int site_id){
		this.site_id = site_id;
	}
	
	
	public String call() throws Exception {		
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet resultSet = null;
		try {
			connection = DataSourcePool.getConnection();
			pst = connection.prepareStatement("select _value from common_data where site_id = ?");
			pst.setInt(1, site_id);
			resultSet = pst.executeQuery();
			while (resultSet.next()) {
				
				
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
