package weiboread.adsl;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import util.HtmlUnitUtil;
import weiboread.PropertiesUtil;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WeiboReadExecute {

	
	public static List<String> weiboUrlList = null;
	
	static{
		String weibourl = PropertiesUtil.getPropertiesMap("weiboUrl");
		weiboUrlList=Arrays.asList(StringUtils.split(weibourl,","));
		System.out.println("微博地址:"+weiboUrlList);
	}
	
	static String adslUser = PropertiesUtil.getPropertiesMap("adslUser");
	
	static String adslPwd = PropertiesUtil.getPropertiesMap("adslPwd");
	
	

	public static void main(String[] args) {
		while(true){
			try{
				Thread.sleep(3000);
				boolean flag = ConnectNetWork.cutAdsl("宽带连接");  
				if(flag){
					Thread.sleep(2000);
					if(ConnectNetWork.connAdsl("宽带连接",adslUser,adslPwd)){
						Thread.sleep(3000);
						execteAdsl();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void execteAdsl(){
		for (final String u:weiboUrlList) {
			WebClient webClient = null;
			try {
				webClient =  HtmlUnitUtil.create();
					try{
						System.out.println(" url "+u+" 刷》》》》》》》》》》》》》》》");
						HtmlPage htmlPage = webClient.getPage(u);
						//String ps = htmlPage.asXml();
						//System.out.println(ps);
						Thread.sleep(2000+(new Random().nextInt(2000)));
					}catch(Exception e){
						e.printStackTrace();
					}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(webClient!=null){
					webClient.closeAllWindows();
				}
			}
		}
	}
	
}
