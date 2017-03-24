package weiboread;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class PropertiesUtil {
	public static final Map<String, String> propertiesMap = new HashMap<String, String>();
	private static final PropertiesUtil propertiesUtil = new PropertiesUtil();

	public PropertiesUtil() {
		//Properties systemConfig = PropertiesUtil.getProperty("/configParameterSys.properties");
		Properties systemConfig=null;
		try {
			File file = new File("c:\\WeiboRead.properties");
			if(!file.exists()){
				System.err.println("load /WeiboRead.properties >>>>>>>>>>>>>>>>>>>>>>>>>>");
				file = new File("/WeiboRead.properties");
			}
			systemConfig = PropertiesUtil.getProperty(FileUtils.openInputStream(
					file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		propertiesMap.putAll((Hashtable) systemConfig);
		System.out.println("propertiesMap :"+propertiesMap);
	}

	public static String getPropertiesMap(String key) {
		return propertiesMap.get(key);
	}

	public static PropertiesUtil getPropertiesUtil() {
		return propertiesUtil;
	}

	public static Properties getProperty(InputStream inputStream) {
		Properties p = new Properties();
		try {
			// PropertiesUtil.class.getClassLoader().getResourceAsStream("/cpu_cost_control.properties");
			p.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}
	
	
	public static Properties getProperty(String path) {
		InputStream inputStream = null;
		Properties p = new Properties();
		URL plugin = PropertiesUtil.class.getResource(path);
		try {
			inputStream = plugin.openStream();
			// PropertiesUtil.class.getClassLoader().getResourceAsStream("/cpu_cost_control.properties");
			p.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public static List<String> getPropertyValues(String name) {
		Properties properties = getProperty(name);
		List<String> valueList = new ArrayList<String>();
		Enumeration<Object> enu = properties.elements();
		while (enu.hasMoreElements()) {
			valueList.add((String) enu.nextElement());
		}
		return valueList;
	}

	public static void main(String[] args) {
		System.out.println(Boolean.parseBoolean("true"));
		System.out.println(Boolean.parseBoolean(""));
	}

}
