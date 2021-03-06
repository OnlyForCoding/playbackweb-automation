package com.ooyala.playback.playerfeatures;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.EventValidator;
import com.ooyala.playback.page.PauseValidator;
import com.ooyala.playback.page.PlayValidator;
import com.ooyala.playback.page.SeekValidator;
import com.ooyala.playback.page.ThumbnailValidator;
import com.ooyala.playback.url.UrlObject;
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

	@Test(groups = "playerFeatures", dataProvider = "testUrls")
	public void testThumbnail(String testName, UrlObject url)
			throws OoyalaException {

		boolean result = true;

			try {

				driver.get(url.getUrl());

				result = result && play.waitForPage();

				injectScript();

				result = result && play.validate("playing_1", 60000);

				result = result && pause.validate("paused_1", 60000);

				result = result && thumbnailValidator.validate("", 60000);

				Thread.sleep(5000);

				result = result && play.validate("playing_2", 60000);

				result = result && seek.validate("seeked_1", 60000);

				result = result && eventValidator.validate("played_1", 60000);

			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}

			Assert.assertTrue(result, "Thumbnail test failed");
	   }
}
