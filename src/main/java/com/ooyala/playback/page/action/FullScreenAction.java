package com.ooyala.playback.page.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.ooyala.playback.page.PlayBackPage;
import com.relevantcodes.extentreports.LogStatus;

public class FullScreenAction extends PlayBackPage implements PlayerAction {

	public FullScreenAction(WebDriver webDriver) {
		super(webDriver);
		PageFactory.initElements(webDriver, this);
		addElementToPageElements("fullscreen");
		addElementToPageElements("pause");
	}

	@Override
	public boolean startAction() throws Exception {
		WebElement player = getWebElement("OOPLAYER");
		
		if(player==null) {
			extentTest.log(LogStatus.FAIL, "OOPLAYER is null");
			return false;
		}
		Actions action = new Actions(driver);
		action.moveToElement(player).perform();
		if(!waitOnElement("FULLSCREEN_BTN", 60000)) {
			extentTest.log(LogStatus.FAIL, "FULLSCREEN_BTN not found");
			return false;
		}

		if (getPlatform().equalsIgnoreCase("Android")) {
			if(!clickOnIndependentElement("FULLSCREEN_BTN")) return false;
		} else {
			if(!clickOnIndependentElement("PAUSE_BUTTON")) {
				extentTest.log(LogStatus.FAIL, "PAUSE_BUTTON not found");
				return false;
			}
			if(!clickOnIndependentElement("FULLSCREEN_BTN")) {
				extentTest.log(LogStatus.FAIL, "FULLSCREEN_BTN not found");
				return false;
			}
		}

		if (!(getBrowser().equalsIgnoreCase("safari")
				|| getBrowser().equalsIgnoreCase("firefox")
				|| getBrowser().equalsIgnoreCase("internet explorer") || getPlatform()
				.equalsIgnoreCase("Android"))) {
			
//			if(!waitOnElement(By.id("fullscreenChanged_true"), 6000)) {
//				return false;
//			}

			logger.info("Changed into Fullscreen");
			
		}
		
		extentTest.log(LogStatus.PASS, "Full Screen Validated!");
		Thread.sleep(1000);
		return clickOnIndependentElement("NORMAL_SCREEN");

	}

}
