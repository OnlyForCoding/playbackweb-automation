package com.ooyala.playback.alice;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.*;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.qe.common.exception.OoyalaException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * Created by soundarya on 11/17/16.
 */
public class PlaybackVerticalVideoTests extends PlaybackWebTest {

	private PlayValidator play;
	private PauseValidator pause;
	private SeekValidator seek;
	private PlayAction playAction;
	private EventValidator eventValidator;
	private AspectRatioValidator aspectRatioValidator;

	public PlaybackVerticalVideoTests() throws OoyalaException {
		super();
	}

	@Test(groups = "AspectRatio", dataProvider = "testUrls")
	public void testVerticalVideo(String testName, String url)
			throws OoyalaException {

		boolean result = true;
		try {
			driver.get(url);

            result = result && play.waitForPage();

			injectScript();

            result = result && play.validate("playing_1", 60000);

			sleep(2000);

            result = result && aspectRatioValidator.setVerticalVideo().validate("assetDimension_1", 60000);

			if(!(getBrowser().equalsIgnoreCase("safari"))){
				result = result && pause.validate("paused_1", 60000);
				result = result && playAction.startAction();
			}

            result = result && seek.validate("seeked_1", 60000);

			logger.info("video seeked");

            result = result && aspectRatioValidator.setVerticalVideo().validate("assetDimension_1", 60000);

			logger.info("validated vertical video dimention");

            result = result && eventValidator.validate("videoPlayed_1", 60000);

			logger.info("video played");

		} catch (Exception e) {
			e.printStackTrace();
            result = false;
		}
		Assert.assertTrue(result, "Vertical Video tests failed");
	}

}