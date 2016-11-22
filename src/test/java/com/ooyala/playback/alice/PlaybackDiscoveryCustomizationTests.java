package com.ooyala.playback.alice;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.*;
import com.ooyala.playback.page.action.ClickDiscoveryButtonAction;
import com.ooyala.playback.page.action.PauseAction;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.playback.page.action.PlayPauseAction;
import com.ooyala.qe.common.exception.OoyalaException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * Created by soundarya on 11/17/16.
 */
public class PlaybackDiscoveryCustomizationTests extends PlaybackWebTest {

    private PlayValidator play;
    private UpNextValidator discoveryUpNext;
    private EventValidator eventValidator;
    private DiscoveryValidator discoveryValidator;
    private ClickDiscoveryButtonAction clickDiscoveryButtonAction;
    private PauseAction pauseAction;
    private PlayAction playAction;
    private PlayPauseAction playPauseAction;
    private SeekValidator seekValidator;

    public PlaybackDiscoveryCustomizationTests() throws OoyalaException {
        super();
    }

    @Test(groups = "Discovery", dataProvider = "testUrls")
    public void testDiscoveryUpNext(String testName, String url) throws OoyalaException
    {
        boolean result = false;

        try {
            driver.get(url);
            if (! driver.getCapabilities().getPlatform().toString().equalsIgnoreCase("android")) {
                driver.manage().window().maximize();
            }

            play.waitForPage();

            injectScript(jsURL());

            play.validate("playing_1", 60);

            logger.info("verified video playing");

            Thread.sleep(3000);

            result = true;
            try {
                clickDiscoveryButtonAction.startAction();

                discoveryValidator.verifyDiscoveryEnabled("On_discovery_click", true);    //verify discovery is disabled on discovery click
                eventValidator.eventAction("DISCOVERY_CLOSE_BTN");
                logger.info("verified discovery close button is present or not");
                sleep(2000);
                play.validate("playing_2", 60);
                logger.info("verified video playing again after discvery check");

                pauseAction.startAction();
                discoveryValidator.verifyDiscoveryEnabled("On_pauseScreen", false);   //verify discovery is disabled on pause screen

                Thread.sleep(10000);

                playAction.startAction();

                eventValidator.eventAction("FULLSCREEN_BTN");
                logger.info("verified fullscreen");
                try {
                    eventValidator.eventAction("PLAYING_SCREEN");
                } catch (Exception e) {
                    eventValidator.eventAction("VIDEO");
                }
                discoveryValidator.verifyDiscoveryEnabled("On_pause_FullScreen", false);
                sleep(1000);
                playPauseAction.startAction();

                clickDiscoveryButtonAction.startAction();

                sleep(2000);
                discoveryValidator.verifyDiscoveryEnabled("On_discoveryclick_fullScreen", true);
                eventValidator.eventAction("DISCOVERY_CLOSE_BTN");
                logger.info("verified discovery in full screen");
                eventValidator.eventAction("NORMAL_SCREEN");

                sleep(2000);

                playAction.startAction();

                seekValidator.seek(20,true);

                loadingSpinner();
                try {

                    discoveryUpNext.validate("UPNEXT_CONTENT", 60);
                    logger.info("Upnext is present");

                } catch (Exception e) {
                    logger.info("No Upnext panel");
                }
                try {

                eventValidator.validateElement("END_SCREEN", 60);
                } catch (Exception e) {
                    playAction.startAction();
                    seekValidator.seek(20,true);
                    eventValidator.validateElement("END_SCREEN", 60);
                }
                discoveryValidator.verifyDiscoveryEnabled("On_endScreen", false);   //verify discovery is disabled on end screen
            }catch (Exception e){
                logger.info("Exception "+ e);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(result, "Discovery customization tests failed");
    }

}
