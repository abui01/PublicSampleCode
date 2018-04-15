package com.learning.library;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

/***
 * This class provides common Selenium functionalities.
 * 
 * @author Andy Bui
 */
public class UtilityLibrary {

	final static Logger logger = Logger.getLogger(UtilityLibrary.class);
	private WebDriver driver;
	public boolean isDemoMode;
	private boolean isRemoteUpload;

	// Constructor
	public UtilityLibrary(WebDriver _driver) {
		driver = _driver;
	}

	/***
	 * This method starts Firefox browser
	 * 
	 * @return driver
	 */
	private WebDriver startFirefoxBrowser() {
		try {
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception ex) {
			logger.error("Oops, error with start firefox browser method!", ex);
		}
		return driver;
	}

	/***
	 * This method starts IE browser
	 * 
	 * @return driver
	 */
	private WebDriver startIEBrowser() {
		try {
			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName("internet explorer");// internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

			driver = new InternetExplorerDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return driver;
	}

	/***
	 * This method starts Chrome browser
	 * 
	 * @return driver
	 */
	private WebDriver startChromeBrowser() {
		try {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return driver;
	}

	/***
	 * This method starts user defined browser
	 * 
	 * @param browserName
	 * @return driver
	 */
	public WebDriver startLocalBrowser(String browserName) {
		try {
			if (browserName.contains("IE")) {
				driver = startIEBrowser();
			} else if (browserName.contains("chrome")) {
				driver = startChromeBrowser();
			} else if (browserName.contains("firefox")) {
				driver = startFirefoxBrowser();
			} else {
				throw new Exception("You choose '" + browserName + "', Currently "
						+ "supporting browsers are 'IE, Firefox & Chrome' !");
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return driver;
	}

	public WebDriver startRemoteBrowser(String hubUrl, String browserName) {
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			if (browserName.contains("IE")) {
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				browserName = "internet explorer";
			}
			capabilities.setBrowserName(browserName);
			driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
		} catch (Exception ex) {
			logger.error("Starting remote browser failed for '" + browserName + "'", ex);
		}
		isRemoteUpload = true;
		return driver;
	}

	/***
	 * This method handles browser check-box element
	 * 
	 * @param checkBoxLocator
	 * @param isCheck
	 */
	public void handleCheckBox(By checkBoxLocator, boolean isCheck) {
		try {
			WebElement CheckBoxElem = driver.findElement(checkBoxLocator);
			boolean userChecboxState = CheckBoxElem.isSelected();
			// System.out.println("Check box default state is: " +
			// userChecboxState);
			if (isCheck == true)// user wanted to select
			{
				if (userChecboxState == true)// default selected
				{
					// do nothing
				} else // default Not selected
				{
					CheckBoxElem.click();
				}
			} else // user do Not want to select
			{
				if (userChecboxState == true) // default selected
				{
					CheckBoxElem.click();
				} else {
					// do nothing
				}
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method waits for user given seconds
	 * 
	 * @param inSeconds
	 */
	public void customWait(int inSeconds) {
		try {
			Thread.sleep(inSeconds * 1000);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method enters user defined text to text field web-element
	 * 
	 * @param by
	 * @param textValue
	 */
	public void enterTextField(By by, String textValue) {
		try {
			WebElement textFieldElem = driver.findElement(by);
			textFieldElem.clear();
			textFieldElem.sendKeys(textValue);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method clicks any user given element
	 * 
	 * @param by
	 */
	public void clickElement(By by) {
		try {
			WebElement element = driver.findElement(by);
			element.click();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public String captureScreenshot(String screenshotFileName, String filePathToSave) {
		String finalImage = null;
		try {
			String relativeFilePath = "target/error-screenshots/";
			String screenshotFileNamePath = relativeFilePath + filePathToSave + screenshotFileName + getCurrentTime()
					+ ".png";
			if(isRemoteUpload)
			{
				driver = new Augmenter().augment(driver);
			}
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenshotFileNamePath));
			finalImage = screenshotFileNamePath;
			logger.debug("Screenshot file location: '" + finalImage + "'");
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		System.out.println("Screenshot captured: '" + finalImage + "'");
		return finalImage;
	}

	/***
	 * This method calculates current time and returns the value
	 * 
	 * @return String current time-stamp
	 */
	public String getCurrentTime() {
		String finalTimeStamp = null;
		try {
			Date date = new Date();
			String tempTime = (new Timestamp(date.getTime())).toString();
			finalTimeStamp = tempTime.replace(":", "").replace(".", "").replace(" ", "");
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return finalTimeStamp;
	}

	/***
	 * This method moves the mouse pointer to the given WebElement
	 * 
	 * @param toElement
	 */
	public void moveMouseToElement(WebElement toElement) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(toElement).build().perform();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method move mouse to first given WebElement then to second given
	 * WebElement.
	 * 
	 * @param firstElem
	 * @param secondElem
	 */
	public void moveMouseToElement(WebElement firstElem, WebElement secondElem) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(firstElem).build().perform();
			logger.debug("Waitting for 1 second...");
			customWait(1);
			action.moveToElement(secondElem).build().perform();
			logger.debug("Waitting for 1 second...");
			customWait(1);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method clicks on hidden WebElement using JavascriptExecutor
	 * interface from Selenium
	 * 
	 * @param by
	 */
	public void clickOnHiddenElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", driver.findElement(by));
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method clicks on hidden WebElement using JavascriptExecutor
	 * interface from Selenium
	 * 
	 * @param element
	 */
	public void clickOnHiddenElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method selects drop-down value (Used only for Select and option Html
	 * tags)
	 * 
	 * @param by
	 * @param optionValue
	 */
	public void selectDropDown(By by, String optionValue) {
		try {
			WebElement dropdownElem = driver.findElement(by);
			Select selectList = new Select(dropdownElem);
			selectList.selectByVisibleText(optionValue);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * This method used for dynamic element search
	 * 
	 * @param by
	 * @return
	 */
	public WebElement fluentWait(final By by) {
		WebElement targetElem = null;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

			targetElem = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(by);
				}
			});
		} catch (Exception ex) {
			logger.error("Oops, FluentWait is erroring.", ex);
			assertTrue(false);
		}
		return targetElem;
	}

	public void highlightElement(WebElement element) {
		try {
			if (isDemoMode == true) {
				for (int i = 0; i < 3; i++) {
					WrapsDriver wrappedElement = (WrapsDriver) element;
					JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
					customWait(1);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow;");
					customWait(1);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
				}
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method switch the driver to iFrame section of the html source
	 * 
	 * @param by
	 */
	public void switchToIframe(By by) {
		try {
			WebElement iframeElement = driver.findElement(by);
			driver.switchTo().frame(iframeElement);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method switch back to original html content from iFrame section
	 */
	public void switchToDefault() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public List<String> automaticallyAttachErrorScreenShotsToEmail() {

		List<String> fileNames = new ArrayList<String>();
		try {
			JavaPropertiesManager property = new JavaPropertiesManager("src/test/resources/config.properties");

			String timeFlag = property.readProperty("SessionTimeForScreenshot");
			String numTime = timeFlag.replaceAll("-", "").trim();
			long testStartTime = Long.parseLong(numTime);

			// fist check if error-screenshot folder has file
			File file = new File("target/error-screenshots");
			if (file.isDirectory()) {
				if (file.list().length > 0) {
					File[] screenShotFiles = file.listFiles();
					for (int i = 0; i < screenShotFiles.length; i++) {
						String eachFileName = screenShotFiles[i].getName();
						int indexOf20 = searchStringInString("20", eachFileName);

						String timeStampFromScreenshotFile = eachFileName.substring(indexOf20,
								eachFileName.length() - 4);
						String numberStamp = timeStampFromScreenshotFile.replaceAll("-", "");
						long number = Long.parseLong(numberStamp);
						if (numberStamp.length() != numTime.length()) {
							String fixed = numberStamp.substring(0, numberStamp.length() - 1);
							number = Long.parseLong(fixed);
						}
						// Only add the screenshots taken for the last running
						// tests
						if (number > testStartTime) {
							fileNames.add("target/error-screenshots/" + eachFileName);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("error...", ex);
		}
		return fileNames;
	}

	/***
	 * This method searches the target sub-string in given string message
	 * 
	 * @param target
	 * @param msg
	 * @return index of first target sub-string match
	 */
	public int searchStringInString(String target, String msg) {
		int targetIndex = 0;
		for (int i = -1; (i = msg.indexOf(target, i + 1)) != -1;) {
			targetIndex = i;
			break;
		}
		return targetIndex;
	}

	/***
	 * This method uploads given file & automatically supports remote file
	 * upload
	 * 
	 * @param by
	 * @param filePath
	 */
	public void fileUpload(By by, String filePath) {
		try {
			if (isRemoteUpload) {
				((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			}
			WebElement fileUploadElem = driver.findElement(by);
			fileUploadElem.sendKeys(filePath);
		} catch (Exception ex) {
			logger.error("file upload method failed.", ex);
		}
	}

}
