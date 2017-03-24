package weiboread;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpTest {

	// HttpHost host = new HttpHost("103.28.149.118", 8080);

	public HttpHost host = new HttpHost("58.154.213.21", 808);;
	public String aurl = "http://www.qtmzmb.cn/read/182075/1391589.html";
	
	public String X_Forwarded_For = null;

	public String aid = "182075";

	public String uid = "1391589";
	
	public String ua = getUserAgent();

	public static void main(String[] args) throws Exception {
		HttpTest httpTest = new HttpTest();
		httpTest.test();
		
		//new Http().executeAll();
		// execute();
		// getAidAndUid();
		// System.out.println(X_Forwarded_For);
		
		
		for(int i=0;i<100;i++){
			//System.out.println(new HttpTest().getUserAgent());
		}
	}

	public void executeAll() throws Exception {
		HttpTest http = new HttpTest();
		http.aurl = "http://www.qtmzmb.cn/read/182151/1391589.html";
		http.execute();
	}

	public void test() throws Exception {
		HttpGet httpReq = new HttpGet(
				"http://haitmall.com/detail.php?aid=186&uid=203");
		HttpTest http = new HttpTest();
		String s = http.getContentByUrl(host, httpReq);
		// String s = HttpClientEx.getContentByUrl(host, httpReq);
		System.out.println(s);
		
		if(StringUtils.isNotBlank(s)){
			executeAll();
		}
		
	}


	public void getAidAndUidAndHost() {
		String[] strs = aurl.replace(".html", "").split("\\/");
		System.out.println(Arrays.toString(strs));

		aid = strs[strs.length - 2];
		uid = strs[strs.length - 1];

		System.out.println("aid=" + aid + "  uid=" + uid);
		
		X_Forwarded_For = host.getHostName();
		
		System.out.println("X_Forwarded_For=" + X_Forwarded_For);
	}

	public void execute() throws Exception {

		getAidAndUidAndHost();

		HttpGet httpReq = new HttpGet(aurl);
		// http://www.stilllistener.com/checkpoint1/
		// HttpGet httpReq = new
		// HttpGet("http://www.stilllistener.com/checkpoint1/");
		// HttpHost host = new HttpHost("103.28.149.118", 8080);
		// HttpHost host = new HttpHost("122.226.189.97", 135);

		System.out.println("==============httpReq======================");

		// String s = getContentByUrl(null, httpReq);
		setHeaderqtmzmb(httpReq);
		String s = getContentByUrl(host, httpReq);

		//System.out.println(s);

		System.out.println("==============httpReqCount======================");

		HttpGet httpReqCount = new HttpGet(
				"http://rrz27.t7yb.net/count.php?id=" + aid
						+ "&dm=www.qtmzmb.cn");
		setHeaderCount(httpReqCount);
		s = getContentByUrl(host, httpReqCount);

		System.out.println(s);

		System.out.println("=================httpReqEnd===================");

		HttpGet httpReqgb = new HttpGet("http://gd87397616.cn/ac.php?uid="
				+ uid + "&aid=" + aid);
		setHeadergd(httpReqgb);
		s = getContentByUrl(host, httpReqgb,getRandomSleep());

		System.out.println(s);
	}
	
	public String getContentByUrl(HttpHost proxy, HttpRequestBase httpReq) {
		return getContentByUrl(proxy, httpReq, 10);
	}
	
	public long getRandomSleep(){
		return new Random().nextInt(5000)+1000;
	}
	
	static List<String> androidVersion = new ArrayList<String>();
	
	static{
	androidVersion.add("Mozilla/5.0 (Linux; U; Android 4.0.1; ja-jp; Galaxy Nexus Build/ITL41D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN        ");
	androidVersion.add("Mozilla/5.0 (Linux; U; Android 4.0.3; ja-jp; URBANO PROGRESSO Build/010.0.3000) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN");
	androidVersion.add("Mozilla/5.0 (Linux; U; Android 4.0.3; ja-jp; Sony Tablet S Build/TISU0R0110) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN          ");
	androidVersion.add("Mozilla/5.0 (Linux; U; Android 4.0.4; ja-jp; SC-06D Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN              ");
	androidVersion.add("Mozilla/5.0 (Linux; U; Android 4.1.1; ja-jp; Galaxy Nexus Build/JRO03H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN        ");
	androidVersion.add("Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03S) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN                     ");
	androidVersion.add("Mozilla/5.0 (iPad; CPU OS 5_0_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A405 Safari/7534.48.3 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN                        ");
	androidVersion.add("Mozilla/5.0 (iPad; CPU OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN                        ");
	androidVersion.add("Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A403 Safari/8536.25 MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN                           ");
	}
	
	
	
	public String getUserAgent(){
		//String ua = "Mozilla/5.0 (Linux; Android 6.0; ALE-TL00 Build/HuaweiALE-TL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile MQQBrowser/6.8 TBS/036872 Safari/537.36 MicroMessenger/6.3.31.940 NetType/WIFI Language/zh_CN";

		String ua = androidVersion.get(new Random().nextInt(androidVersion.size()));
		
		//System.out.println("getUserAgent==="+ua);
		return ua;
	}

	public void setHeaderCount(HttpRequestBase request) {
		request.setHeader(
				"User-Agent",
				ua);
		request.setHeader("Host", "rrz27.t7yb.net");
		request.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-encoding", "gzip, deflate");
		request.setHeader("X-Forwarded-For", X_Forwarded_For);
		request.setHeader("Referer", aurl);
	}

	public void setHeaderqtmzmb(HttpRequestBase request) {
		request.setHeader(
				"User-Agent",
				ua);
				request.setHeader("Host", "www.qtmzmb.cn");
		request.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-encoding", "gzip, deflate");
		request.setHeader("X-Forwarded-For", X_Forwarded_For);
	}

	public void setHeadergd(HttpRequestBase request) {
		request.setHeader(
				"User-Agent",
				ua);
		request.setHeader("Host", "gd87397616.cn");
		request.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-encoding", "gzip, deflate");
		request.setHeader("X-Forwarded-For", X_Forwarded_For);
		request.setHeader("Referer", aurl);
	}

	public String getContentByUrl(HttpHost proxy, HttpRequestBase httpReq,long sleepTime) {

		/*CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("localhost", 8080),
				new UsernamePasswordCredentials("username", "password"));
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider).build();*/
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
	
		//请求超时
		//httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); 
		//读取超时
		//httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		
		//链接超时
		//httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);  
		//读取超时
		//httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000)
		HttpEntity entity2 = null;
		HttpResponse response =null;
		try {
			// https://us.fotolia.com/ //https://us.fotolia.com/id/35642224
			// HttpHost proxy = new HttpHost("117.185.124.77", 8088);
			// HttpHost httpHost = new HttpHost("www.fotolia.com", 443,
			// "https");
			// HttpHost proxy = new HttpHost("153.121.75.130",8080);
			// HttpGet httpget = new HttpGet("/id/35642224");
			
			org.apache.http.client.config.RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setSocketTimeout(30000)
					.setConnectionRequestTimeout(30000).setConnectTimeout(30000);//设置请求和传输超时时间
			if (null != proxy) {
				requestConfigBuilder.setProxy(proxy);
			}
			
			httpReq.setConfig(requestConfigBuilder.build());
			//System.out.println("Executing request " + httpReq.getRequestLine()+ " to " + httpReq.getURI() + " via " + proxy);
			// httpclient.execute(httpHost,
			// httpReq,MuResponseHanlder.responseHandler(filesavePath));

			 response = httpclient.execute(httpReq);
			 entity2 = response.getEntity();
			String entityBody = EntityUtils.toString(entity2, "utf-8");//
			
			//response.getEntity().getContent().close();
			return entityBody;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			
			if(entity2!=null){
				try {
					EntityUtils.consumeQuietly(response.getEntity());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			

			try {
				httpReq.releaseConnection();
				Thread.sleep(sleepTime);
				//Thread.sleep(new Random().nextInt(5000)+1000);
				httpclient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;

	}

}
