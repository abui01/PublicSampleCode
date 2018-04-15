package com.learning.library;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;

public class ScreenshotRule extends TestWatcher {
	public WebDriver driver;
	public UtilityLibrary myLib;

	public ScreenshotRule(WebDriver _driver) {
		this.driver = _driver;
	}

	public void setDriver(WebDriver _driver) {
		this.driver = _driver;
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	private String getTestName(Description des) {
		String finalTestName = null;
		String tempTestName = des.getDisplayName();
		finalTestName = tempTestName.replace('(', '_').replace(')', '_').replace('.', '_');
		//System.out.println("temp: " + tempTestName);

		return finalTestName;
	}

	@Override
	public void failed(Throwable t, Description des) {
		try {
			myLib = new UtilityLibrary(driver);
			String testName = getTestName(des);
			myLib.captureScreenshot(testName, "");
			if (driver != null) // if browser is open
			{
				driver.close();
				driver.quit();
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
