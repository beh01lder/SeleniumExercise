package org.github.beh01lder.testcore;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestPageObject extends SeleniumCommonActions{
	
	private WebDriver driver;
	final static Logger logger = Logger.getLogger(TestPageObject.class);
	int delay = 200;
	String buttonSearch = "(//button[contains(text(),'Δείξε ')])[1]";
	String errorMessageElements = "//div[@class='field-error-msg']";
	

	public TestPageObject goToUrl(String url) {
		try {
			maximizeWindow(this.getDriver());
			logger.info("Going to... " + url);
			this.driver.get(url);
		} catch (Exception e) {
			logger.info("Exception... " + e.getMessage());
			logger.debug("Exception... ", e);
		}
		return this;
	}
	
	public void enterMake(String make) throws Exception {
		String searchElementMake = "//label/span[contains(text(),'Μάρκα')]";
		WebElement element = getDriver().findElement(By.xpath(searchElementMake));
		highlightElement(element);
		moveToElement(element);
		Thread.sleep(delay);
		element = getDriver().findElement(By.xpath(searchElementMake + "//..//../div/div[1]"));
		clickElement(element);

		logger.info("Enter make: " + make);
		typeText(searchElementMake + "//..//../div/div[2]/div/input", make);

		// Select Checkbox
		logger.info("Select Checkbox");
		element = getDriver().findElement(By.xpath("//span[contains(text(),'" + make + "')]"));
		clickElement(element);

		// Close searchbox
		logger.info("Close search box");
		element = getDriver().findElement(By.xpath("(//button[contains(text(),'Κλείσιμο')])[2]"));
		clickElement(element);
	}
	
	public void clickSearch() throws Exception {
		logger.info("Click Search button");
		WebElement element = getDriver().findElement(By.xpath(buttonSearch));
		moveToElement(element);
		highlightElement(element);
		Thread.sleep(delay);
		clickElement(element);
	}
	
	public int getErrorMessages() throws Exception {
		int errorsFound = 0;
		logger.info("Validate error messages");
		List<WebElement> elementsList = getDriver().findElements(By.xpath(errorMessageElements));
		if (elementsList!=null && !elementsList.isEmpty()) {
			errorsFound = elementsList.size();
			logger.info(elementsList.size() + " error message(s) found");
			for (WebElement element : getDriver().findElements(By.xpath(errorMessageElements))) {
				moveToElement(element);
				highlightElement(element);
				Thread.sleep(delay);
				logger.info(element.getText());
			}
		}
		return errorsFound;
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
