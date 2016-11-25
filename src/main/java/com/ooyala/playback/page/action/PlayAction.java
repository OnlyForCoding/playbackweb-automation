package com.ooyala.playback.page.action;

import static java.lang.Thread.sleep;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.ooyala.playback.factory.PlayBackFactory;
import com.ooyala.playback.page.PlayBackPage;

public class PlayAction extends PlayBackPage implements PlayerAction {

	public PlayAction(WebDriver webDriver) {
		super(webDriver);
		PageFactory.initElements(webDriver, this);
		/**
		 * Here we will tell Facile to add the page elements of our Login Page
		 */
		addElementToPageElements("play");
		addElementToPageElements("startscreen");
	}

	@Override
	public void startAction() {
		clickOnIndependentElement("PLAY_BUTTON");

	}
	
	public void startActionOnScreen() throws Exception{
		try {
			waitOnElement("STATE_SCREEN_SELECTABLE", 50);
			clickOnIndependentElement("STATE_SCREEN_SELECTABLE");
		} catch (Exception e) {
			Actions action = new Actions(driver);
			action.moveToElement(getWebElement("PLAY_BUTTON")).build().perform();
			Thread.sleep(5000);
			PlayBackFactory.getInstance(driver).getPlayAction().startAction();
		}
	}

}
