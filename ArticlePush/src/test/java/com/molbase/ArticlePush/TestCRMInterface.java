package com.molbase.ArticlePush;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.molbase.articlePush.http.HttpRequestAssist;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;
public class TestCRMInterface {
	
	@Test
	public void testProductPrice() throws UnsupportedEncodingException{				
		Map<String, String> map = new HashMap<String, String>();		
		map.put("mol_id", "");
		map.put("cas_no", "50-00-0");
		map.put("store_id", "");
		map.put("name_en", "");
		map.put("name_cn", "");
		map.put("page", "3");
		map.put("size", "");
		
//		String result = HttpRequestAssist.httpGet(map, "http://api88.molbase.com/price");
		try{
			String result = HttpRequestAssist.httpPost(map, "http://api88.molbase.com/price");				
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	@Test
	public void testFindMol() throws UnsupportedEncodingException{				
		Map<String, String> map = new HashMap<String, String>();		
		map.put("mol_id", "");
		map.put("cas_no", "");		
		map.put("name_en", "");
		map.put("name_cn", "");				
//		String result = HttpRequestAssist.httpGet(map, "http://api88.molbase.com/findMol");
		try{
			String result = HttpRequestAssist.httpPost(map, "http://api88.molbase.com/findMol");				
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindSku() throws UnsupportedEncodingException{				
		Map<String, String> map = new HashMap<String, String>();		
		map.put("mol_id", "");
		map.put("cas_no", "");		
		map.put("name_en", "zoledronic acid");
		map.put("name_cn", "");		
		map.put("supplier_name", "");
		map.put("user_id", "193658");
//		String result = HttpRequestAssist.httpGet(map, "http://api88.molbase.com/findMol");
		try{
			String result = HttpRequestAssist.httpPost(map, "http://api88.molbase.com/findSku");				
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	public static String ascii2native(String ascii) {  
	    int n = ascii.length() / 6;  
	    StringBuilder sb = new StringBuilder(n);  
	    for (int i = 0, j = 2; i < n; i++, j += 6) {  
	        String code = ascii.substring(j, j + 4);  
	        char ch = (char) Integer.parseInt(code, 16);  
	        sb.append(ch);  
	    }  
	    return sb.toString();  
	} 
	
	
	@Test
	public void testAll(){
		System.out.println(new String("\u4e0a\u6d77\u5e02\u6d66\u4e1c\u65b0\u533a\u8fbe\u5c14\u6587\u8def88\u53f7\u5f20\u6c5f\u534a\u5c9b\u79d1\u6280\u56ed10\u53f75\u5c42"));
		System.out.println(new Date(14332145854l));
		System.out.println(new String("\u00a5537.60"));
		System.out.println(new String("\u7532\u9187"));
	}
	
	@Test
	public void testString2unicode(){		
		String test = "￥";
		int length = test.length();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<length;i++){	
			char c = test.charAt(i);
			sb.append("\\u" +Integer.toHexString(c));			
		}		
		System.out.println(sb.toString());
		System.out.println(testUnicode2string(sb.toString()));
	}
	
	
	public String testUnicode2string(String test){		
//		String test = "\u4f60\u597d";		
		String[] arr = test.split("\\\\u");		
		StringBuilder sb = new StringBuilder();
		for(String s : arr){			
			if(!"".equals(s.trim())){
				int c = Integer.parseInt(s, 16);
				sb.append((char)c);
			}			
		}
		return sb.toString();
//		System.out.println(sb.toString());					
	}
	
	
	@Test
	public void test() throws Exception{		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("101.251.223.51");
		factory.setUsername("read");
		factory.setPassword("123456");
		factory.setVirtualHost("/test");
		
		Connection connection = factory.newConnection();		
		Channel channel = connection.createChannel();
		channel.queueDeclare("crm_molbase_pub", true, false, false, null);		
//		channel.exchangeDeclare("molbase_crm", "fanout",true);
		channel.exchangeDeclare("crm_molbase", "fanout",true);
		channel.queueBind("crm_molbase_pub", "crm_molbase", "test1");
		
		String message = "helloworld23";
		channel.basicPublish("crm_molbase", "",
				MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		/*
		String message2 = "helloworld";
		channel.basicPublish("b", "test12",
				MessageProperties.PERSISTENT_TEXT_PLAIN, message2.getBytes());*/
		channel.close();
		connection.close();
	}
	
	
	@Test
	public void testConsumeCrm(){
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("101.251.223.51");
		factory.setUsername("read");
		factory.setPassword("123456");
		factory.setVirtualHost("/test");
		Connection connection = null;
		try {
			connection = factory.newConnection();
			final Channel channel = connection.createChannel();
			channel.queueDeclare("crm_molbase_pub", true, false, false, null);	
			channel.basicQos(1);
			final Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message + "'");
					System.out.println(String.format("交换机名称：%s,路由键：%s", envelope.getExchange(),envelope.getRoutingKey()));
					try {
						// doWork(message);
					} finally {
						System.out.println(" [x] Done");
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				}

				@Override
				public void handleConsumeOk(String consumerTag) {					
					System.out.println(String.format("Consumer Tag:%s", consumerTag));					
				}				
			};
			channel.basicConsume("crm_molbase_pub", false, consumer);			
			Thread.sleep(5000);		
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	@Test
	public void testConsumeMolbase(){
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("101.251.223.51");
		factory.setUsername("read");
		factory.setPassword("123456");
		factory.setVirtualHost("/test");
		Connection connection = null;
		try {
			connection = factory.newConnection();
			final Channel channel = connection.createChannel();
			channel.queueDeclare("molbase_crm_pub", true, false, false, null);	
			channel.basicQos(1);
			final Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message + "'");
					System.out.println(String.format("交换机名称：%s,路由键：%s", envelope.getExchange(),envelope.getRoutingKey()));
					try {
						// doWork(message);
					} finally {
						System.out.println(" [x] Done");
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				}

				@Override
				public void handleConsumeOk(String consumerTag) {					
					System.out.println(String.format("Consumer Tag:%s", consumerTag));					
				}
				
			};
			channel.basicConsume("molbase_crm_pub", false, consumer);			
			Thread.sleep(5000);		
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
