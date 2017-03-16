package com.ooyala.playback.amf.preroll;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.*;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.playback.page.action.SeekAction;
import com.ooyala.qe.common.exception.OoyalaException;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Integer.parseInt;

/**
 * Created by jitendra on 2/3/17.
 */
public class PlaybackVerifyVideoElementCreatedAllPrerollAdsTests extends PlaybackWebTest {

    private static Logger logger = Logger.getLogger(PlaybackVerifyVideoElementCreatedAllPrerollAdsTests.class);
    private EventValidator eventValidator;
    private PlayValidator play;
    private PlayAction playAction;
    private IsAdPlayingValidator isAdPlayingValidator;
    private DifferentElementValidator differentElementValidator;
    private SeekAction seekAction;
    private SeekValidator seekValidator;
    private PoddedAdValidator poddedAdValidator;

    PlaybackVerifyVideoElementCreatedAllPrerollAdsTests() throws OoyalaException {
        super();
    }

    @Test(groups = "VideoCreated", dataProvider = "testUrls")
    public void testVideoElementCreated(String testName, String url)
            throws OoyalaException {

        boolean result = true;
        int counter = 0 ;
        try {
            driver.get(url);

            result=result && play.waitForPage();

            injectScript();

            result=result && playAction.startAction();
            result = result && eventValidator.validate("videoPlaying_1",50000);

            result = result && poddedAdValidator.setPosition("PreRoll").validate("countPoddedAds_1", 120000);

            // for IMA ad videoControllerVideoElementCreated event is not triggering
            if (!eventValidator.isAdPluginPresent("ima")) {
                int noOfPoddedAds = parseInt(
                        (((JavascriptExecutor) driver).executeScript("return adPodStarted_1.textContent")).toString());

                logger.info("Number of ads present :"+noOfPoddedAds);

                for (int i = 1 + counter; i <= noOfPoddedAds; i++) {
                    result = result && eventValidator.validate("videoCreatedForAds_" + i + "", 20000);
                }
            }

            result = result && differentElementValidator.validateMainVideoElementId("VIDEO_ELEMENT",20000);

            if (!eventValidator.isAdPluginPresent("ima") ){
                result = result && differentElementValidator.validateAdElementId("AD_ELEMENT",5000);
            }

        } catch (Exception e) {
            logger.error("Exception while checking Video Element tests  "+e.getMessage());
            extentTest.log(LogStatus.FAIL, e.getMessage());
            result = false;
        }
        Assert.assertTrue(result, "Playback Video Element test failed");

    }
}