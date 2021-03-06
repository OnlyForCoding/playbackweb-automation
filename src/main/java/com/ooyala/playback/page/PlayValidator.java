package com.ooyala.playback.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

public class PlayValidator extends PlayBackPage implements PlaybackValidator {

	private static Logger logger = Logger.getLogger(PlayValidator.class);

	public PlayValidator(WebDriver webDriver) {
		super(webDriver);
		PageFactory.initElements(webDriver, this);
		/**
		 * Here we will tell Facile to add the page elements of our Login Page
		 */
		addElementToPageElements("play");
	}
	
	private void errorDescription(){
		if(isElementPresent("ERROR_SCREEN")){
			String text = getWebElement("ERROR_DESCRIPTION").getText();
			extentTest.log(LogStatus.FAIL, text);
		}
		
		Assert.assertTrue(false,"Getting error like NETWORK ERROR or Play button is not present.");
	}

	@Override
	public boolean waitForPage() {
		boolean errorScreen = false;

		try {
			/*if(waitOnElement("PLAYER_SKIN", 90000)){ // to avoid waiting for a long time for the play button to appear in case of error scenarios.
				Thread.sleep(2000);
				if(isElementPresent("PLAY_BUTTON")){
					extentTest.log(LogStatus.PASS, "Play button found.");
					return true;
				}else{
					errorDescription();
					return false;
				}
			}
			return false;*/
			
			if(!loadingSpinner()){
				Assert.assertTrue(false,"In loading spinner for a very long time.");
				extentTest.log(LogStatus.FAIL, "In loading spinner for a very long time.");
				return false;
			}
			
			if(!waitOnElement("PLAY_BUTTON", 90000)){
				errorDescription();
				return false;
			}
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			driver.navigate().refresh();
			if (!waitOnElement("INNER_WRAPPER", 60000))
				return false;
			errorScreen = isElementPresent("ERROR_SCREEN");
			if (errorScreen)
				driver.navigate().refresh();
			if (!waitOnElement("PLAY_BUTTON", 60000)){
				errorDescription();
				return false;
			}
		}
		logger.info("Page is loaded completely");
		return true;
	}

	public boolean validate(String element, int timeout) throws Exception {

		// if(!PlayBackFactory.getInstance(driver).getPlayAction().startAction())
		// return false;

		if (!clickOnIndependentElement("PLAY_BUTTON")){
			extentTest.log(LogStatus.FAIL, "FAILED to click on PLAY_BUTTON.");
			return false;
		}

		if (!loadingSpinner()){
			extentTest.log(LogStatus.FAIL, "Loading spinner seems to be there for a really long time.");
			return false;
		}

		if (!waitOnElement("PLAYING_SCREEN", 60000))
			return false;

		if (!waitOnElement(By.id(element), timeout))
			return false;
		extentTest.log(LogStatus.PASS,
				"Video Playing and validation of element " + element
						+ " is successful");
		return true;
	}
}
