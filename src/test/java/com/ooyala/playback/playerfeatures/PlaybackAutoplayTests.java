package com.ooyala.playback.playerfeatures;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.EventValidator;
import com.ooyala.playback.page.action.AutoplayAction;
import com.ooyala.playback.page.action.SeekAction;
import com.ooyala.playback.url.UrlObject;
import com.ooyala.qe.common.exception.OoyalaException;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Created by soundarya on 11/16/16.
 */
public class PlaybackAutoplayTests extends PlaybackWebTest {

	private static Logger logger = Logger.getLogger(PlaybackAutoplayTests.class);
	private EventValidator eventValidator;
	private AutoplayAction autoplayAction;
	private SeekAction seekAction;

	public PlaybackAutoplayTests() throws OoyalaException {
		super();
	}

	@Test(groups = "playerFeatures", dataProvider = "testUrls")
	public void testAutoPlay(String testName, UrlObject url) throws OoyalaException {
		boolean result = true;
		try {
			driver.get(url.getUrl());

			injectScript();

			result = result && eventValidator.loadingSpinner();

			result = result && autoplayAction.startAction();

			result = result && eventValidator.validate("singleAdPlayed_1", 5000);

			result = result && eventValidator.validate("playing_1", 60000);

			result = result && seekAction.seek(10, true);

			result = result && eventValidator.validate("seeked_1", 20000);

			result = result && eventValidator.validate("played_1", 60000);

		} catch (Exception e) {
			logger.error("Exception while checking autplay  " + e.getMessage());
			extentTest.log(LogStatus.FAIL, e.getMessage());
			result = false;
		}
		Assert.assertTrue(result, "Playback Autoplay tests failed");
	}
}
