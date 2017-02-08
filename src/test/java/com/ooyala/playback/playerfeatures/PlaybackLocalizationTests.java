package com.ooyala.playback.playerfeatures;

import com.ooyala.playback.page.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.action.PlayPauseAction;
import com.ooyala.qe.common.exception.OoyalaException;

/**
 * Created by soundarya on 11/16/16.
 */
public class PlaybackLocalizationTests extends PlaybackWebTest {

	private static Logger logger = Logger
			.getLogger(PlaybackLocalizationTests.class);

	private PlayValidator play;
	private PauseValidator pause;
	private SeekValidator seek;
	private PlayPauseAction playPauseAction;
	private EventValidator eventValidator;
	private ShareTabValidator shareTabValidator;
	private FullScreenValidator fullScreenValidator;

	public PlaybackLocalizationTests() throws OoyalaException {
		super();
	}

	@Test(groups = "playerFeatures", dataProvider = "testUrls")
	public void testPlaybackLocalization(String testName, String url)
			throws OoyalaException {

		boolean result = true;

		try {
			driver.get(url);
			if (!getPlatform().equalsIgnoreCase("android")) {
				driver.manage().window().maximize();
			}

			result = result && play.waitForPage();

			injectScript();

			result = result && play.validate("playing_1", 60000);

			result = result && pause.validate("paused_1", 60000);

			result = result && play.validate("playing_2", 60000);

			result = result && shareTabValidator.validate("", 60000);

			result = result && fullScreenValidator.getFullscreen();

			result = result && shareTabValidator.validate("", 60000);

			result = result && fullScreenValidator.getNormalscreen();

			result = result && playPauseAction.startAction();

			result = result && seek.validate("seeked_1", 60000);

			result = result && eventValidator.validate("played_1", 60000);

		} catch (Exception e) {
			logger.error(e);
			result = false;
		}
		Assert.assertTrue(result, "Playback Localization tests failed");
	}
}
