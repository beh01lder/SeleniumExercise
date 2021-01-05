package org.github.beh01lder.testcore;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumCommonActions {
	
	private WebDriver driver;
	final static Logger logger = Logger.getLogger(SeleniumCommonActions.class);
	protected static int waitForElement = 10;


	public void openBrowser() throws Exception {
    	System.setProperty("webdriver.chrome.driver", new File("./src/main/resources/drivers/chromedriver.exe").getCanonicalPath());

        //Create a new ChromeDriver
		setDriver(new ChromeDriver());
		// Puts an Implicit wait, Will wait for 10 seconds before throwing exception
		this.getDriver().manage().timeouts().implicitlyWait(waitForElement, TimeUnit.SECONDS);
		this.getDriver().manage().timeouts().pageLoadTimeout(waitForElement, TimeUnit.SECONDS);
	}
	
	public void closeBrowser() {
		try {
			this.getDriver().close();
			this.getDriver().quit();
		} catch (Exception ex) {
			logger.debug(ex);
		}
	}
	
	public void navigateto(String url) {
		try {
			maximizeWindow(this.getDriver());
		} catch (Exception e) {
			logger.info("Exception... " + e.getMessage());
			logger.debug("Exception... ", e);
		}
		logger.info("Going to... " + url);
		this.getDriver().navigate().to(url);
	}
	
	public void maximizeWindow(WebDriver driver) {
		Dimension originalSize = this.getDriver().manage().window().getSize();
		logger.info("Set size for browser window");

		Dimension targetSize = driver.manage().window().getSize();
		if (targetSize.getHeight() <= originalSize.getHeight() || targetSize.getWidth() <= originalSize.getWidth()) {
			logger.info("Current size: " + originalSize);
			logger.info("Set to 1360 x 768");
			this.getDriver().manage().window().setSize(new Dimension(1360, 768));
			this.getDriver().manage().window().setPosition(new Point(0, 0));
		}
		logger.info("Final size:" + driver.manage().window().getSize());
	}
	
	
	protected WebElement clickElement(WebElement webElement) {
		try {
			webElement.click();
		} catch (Exception ex) {
			logger.debug("", ex);
			// Retry
			webElement.click();
		}
		return webElement;
	}
	
	protected WebElement clickElementById(String locatorId) {
		WebElement element = null;
		int retries = 1;
		waitForElementVisibilityById(locatorId);
		while (element == null && retries < 4) {
			try {
				element = this.getDriver().findElement(By.id(locatorId));
				return clickElement(element);
			} catch (Exception ex) {
				logger.debug("Exception", ex);
			}
			retries++;
		}
		return element;
	}
	
	protected void waitForElementVisibilityById(String locatorId) {
		WebDriverWait wait = new WebDriverWait(this.getDriver(), waitForElement);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorId)));
	}
	
	public void highlightElement(WebElement webElement) {
		ChromeDriver webDriver = (ChromeDriver) this.getDriver();
		webDriver.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement,"color: red" + "; border: 2px solid yellow;");
	}
	
	public void moveToElement(WebElement webElement) throws Exception {
		Actions actions = new Actions(this.getDriver());
		actions.moveToElement(webElement);
		actions.perform();
		Thread.sleep(200);
	}
	
	public boolean findElementById(String locatorId) {
		logger.info("Find element with locatorId (" + locatorId + ")");
		boolean found = false;
		try {
			WebElement element = getDriver().findElement(By.xpath(locatorId));
			highlightElement(element);
			found = true;
		} catch(Exception ex) {
			logger.debug("Element not found:" + ex);
		}
		return found;
	}
	
	public void typeText(String locatorId, String text) {
		WebElement element = getDriver().findElement(By.xpath(locatorId));
		element.sendKeys(Keys.CONTROL, "a"); //select all text before typing
		element.sendKeys(text);
	}
	
	public void switchToLastTab() {
		String redirectUrl;
		driver = getDriver().switchTo().defaultContent();
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		logger.info("Found " + tabs.size() + " open tab(s)");
		driver.switchTo().window(tabs.get(tabs.size() - 1));
		redirectUrl = driver.getCurrentUrl();
		logger.info("Current tab url: " + redirectUrl);
	}
	

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}