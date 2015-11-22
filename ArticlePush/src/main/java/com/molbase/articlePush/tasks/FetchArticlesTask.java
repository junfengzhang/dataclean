package com.molbase.articlePush.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import com.molbase.articlePush.Config;
import com.molbase.articlePush.data.DataRepertory;
import com.molbase.articlePush.impls.StringConvertToArticleBean;
import com.molbase.articlePush.interfaces.ArticleDataConvertor;
import com.molbase.articlePush.pojos.ArticleBean;
import com.molbase.articlePush.pools.DataSourcePool;
public class FetchArticlesTask implements Callable<String> {

	private Logger logger = Logger.getLogger(FetchArticlesTask.class);

	private int site_id;

	private ArticleDataConvertor convertor;
	
	public FetchArticlesTask(int site_id) {
		this.site_id = site_id;
		convertor = new StringConvertToArticleBean();
	}

	public String call() throws Exception {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet resultSet = null;
		Date lastScanDate = Config.getLastscanTimeMap().get(site_id);
		try {
			connection = DataSourcePool.getConnection();			
			DataRepertory rep = DataRepertory.getInstance();
			while (true) {				
				if(!(rep.size() < Config.MIN_SIZE)){
					Thread.sleep(Config.INTERVAL * 1000);
				}
				
				pst = connection.prepareStatement(
						"select _value,update_time from common_data where site_id = ? and update_time >= ? limit 0,200");
				pst.setInt(1, site_id);
				pst.setTimestamp(2, new Timestamp(lastScanDate.getTime()));
				resultSet = pst.executeQuery();
				ArticleBean bean = null;
				
				while (resultSet.next()) {
					// 如果出现错误，记录这一次扫描的时间，以便程序以此时间开始，重新扫描
					lastScanDate = resultSet.getDate(2);
					bean = convertor.convertor(resultSet.getString(1));
					rep.put(bean);
				}
			}
		} catch (Exception e) {
			logger.error(String.format("获取网站ID：%s数据出错，最后扫描时间：%s", site_id,lastScanDate), e);			
		} finally {
			resultSet.close();
			pst.close();
			connection.close();
		}
		return null;
	}

}
