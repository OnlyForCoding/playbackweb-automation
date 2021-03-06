package com.ooyala.playback.playerfeatures;


import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.*;
import com.ooyala.playback.url.UrlObject;
import com.ooyala.qe.common.exception.OoyalaException;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by soundarya on 11/16/16.
 */
public class PlaybackClosedCaptionTests extends PlaybackWebTest {
	private static Logger logger = Logger
			.getLogger(PlaybackClosedCaptionTests.class);

	private PlayValidator play;
	private PauseValidator pause;
	private SeekValidator seek;
	private EventValidator eventValidator;
	private CCValidator ccValidator;

	public PlaybackClosedCaptionTests() throws OoyalaException {
		super();
	}

	@Test(groups = "playerFeatures", dataProvider = "testUrls")
	public void testClosedCaption(String testName, UrlObject url)
			throws OoyalaException {

		boolean result = true;

		try {
			driver.get(url.getUrl());

			result = result && play.waitForPage();

			injectScript();

			result = result && play.validate("playing_1", 60000);

			result = result && pause.validate("paused_1", 60000);

			result = result && play.validate("playing_2", 60000);

			result = result && ccValidator.validate("cclanguage", 60000);

			result = result && seek.validate("seeked_1", 60000);

			result = result && eventValidator.validate("played_1", 60000);

		} catch (Exception e) {
			logger.error(e);
			result = false;
			extentTest.log(LogStatus.FAIL, "Playback Closed Caption tests failed", e);

		}
		Assert.assertTrue(result, "Closed Caption tests failed");
	}
}
