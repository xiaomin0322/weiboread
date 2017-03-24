package util;

import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitUtil {

	static Logger logger = Logger.getLogger(HtmlUnitUtil.class);

	public static void setProxy(WebClient webClient,String host,int port) {
		ProxyConfig proxyConfig = webClient.getOptions().getProxyConfig();
		proxyConfig.setProxyHost(host);
		proxyConfig.setProxyPort(port);
		//DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
		//credentialsProvider.addCredentials(proxy.getUser(), proxy.getPassword());
	}
	
	public static WebClient create(String host,int port) {
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(
				Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(
				Level.OFF);

		// LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
		// java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17,host,port);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setCssEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setTimeout(60000);
		webClient.setJavaScriptTimeout(60000);
		webClient.waitForBackgroundJavaScript(120000);
		return webClient;
	}
	
	
	public static WebClient create() {
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(
				Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(
				Level.OFF);

		// LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
		// java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setCssEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setTimeout(60000);
		webClient.setJavaScriptTimeout(60000);
		webClient.waitForBackgroundJavaScript(120000);
		return webClient;
	}
	
	public static void main(String[] args)throws Exception {
		 WebClient client = HtmlUnitUtil.create();
		//Page client.getPage("http://vividscreen.info/pic/intel-core-i7/376/");
	}

	public static boolean setAttributeVal(HtmlPage htmlPage, String xpath,
			String value) {
		return setAttribute(htmlPage, xpath, "value", value);
	}


	protected void setInput(HtmlPage page, String uid, String password) {
		HtmlElement ue = (HtmlElement) page.getElementById("u");
		ue.setAttribute("value", uid);
		HtmlElement pe = (HtmlElement) page.getElementById("p");
		pe.setAttribute("value", password);
	}

	public static Page click(HtmlPage htmlPage, String xpath) {
		Page page = null;
		HtmlElement element = null;
		try {
			element = htmlPage.getFirstByXPath(xpath);
			if (element != null) {
				page = element.click();
			} else {
				logger.warn("find element xpath = " + xpath
						+ " is null click page xml " + htmlPage.asXml());
			}/*
			 * else { logger.error("click element is null xpath " + xpath);
			 * htmlPage.refresh(); page = htmlPage; }
			 */
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (element == null) {
			throw new RuntimeException("find element xpath = " + xpath
					+ " is null");
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return page;
	}

	public static boolean setAttribute(HtmlPage htmlPage, String xpath,
			String key, String value) {
		boolean flag = true;
		try {
			HtmlElement element = htmlPage.getFirstByXPath(xpath);
			if (element != null) {
				element.click();
				element.setAttribute(key, "");
				element.setAttribute(key, value);
			} else {
				logger.error("setAttribute element is null xpath " + xpath);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
		}
		return flag;
	}

	public static boolean login(WebClient client, String userName,
			String passWrod) throws Exception {
		boolean flag = false;
		try {
			String s = "https://passport.sina.cn/signin/signin?entry=wapsso&vt=4&r=http%3A%2F%2Fmy.sina.cn%2F%3Fpos%3D108%26vt%3D4%26m%3D78fc51068140045a973a3aeab4db2381&amp;revalid=1";
			HtmlPage page = client.getPage(s);
			// <input type="text" placeholder="微博帐号/手机号/邮箱" autocorrect="off"
			// autocapitalize="off" id="loginName">

			HtmlInput htmlInput = page
					.getFirstByXPath("//input[@id='loginName']");
			htmlInput.click();
			htmlInput.setAttribute("value", userName);

			// <input type="password" placeholder="密码" id="loginPassword">
			htmlInput = page.getFirstByXPath("//input[@id='loginPassword']");
			htmlInput.click();
			htmlInput.setAttribute("value", passWrod);

			// <a id="loginAction" class="btn_login" href="javascript:;">登录</a>

			HtmlElement a = page.getFirstByXPath("//a[@id='loginAction']");
			page = a.click();

			client.setJavaScriptTimeout(5000);
			String str = page.asXml();
			if (!str.contains("QQ帐号登录")) {
				flag = true;
				logger.error("登录成功");
			}
		} catch (Exception e) {
			logger.error(e);
			flag = false;
		}
		return flag;
	}

}
