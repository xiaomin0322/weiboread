package weiboread;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;

public class IpUtils {
	
	public static void main(String[] args) {
		getIpsmemories1999();
	}

	// http://ip.memories1999.com/api.php?dh=2764810913906166&sl=2&xl=%E5%9B%BD%E5%86%85&gl=1
	public static List<HttpHost> getIpsmemories1999() {
		//return getips("http://ip.memories1999.com/api.php?dh=2764810913906166&sl="+Execute.poolSize+"&xl=%E5%9B%BD%E5%86%85&gl=1");
		String url = PropertiesUtil.getPropertiesMap("ipurlrrz");
		return getips(url);
	}

	public  static List<HttpHost> getips(String url) {
		
		System.out.println("ip url =="+url);

		try {
			List<HttpHost> hosts = new ArrayList<HttpHost>();
			String str = getIpStr(url);
			
			if(StringUtils.isBlank(str)){
				System.out.println("获取ip是空>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}

			//System.out.println("get ipstr :" + str);

			String[] strs = str.split("\r\n");
			for (String ipstr : strs) {
				String[] ip = ipstr.split(":");
				HttpHost e = new HttpHost(ip[0], Integer.parseInt(ip[1]));
				hosts.add(e);
			}
			return hosts;
		} catch (Exception e) {
			System.out.println("获取ip有问题>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			e.printStackTrace();
		}
		return null;
	}

	public static String getIpStr(String url) {
		String urlStr = new HttpTest().getContentByUrl(null, new HttpGet(url));
		return urlStr;
	}

}
