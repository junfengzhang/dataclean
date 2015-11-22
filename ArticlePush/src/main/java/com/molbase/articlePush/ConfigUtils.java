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
import java.io.IOException;
import java.io.InputStream;
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
 * 张军峰  
 * 2015年11月22日 下午4:25:34  
 *   
 * @version 1.0.0  
 *   
 */
public class ConfigUtils {
	
	private static Logger logger = Logger.getLogger(ConfigUtils.class);
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static void updateScanTime(String id,Date lastScanDate){		
		String lastScanTimes = null;				
		InputStream input = ConfigUtils.class.getResourceAsStream("/config.properties");
		Properties props = new Properties();		
		try{
			props.load(input);
			lastScanTimes = props.getProperty("LAST_TIMES");
			
			if(lastScanTimes.indexOf(id) != -1){
				lastScanTimes.replaceFirst(id, sdf.format(lastScanDate));
			}else{
				if(lastScanTimes.trim().endsWith("}")){
					lastScanTimes = lastScanTimes +",{" +id +"=" + sdf.format(lastScanDate) +"}";
				}else{
					lastScanTimes = "{" +id +"=" + sdf.format(lastScanDate) +"}";
				}
			}			
			props.setProperty("LAST_TIMES", lastScanTimes);			
		}catch(Exception e){
			logger.error("更改扫描日期出错", e);
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				logger.error("关闭config配置文件输入流出错", e);
			}
		}
	}
	
	public static void initConfig(){			
		InputStream input = ConfigUtils.class.getResourceAsStream("/config.properties");
		Properties props = new Properties();		
		try{
			props.load(input);
			
			Config.setDATABASE_URL(props.getProperty("JDBC_URL"));
			Config.setUSERNAME(props.getProperty("USERNAME"));
			Config.setPASSWORD(props.getProperty("PASSWORD"));
			Config.setMIN_SIZE(Integer.parseInt(props.getProperty("MIN_SIZE")));
			Config.setINTERVAL(Integer.parseInt(props.getProperty("INTERVAL")));
			Config.setFETCH_SIZE(Integer.parseInt(props.getProperty("FETCH_SIZE")));
			Config.setQUEUE_SIZE(Integer.parseInt(props.getProperty("QUEUE_SIZE")));
			
//			SITE_IDS
			String siteIds = props.getProperty("SITE_IDS");
			Set<Integer> set = new HashSet<Integer>();
			for(String id : siteIds.split(",")){				
				set.add(Integer.parseInt(id));
			}			
			Config.setSiteIds(set);
			
			//LastTime Maps
			String lastTimes = props.getProperty("LAST_TIMES");			
			Pattern p = Pattern.compile(".*({\\d+=[\\d ]+}).*");
			Matcher m = p.matcher(lastTimes);
			if(m.matches()){
				int count = m.groupCount();
				String temp = "";
				Map<Integer, Date> map = new HashMap<Integer, Date>();
				String[] nameValue = null;
				for(int i = 1;i<= count;i++){
					temp = m.group(i);
					temp = temp.substring(1, temp.length()-1);
					nameValue = temp.split("=");
					if(nameValue.length == 2){
						map.put(Integer.parseInt(nameValue[0]), sdf.parse(nameValue[1]));
					}
				}	
				Config.setLastscanTimeMap(map);
			}						
		}catch(Exception e){
			logger.error("更改扫描日期出错", e);
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				logger.error("关闭config配置文件输入流出错", e);
			}
		}
	}
}
