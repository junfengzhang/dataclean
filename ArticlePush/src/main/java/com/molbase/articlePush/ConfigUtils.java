/**  
 * 系统项目名称  
 * com.molbase.articlePush  
 * UpdateScanTimeUtils.java  
 *   
 * 2015年11月22日-下午4:25:34  
 *  2015张军峰先生-版权所有  
 *   
 */
package com.molbase.articlePush;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * UpdateScanTimeUtils
 * 
 * 张军峰 2015年11月22日 下午4:25:34
 * 
 * @version 1.0.0
 * 
 */
public class ConfigUtils {

	private static Logger logger = Logger.getLogger(ConfigUtils.class);

	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyyMMddhhmmss");

	
	public static synchronized void updateScanTime(String id, Date lastScanDate) {
		String lastScanTimes = null;
		FileOutputStream output = null;
//		URL url = ConfigUtils.class.getClassLoader().getSystemResource("config.properties");
//		String path = url.toString().substring(
//				url.toString().indexOf("file:") + 5);
		
		String path = System.getProperty("WORKDIR");
		File file = new File(path+"/last_update.properties");
		InputStream input = null;			
		Properties props = new Properties();		
		try {
			if(file.exists()){
				input = new FileInputStream(file);
			}else{
				input = ConfigUtils.class.getClassLoader()
						.getResourceAsStream("config.properties");
			}
			
			props.load(input);
			lastScanTimes = props.getProperty("LAST_TIMES");
			if (lastScanTimes.indexOf(id) != -1) {
				// Matcher m =
				// Pattern.compile(id+"=([\\d-:]+)").matcher(lastScanTimes);
				lastScanTimes = lastScanTimes.replaceFirst(id + "[\\\\]{0,1}=([\\d]+)",
						id + "=" + sdf.format(lastScanDate));
			} else {
				if (lastScanTimes.trim().endsWith("}")) {
					lastScanTimes = lastScanTimes + ",{" + id + "="
							+ sdf.format(lastScanDate) + "}";
				} else {
					lastScanTimes = "{" + id + "=" + sdf.format(lastScanDate)
							+ "}";
				}
			}
			props.setProperty("LAST_TIMES", lastScanTimes);
			output = new FileOutputStream(file);
			props.store(output, null);
		} catch (Exception e) {
			logger.error("更改扫描日期出错", e);
		} finally {
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				logger.error("关闭config配置文件输入流出错", e);
			}
		}
	}

	@SuppressWarnings("static-access")
	public static void initConfig() {
		
		URL url = ConfigUtils.class.getClassLoader().getSystemResource("config.properties");
		
		System.out.println(url.toString());
		
		InputStream input = ConfigUtils.class.getClassLoader()
				.getSystemResourceAsStream("config.properties");
		Properties props = new Properties();
		try {
			props.load(input);
			Config.setDATABASE_URL(props.getProperty("JDBC_URL").trim());
			Config.setUSERNAME(props.getProperty("USERNAME").trim());
			Config.setPASSWORD(props.getProperty("PASSWORD").trim());
			Config.setMIN_SIZE("".equals(props.getProperty("MIN_SIZE")) ? 50
					: Integer.parseInt(props.getProperty("MIN_SIZE").trim()));
			Config.setINTERVAL("".equals(props.getProperty("INTERVAL")) ? 60
					: Integer.parseInt(props.getProperty("INTERVAL").trim()));
			Config.setFETCH_SIZE("".equals(props.getProperty("FETCH_SIZE")) ? 200
					: Integer.parseInt(props.getProperty("FETCH_SIZE").trim()));
			Config.setQUEUE_SIZE("".equals(props.getProperty("QUEUE_SIZE")) ? 2000
					: Integer.parseInt(props.getProperty("QUEUE_SIZE").trim()));
			Config.setCONSUMER_SIZE("".equals(props
					.getProperty("CONSUMER_SIZE")) ? 1 : Integer.parseInt(props
					.getProperty("CONSUMER_SIZE").trim()));
			Config.setATICLE_URL(props.getProperty("ATICLE_URL").trim());
			// SITE_IDS
			String siteIds = props.getProperty("SITE_IDS").trim();
			Set<Integer> set = new HashSet<Integer>();
			for (String id : siteIds.split(",")) {
				set.add(Integer.parseInt(id));
			}
			Config.setSiteIds(set);

			// LastTime Maps
			String lastTimes = props.getProperty("LAST_TIMES").trim();
			Pattern p = Pattern.compile("(\\{\\d+=[\\d]+\\})");
			Matcher m = p.matcher(lastTimes);
			Map<Integer, Date> map = new HashMap<Integer, Date>();
			while (m.find()) {
				// int count = m.groupCount();
				String temp = "";
				String[] nameValue = null;
				// for(int i = 1;i<= count;i++){
				temp = m.group();
				temp = temp.substring(1, temp.length() - 1);
				nameValue = temp.split("=");
				if (nameValue.length == 2) {
					map.put(Integer.parseInt(nameValue[0]),
							sdf.parse(nameValue[1]));
				}
				// }
				Config.setLastscanTimeMap(map);
			}
		} catch (Exception e) {
			logger.error("更改扫描日期出错", e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				logger.error("关闭config配置文件输入流出错", e);
			}
		}
	}
}
