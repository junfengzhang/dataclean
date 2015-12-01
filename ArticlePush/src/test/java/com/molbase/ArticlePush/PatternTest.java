package com.molbase.ArticlePush;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.testng.annotations.Test;

import com.molbase.articlePush.ArticlePushException;
import com.molbase.articlePush.ConfigUtils;
import com.molbase.articlePush.impls.StringConvertToArticleBean;
import com.molbase.articlePush.pojos.ResultMsg;
public class PatternTest {

	
	@Test
	public void test(){
		
		Pattern p = Pattern.compile("(\\{\\d+=[\\d -:]+\\})");
		
		Matcher m = p.matcher("{11780=2015-11-18 09:21:24},{11781=2015-11-18 09:21:24}");
		
		m.find();
		System.out.println(m.group());
		
		m.find();
		System.out.println(m.group());
		
		
//		int count = m.groupCount();		
//		System.out.println(count);
//		System.out.println(m.group(0));
//		m.find();
//		System.out.println(m.group(0));
		
		
		
	}
	
	@Test
	public void test2() throws Exception{
		
		StringConvertToArticleBean bean = new StringConvertToArticleBean();
		ResultMsg msg = bean.msgConvertor("{\"code\": \"1\",\"msg\":\"成功\" }");
		System.out.println(msg.getCode()+ "  =="+msg.getMsg());
	}
	
	@Test
	public void test3() throws Exception{
		
//		ConfigUtils.updateScanTime("11780", new Date());
//
//		URL  url = PatternTest.class.getClassLoader().getSystemResource("config.properties");
//		
//		String path = url.toString().substring(url.toString().indexOf("file:")+5);
//		System.out.println(path);
//		
		 SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyyMMddhhmmss");
		 
		String lastScanTimes = "{11780\\=20151118092124},{11783=20151118092124},{11786=20151118092124},{11789=20151118092124}";
		lastScanTimes = lastScanTimes.replaceFirst("11780" + "[\\\\]{0,1}=([\\d]+)",
				"11780" + "=" + sdf.format(new Date()));
		System.out.println(lastScanTimes);
		
	}
	
	
}
