package com.learning.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;

public class BasePage {

	final static Logger logger = Logger.getLogger(BasePage.class);
	public static WebDriver driver;
	public static UtilityLibrary myLib;
	private static JavaPropertiesManager property;
	private static String browser;
	private static String SessionFlag = "SessionTimeForScreenshot";
	private static String isRemoteRun;
	private static String hubUrlValue;

	@Rule
	public ScreenshotRule screenCaptureRule = new ScreenshotRule(driver);

	@Before
	public void beforeEachTest() throws Exception {
		if(isRemoteRun.contains("true"))
		{
			logger.info("testing: " + hubUrlValue);
			driver = myLib.startRemoteBrowser(hubUrlValue, browser);
		}else{
		driver = myLib.startLocalBrowser(browser);
		screenCaptureRule.setDriver(driver);
		logger.info("*** Test Started ***");
		}
	}

	@After
	public void afterEachTest() throws Exception {
		myLib.customWait(5);
		logger.info("*** Test Ended ***");
	}

	@AfterClass
	public static void afterAllTest() {
		// if (driver != null) {
		// driver.close();
		// driver.quit();
		// }

		List<String> screenshots = new ArrayList<>();
		EmailManager emailSender = new EmailManager();
		emailSender.attachmentFiles.add("target/logs/Selenium-Report.html");
		emailSender.attachmentFiles.add("target/logs/log4j-selenium.log");

		screenshots = myLib.automaticallyAttachErrorScreenShotsToEmail();
		if (screenshots.size() != 0) {
			for (String temp : screenshots) {
				emailSender.attachmentFiles.add(temp);
			}
		}
		emailSender.sendEmail(emailSender.attachmentFiles);
	}

	@BeforeClass
	public static void beforeAllTestStart() throws Exception {
		property = new JavaPropertiesManager("src/test/resources/config.properties");
		browser = property.readProperty("browserType");
		isRemoteRun = property.readProperty("isRemote");
		hubUrlValue = property.readProperty("hubUrl");
		
		myLib = new UtilityLibrary(driver);
		if (property.readProperty("isDemoMode").contains("true")) {
			myLib.isDemoMode = true;
			logger.info("Test is running Demo mode ON");
		} else {
			myLib.isDemoMode = false;
			logger.info("Test is running Demo mode OFF");
		}
		
		property.setProperty(SessionFlag, myLib.getCurrentTime());
	}

}
