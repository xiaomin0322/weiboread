package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import weiboread.PropertiesUtil;

/**
 * 娴忚鍣╱til
 */

public class SeleniumUtil {

	private static final Logger logger = Logger.getLogger(SeleniumUtil.class);

	private static FirefoxProfile profile = null;
	private static DesiredCapabilities capability = null;

	public static void setDesiredCapabilities() {
		logger.info("start init Firefox profile!");
		String plugin = SeleniumUtil.class.getResource("/plugin/killspinners-1.2.1-fx.xpi").getPath();
		try {
			profile = new FirefoxProfile();
			// profile = new ProfilesIni().getProfile("default");
			profile.addExtension(new File(plugin));
			// 鍘绘帀css
			// profile.setPreference("permissions.default.stylesheet", 2);
			// 鍘绘帀鍥剧墖
			// profile.setPreference("permissions.default.image", 2);
			// 鍘绘帀flash
			profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
			capability = DesiredCapabilities.firefox();
			capability.setCapability("firefox_profile", profile);

		} catch (Exception e) {
			logger.error("init firefox plugin(killspinnners) is error! ", e);
		}
		logger.info("init Firefox profile is success!");
	}

	/**
	 * 鍒濆鍖栨祻瑙堝櫒鐨刾rofile鏂囦欢
	 */
	static {
		setDesiredCapabilities();
	}

	/**
	 * 如果你没找到 NPAPI 项，试试输入：chrome://flags/#enable-npapi 如果仍然没找到，那么，你需要升级你的
	 * Chrome 浏览器到最新版，我的原来是 40.0版本，升级到42.0版本，结果就出现了。 按“启用”后，关闭 Chrome
	 * 程序，然后再重新打开，就可以正常呼出阿里旺旺了。
	 * 
	 * @return
	 */
	public static WebDriver initChromeDriver() {
		logger.info("start init WebDriver!");
		WebDriver driver = null;
		try {
			/*ChromeDriverService service = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File("e:\\app\\chromedriver\\chromedriver.exe")).usingAnyFreePort()
					.build();
			service.start();*/
			ChromeOptions options = new ChromeOptions();
			// options.addArguments(“–user-data-dir=C:/Users/xxx/AppData/Local/Google/Chrome/User
			// Data/Default”);
			/*String userDateDir = "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User Data";
			if(StringUtils.isNotBlank(PropertiesUtil.getPropertiesMap("alimama.userDateDir"))){
				userDateDir = PropertiesUtil.getPropertiesMap("alimama.userDateDir");
			}
			options.addArguments("--user-data-dir=" + userDateDir,"--allow-outdated-plugins");*/
			// 璁剧疆璁块棶ChromeDriver鐨勮矾寰�
			System.setProperty("webdriver.chrome.driver", "d:\\app\\chromedriver\\chromedriver.exe");
			driver = new ChromeDriver(options);
		} catch (Exception e) {
			logger.error("Init WebDriver is error!", e);
			throw new RuntimeException(e);
		}
		logger.info("init WebDriver is success!");
		return driver;
	}

	/**
	 * 鍒濆鍖栨祻瑙堝櫒
	 * 
	 * @param server
	 * @return
	 */
	public static WebDriver initWebDriver() {
		logger.info("start init WebDriver!");
		WebDriver driver = null;
		try {
			// driver = new RemoteWebDriver(new
			// URL(SystemConfigProper.SELENIUM_SERVER_HTTP+"/wd/hub"),
			// capability);
			driver = new FirefoxDriver(capability);
		} catch (Exception e) {
			logger.error("Init WebDriver is error!", e);
			throw new RuntimeException(e);
		}
		logger.info("init WebDriver is success!");
		return driver;
	}

	/**
	 * 鍒濆鍖栨祻瑙堝櫒
	 * 
	 * @param server
	 * @return
	 */
	public static WebDriver initWebDriver(String url) {
		logger.info("start init WebDriver!");
		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(new URL(url), capability);
		} catch (Exception e) {
			logger.error("Init WebDriver is error!", e);
			throw new RuntimeException(e);
		}
		logger.info("init WebDriver is success!");
		return driver;
	}

	public static WebDriver createWebDriver() throws Exception {
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setJavascriptEnabled(true);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		// 鍘绘帀css
		// firefoxProfile.setPreference("permissions.default.stylesheet", 2);
		// 鍘绘帀鍥剧墖
		// firefoxProfile.setPreference("permissions.default.image", 2);
		// 鍘绘帀flash
		firefoxProfile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);

		capability.setCapability("firefox_profile", firefoxProfile);
		WebDriver driver = new FirefoxDriver(capability);

		// WebDriver driver = new HtmlUnitDriver();
		// WebDriver driver = new HtmlUnitDriver();
		// 濡傛灉3s鍐呰繕瀹氫綅涓嶅埌鍒欐姏鍑哄紓甯?
		// driver.manage().timeouts().implicitlyWait(IMPLICITLYWAIT,
		// TimeUnit.SECONDS);
		// 椤甸潰鍔犺浇瓒呮椂鏃堕棿璁剧疆涓?5s
		// driver.manage().timeouts().pageLoadTimeout(PAGELOADTIMEOUT,
		// TimeUnit.SECONDS);
		// driver.manage().timeouts().setScriptTimeout(60,TimeUnit.SECONDS);
		return driver;

	}

	/**
	 * 鎴浘
	 * 
	 * @param driver
	 * @param url
	 * @param filePath
	 * @return
	 */
	public static String capture(WebDriver driver, String url, String filePath) {
		if (driver == null) {
			return null;
		}
		if (!url.startsWith("http:") && !url.startsWith("https:")) {
			return null;
		}
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		try {
			driver.get(url);
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			File file = new File(filePath);
			FileUtils.copyFile(screenshot, file);
			logger.info("capture success!");
		} catch (IOException e) {
			logger.error("browser capture is error!", e);
			filePath = null;
			throw new RuntimeException(e);
		}
		return filePath;
	}

	/**
	 * 鎴浘
	 * 
	 * @param driver
	 * @param url
	 * @param filePath
	 * @return
	 */
	public static String capture(WebDriver driver, String url) {
		if (driver == null) {
			return null;
		}
		if (!url.startsWith("http:") && !url.startsWith("https:")) {
			return null;
		}
		String response;
		try {
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driver.get(url);
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			response = augmentedDriver.getPageSource();
			logger.info("content success!");
		} catch (Exception e) {
			logger.error("browser content is error!", e);
			throw new RuntimeException(e);
		}
		return response;
	}

	public static boolean contentIsIn(WebDriver driver, String url, String text) {
		if (driver == null) {
			return false;
		}
		if (!url.startsWith("http:") && !url.startsWith("https:")) {
			return false;
		}
		text = text.trim();
		if (StringUtils.isBlank(text)) {
			return false;
		}
		try {
			driver.get(url);
			String test = driver.getPageSource();
			String[] txts = text.split(" ++");
			for (String txt : txts) {
				if (test.indexOf(txt) < 0) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("browser content is error!", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 鍏抽棴娴忚鍣?
	 * 
	 * @param driver
	 */
	public static void CloseWebDriver(WebDriver driver) {
		logger.info("start Destory WebDriver!");
		driver.quit();
		logger.info("Destory WebDriver is success!");
	}
}
