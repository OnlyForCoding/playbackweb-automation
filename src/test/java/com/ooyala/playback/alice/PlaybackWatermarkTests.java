package com.ooyala.playback.alice;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.*;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.playback.url.UrlGenerator;
import com.ooyala.qe.common.exception.OoyalaException;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * Created by soundarya on 11/16/16.
 */
public class PlaybackWatermarkTests extends PlaybackWebTest {

    @DataProvider(name = "testUrls")
    public Object[][] getTestData() {

        return UrlGenerator.parseXmlDataProvider(getClass().getSimpleName(),
                nodeList);
    }

    public PlaybackWatermarkTests() throws OoyalaException {
        super();
    }

    @Test(groups = "alice", dataProvider = "testUrls")
    public void testBasicPlaybackAlice(String testName, String url) throws OoyalaException {

        boolean result = false;
        PlayValidator play = pageFactory.getPlayValidator();
        SeekValidator seek = pageFactory.getSeekValidator();
        PlayAction playAction = pageFactory.getPlayAction();
        EventValidator eventValidator = pageFactory.getEventValidator();
        VolumeValidator volumeValidator = pageFactory.getVolumeValidator();
        PauseValidator pause = pageFactory.getPauseValidator();
        WaterMarkValidator waterMarkValidator = pageFactory.getWaterMarkValidator();

        try {
            driver.get(url);
            if (!getPlatform().equalsIgnoreCase("android")) {
                driver.manage().window().maximize();
            }

            play.waitForPage();

            injectScript("http://10.11.66.55:8080/alice.js");

            playAction.startAction();

            Boolean isAdplaying = (Boolean) (((JavascriptExecutor) driver).executeScript("return pp.isAdPlaying()"));
            if (isAdplaying) {
                volumeValidator.validate("VolumeMax", 60);
                eventValidator.validate("adPodEnded_1", 200);
            }

            play.validate("playing_1", 60);
            Thread.sleep(3000);

            pause.validate("paused_1",60);

            waterMarkValidator.validate("watermarklogo",60);

            playAction.startAction();

            seek.validate("seeked_1", 60);

            eventValidator.validate("videoPlayed_1",60);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(result, "Alice basic playback tests failed");
    }
}
