package com.ooyala.playback.alice;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.*;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.qe.common.exception.OoyalaException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by soundarya on 11/16/16.
 */
public class PlayerMetadataStatesTests extends PlaybackWebTest {

    private PlayValidator play;
    private SeekValidator seek;
    private PlayAction playAction;
    private EventValidator eventValidator ;
    private PauseValidator pause;
    private EndScreenValidator endScreenValidator ;
    private StartScreenValidator startScreenValidator ;


	public PlayerMetadataStatesTests() throws OoyalaException {
        super();
    }
        @Test(groups = "Player", dataProvider = "testUrls")
        public void testPlayerMetadataStates(String testName, String url) throws OoyalaException {

        boolean result = false;

            try {
                driver.get(url);
                if (!getPlatform().equalsIgnoreCase("android")) {
                    driver.manage().window().maximize();
                }

                play.waitForPage();

                startScreenValidator.validate("",60);

                injectScript(jsURL());

                // playAction.startAction();

                play.validate("playing_1", 60);
                logger.info("video is playing");
                Thread.sleep(2000);

                pause.validate("paused_1",60);
                logger.info("video is paused");

                play.validate("playing_2", 60);
                logger.info("video is playing again");

                seek.validate("seeked_1", 60);
                logger.info("video seeked");

                eventValidator.validate("played_1",60);
                logger.info("video played");

                endScreenValidator.validate("",60);

                eventValidator.eventAction("FULLSCREEN_BTN_1");

                endScreenValidator.validate("fullscreenChangedtrue",50);
                logger.info("checked fullscreen");

                endScreenValidator.validate("", 60);
                eventValidator.eventAction("FULLSCREEN_BTN_1");

                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertTrue(result, "Playback MetadataStates tests failed");
        }
}

