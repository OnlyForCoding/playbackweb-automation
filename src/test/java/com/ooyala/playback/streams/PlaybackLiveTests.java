package com.ooyala.playback.streams;

import com.ooyala.playback.page.action.PauseAction;
import com.ooyala.playback.page.action.PlayAction;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.ControlBarValidator;
import com.ooyala.playback.page.EventValidator;
import com.ooyala.playback.page.FullScreenValidator;
import com.ooyala.playback.page.PauseValidator;
import com.ooyala.playback.page.PlayValidator;
import com.ooyala.playback.page.action.LiveAction;
import com.ooyala.qe.common.exception.OoyalaException;

/**
 * Created by soundarya on 11/16/16.
 */
public class PlaybackLiveTests extends PlaybackWebTest {

	private static Logger logger = Logger.getLogger(PlaybackLiveTests.class);
	private PlayValidator play;
	private PauseValidator pause;
	private EventValidator eventValidator;
	private ControlBarValidator controlBarValidator;
	private FullScreenValidator fullScreenValidator;
	private LiveAction liveAction;
    private PauseAction pauseAction;
    private PlayAction playAction;

	public PlaybackLiveTests() throws OoyalaException {
		super();
	}

	@Test(groups = "streams", dataProvider = "testUrls")
	public void testLive(String testName, String url) throws OoyalaException {

		boolean result = true;

        if((testName.split(":")[1].toLowerCase()).contains("HLS".toLowerCase())&& !(getBrowser().equalsIgnoreCase("safari")) ){
            throw new SkipException("HLS tests run only on Safari browser - Test Skipped");
        }

			try {
				driver.get(url);

                result = result && play.waitForPage();

				injectScript();

                result = result && play.validate("playing_1", 60000);
                Thread.sleep(3000);

                result = result && pause.validate("paused_1", 60000);

                  result = result && controlBarValidator.validate("", 60000);
				// to-do add ooyala logo to the test page

                result = result && fullScreenValidator.validate("FULLSCREEN_BTN_1", 60000);

                result = result && pauseAction.startAction();

                result = result && liveAction.startAction();

                result = result && playAction.startAction();

			} catch (Exception e) {
				e.printStackTrace();
                result = false;
			}
			Assert.assertTrue(result, "Playback Live tests failed");

	}
}