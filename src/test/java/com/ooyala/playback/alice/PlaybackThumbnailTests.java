package com.ooyala.playback.alice;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.EventValidator;
import com.ooyala.playback.page.PauseValidator;
import com.ooyala.playback.page.PlayValidator;
import com.ooyala.playback.page.SeekValidator;
import com.ooyala.playback.page.ThumbnailValidator;
import com.ooyala.qe.common.exception.OoyalaException;

/**
 * Created by soundarya on 11/22/16.
 */
public class PlaybackThumbnailTests extends PlaybackWebTest {

	private EventValidator eventValidator;
	private PlayValidator play;
	private PauseValidator pause;
	private SeekValidator seek;
	private ThumbnailValidator thumbnailValidator;

	public PlaybackThumbnailTests() throws OoyalaException {
		super();
	}

	@Test(groups = "playback", dataProvider = "testUrls")
	public void testBasicPlaybackAlice(String testName, String url)
			throws OoyalaException {

		boolean result = true;

		try {
			driver.get(url);
			if (!getPlatform().equalsIgnoreCase("android")) {
				driver.manage().window().maximize();
			}

            result = result && play.waitForPage();

			Thread.sleep(10000);

			injectScript();

            result = result && play.validate("playing_1", 60);

			logger.info("Verifed that video is getting playing");

			Thread.sleep(5000);

            result = result && pause.validate("paused_1", 60);

            result = result && thumbnailValidator.validate("", 60);

			Thread.sleep(5000);

            result = result && play.validate("playing_2", 60);

            result = result && seek.validate("seeked_1", 60);

            result = result && eventValidator.validate("played_1", 60);

			logger.info("Verified that video is played");

		} catch (Exception e) {
			e.printStackTrace();
            result  = false;
		}
		Assert.assertTrue(result, "Thumbnail test failed");
	}
}
