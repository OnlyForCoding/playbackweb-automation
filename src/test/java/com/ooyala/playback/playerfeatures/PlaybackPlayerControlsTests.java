package com.ooyala.playback.playerfeatures;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.*;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.playback.url.UrlObject;
import com.ooyala.qe.common.exception.OoyalaException;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaybackPlayerControlsTests extends PlaybackWebTest {

    private static Logger logger = Logger
            .getLogger(PlaybackPlayerControlsTests.class);
    private EventValidator eventValidator;
    private PlayValidator play;
    private PauseValidator pause;
    private SeekValidator seek;
    private FullScreenValidator fullScreenValidator;
    private ControlBarValidator controlBarValidator;
    private PlayAction playAction;

    public PlaybackPlayerControlsTests() throws OoyalaException {
        super();
    }

    @Test(groups = "playerFeatures", dataProvider = "testUrls")
    public void testBasicPlaybackPlayerControl(String testName, UrlObject url)
            throws OoyalaException {

        boolean result = true;

        try{
            driver.get(url.getUrl());

            result = result && play.waitForPage();

            injectScript();

            result = result && playAction.startAction();

            result = result && eventValidator.validate("playing_1",20000);

            result = result && pause.validate("paused_1", 60000);

            if (!(getBrowser().equalsIgnoreCase("safari") || getPlatform().equalsIgnoreCase("Android"))) {
                result = result && fullScreenValidator.getFullScreen();
                Thread.sleep(1000);
                result = result && fullScreenValidator.getNormalScreen();
            }

            result = result && controlBarValidator.validate("", 60000);

            result = result && playAction.startAction();

            result = result && eventValidator.validate("playing_2", 60000);

            result = result && seek.validate("seeked_1", 60000);

            result = result && eventValidator.validate("played_1", 60000);

        } catch (Exception e) {
            logger.error("Alice basic playback tests failed" + e.getMessage());
            extentTest.log(LogStatus.FAIL, e.getMessage());
            result = false;
        }
        Assert.assertTrue(result, "Alice basic playback tests failed"+testName);
    }
}
