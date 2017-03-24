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

	public static int poolSize = Integer.valueOf(PropertiesUtil.getPropertiesMap("threadPoolSize"));
	
	public static List<String> weiboUrlList = null;
	
	static{
		String weibourl = PropertiesUtil.getPropertiesMap("weiboUrl");
		weiboUrlList=Arrays.asList(StringUtils.split(weibourl,","));
		System.out.println("微博地址:"+weiboUrlList);
		System.out.println("线程数："+poolSize);
	}
	
	static String adslUser = PropertiesUtil.getPropertiesMap("adslUser");
	
	static String adslPwd = PropertiesUtil.getPropertiesMap("adslPwd");
	
	static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors
			.newFixedThreadPool(poolSize);
	

	public static void main(String[] args) {
		while(true){
			try{
				ConnectNetWork.cutAdsl("宽带连接");  
				Thread.sleep(2000);
				ConnectNetWork.connAdsl("宽带连接",adslUser,adslPwd); 
				Thread.sleep(2000);
				execteAdsl();
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
	
	public static void execute(){
		Runnable runnable = new Runnable() {
			public void run() {
				try{
				// task to run goes here
				//System.out.println("Hello !!");
				int queueSize = threadPoolExecutor.getQueue().size();
				System.out.println("queueSize : " + queueSize);

				if(queueSize < poolSize){
					for (final String u:weiboUrlList) {
						threadPoolExecutor.execute(new Runnable() {
							public void run() {
								WebClient webClient = null;
								try {
									webClient =  HtmlUnitUtil.create();
										try{
											System.out.println(" url "+u+" 刷》》》》》》》》》》》》》》》");
											HtmlPage htmlPage = webClient.getPage(u);
											String ps = htmlPage.asXml();
											System.out.println(ps);
											Thread.sleep(5000+(new Random().nextInt(5000)));
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
						});
					}
				}else{
					System.out.println("线程池队列容量充足>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		
		
		
		
		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable, 2, 10, TimeUnit.SECONDS);
	}
}
