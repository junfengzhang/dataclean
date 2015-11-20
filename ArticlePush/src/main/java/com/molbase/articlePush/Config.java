package com.molbase.articlePush;
import java.util.Set;
public class Config {

	public static int FETCH_SIZE ;
	
	public static int QUEUE_SIZE ;
	
	public static int MIN_SIZE ;
		
	public static int INTERVAL;//扫描数据间隔 单位秒
	
	public static String DATABASE_URL;
	
	public static String USERNAME;
	
	public static String PASSWORD;
	
	private static Set<Integer> siteIds;
	
	public static Set<Integer> getSiteIds() {
		return siteIds;
	}

	public static void setSiteIds(Set<Integer> siteIds) {
		Config.siteIds = siteIds;
	}

	public static int getFETCH_SIZE() {
		return FETCH_SIZE;
	}

	public static void setFETCH_SIZE(int fETCH_SIZE) {
		FETCH_SIZE = fETCH_SIZE;
	}

	public static int getQUEUE_SIZE() {
		return QUEUE_SIZE;
	}

	public static void setQUEUE_SIZE(int qUEUE_SIZE) {
		QUEUE_SIZE = qUEUE_SIZE;
	}

	public static int getMIN_SIZE() {
		return MIN_SIZE;
	}

	public static void setMIN_SIZE(int mIN_SIZE) {
		MIN_SIZE = mIN_SIZE;
	}

	public static int getINTERVAL() {
		return INTERVAL;
	}

	public static void setINTERVAL(int iNTERVAL) {
		INTERVAL = iNTERVAL;
	}

	public static String getDATABASE_URL() {
		return DATABASE_URL;
	}

	public static void setDATABASE_URL(String dATABASE_URL) {
		DATABASE_URL = dATABASE_URL;
	}

	public static String getUSERNAME() {
		return USERNAME;
	}

	public static void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
}
