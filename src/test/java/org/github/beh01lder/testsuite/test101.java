package org.github.beh01lder.testsuite;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import org.github.beh01lder.testcore.TestPageObject;
import org.github.beh01lder.testcore.TestSuperObject;
import org.github.beh01lder.testcore.VlcScreenRecorderHelper;
 
public class test101 extends TestSuperObject{
	TestPageObject testPageObject = new TestPageObject();
	
    //-----------------------------------Global Variables-----------------------
    //Declare a Webdriver variable

    public WebDriver driver;

    //Declare a test URL variable
    public String testURL = "https://www.car.gr/classifieds/boats/search/";

    
    VlcScreenRecorderHelper vlcScreenRecord = new VlcScreenRecorderHelper();

 
    //-----------------------------------Test Setup-----------------------------
    @BeforeClass
    public void setupTest (){
    	
    	vlcScreenRecord.startRecording("testrun");
    	
    	/* alternative screen recording lib */
    	//MmScreenRecorderHelper.startRecording("testrun");
    }
 
    //-----------------------------------Tests----------------------------------
	@Test(priority = 1, description = "Navigate to Url")
    public void navigateToUrl () throws Exception {
    	testPageObject.openBrowser();
    	testPageObject.navigateto(testURL);
    	Thread.sleep(2000);
    	
        //Get page title
        String title = testPageObject.getDriver().getTitle();
 
        //Print page's title
        System.out.println("Page Title: " + title);
 
        //Assertion
        Assert.assertEquals(title, "Αναζήτηση αγγελίες σκαφών - Car.gr", "Title assertion");
    }
    
	@Test(priority = 2, description = "Search")
	@Parameters({ "make"})
    public void searchItems (@Optional String make) throws Exception {
		if(make == null) {
			make = "Callegari";
		}
    	testPageObject.enterMake(make);
    	Thread.sleep(1000);
    	testPageObject.clickSearch();
        Thread.sleep(1000);

        //TODO screenshot
    }
 
    //-----------------------------------Test TearDown--------------------------
    @AfterClass
    public void teardownTest () {
        //Close browser and end the session
    	vlcScreenRecord.stopRecording();
    	vlcScreenRecord.releaseRecordingResources();
    	testPageObject.closeBrowser();
    	
    	/* alternative screen recording lib */
    	//MmScreenRecorderHelper.stopRecording();
    	
    }
}