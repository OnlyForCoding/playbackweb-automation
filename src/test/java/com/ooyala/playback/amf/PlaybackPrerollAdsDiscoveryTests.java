package com.ooyala.playback.amf;

import static com.relevantcodes.extentreports.LogStatus.PASS;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.DiscoveryValidator;
import com.ooyala.playback.page.EventValidator;
import com.ooyala.playback.page.PlayValidator;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.qe.common.exception.OoyalaException;
import com.relevantcodes.extentreports.LogStatus;

public class PlaybackPrerollAdsDiscoveryTests extends PlaybackWebTest{

	public PlaybackPrerollAdsDiscoveryTests() throws OoyalaException {
		super();
	}
	
	private EventValidator event;
	private PlayAction playAction;
	private PlayValidator playValidator;
	private DiscoveryValidator discoveryValidator;

	@Test(groups = "amf", dataProvider = "testUrls")
	public void verifyPrerollDiscovery(String testName, String url)
			throws OoyalaException {

		boolean result = false;

		try {

			driver.get(url);
			if (!getPlatform().equalsIgnoreCase("android")) {
				driver.manage().window().maximize();
			}

			playValidator.waitForPage();
			Thread.sleep(2000);

			injectScript();

			playAction.startAction();
	        loadingSpinner();
	        event.validate("singleAdPlayed_1",150);
	        extentTest.log(LogStatus.PASS, "Played Preroll ads");

	        loadingSpinner();
	        event.validate("playing_1",150);
	        discoveryValidator.validate("reportDiscoveryClick_1", 60);
	            
	        extentTest.log(PASS, "Verified Preroll Ads with Discovery");


			result = true;

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		Assert.assertTrue(result, "Verified PreRoll Ads test");

	}

}
