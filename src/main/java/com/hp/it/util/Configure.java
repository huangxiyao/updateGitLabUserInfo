package com.hp.it.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configure {
	// action�����ļ�·��  
	public static final String ACTIONPATH = "conf.properties";  
	
	static Logger log = LoggerFactory.getLogger(Configure.class);
	
	public static HashMap<String, String> map = new HashMap<String, String>();
	
	// �����ļ�   
	public static Properties prop = new Properties();  
	 static InputStream fis = null;
	static {
		//��ȡ��ǰ����صĸ�Ŀ¼���磺/C:/Program Files/Apache/Tomcat 6.0/webapps/fee/WEB-INF/classes/  
		String path;
		try {
			URL u = Thread.currentThread().getContextClassLoader().getResource("conf.properties");
			fis=Configure.class.getResourceAsStream("conf.properties");
			// ���ļ������ļ��������������ڴ���  
			if(u == null){
				fis = new FileInputStream("conf/conf.properties");
				PropertyConfigurator.configure(new FileInputStream("conf/log4j.properties"));
			}else{
				path = u.getPath();
				fis = new FileInputStream(path);
			}
			//�����ļ���������     
			prop.load(fis);
			//����propers ���洢��map��
			Enumeration enu2=prop.propertyNames();
			while(enu2.hasMoreElements()){
			    String key = (String)enu2.nextElement();
			    String value = prop.getProperty(key);
			    map.put(key, value);
			    log.debug(key+":"+value);
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		   
		
	}
	
	public static String get(String key){
		return map.get(key);
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		// ���ļ������ļ��������������ڴ���  
		//fis = Configure.class.getClassLoader().getResourceAsStream("conf.properties");
		System.out.println(fis);
	}
}
