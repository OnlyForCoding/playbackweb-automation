package com.ooyala.playback.alice;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.DiscoveryValidator;
import com.ooyala.playback.page.EventValidator;
import com.ooyala.playback.page.PlayValidator;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.qe.common.exception.OoyalaException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * Created by soundarya on 11/16/16.
 */
public class PlaybackDiscoveryTests extends PlaybackWebTest {
    private PlayValidator play;
    private DiscoveryValidator discoveryValidator;
    private PlayAction playAction ;
    private EventValidator eventValidator ;


    public PlaybackDiscoveryTests() throws OoyalaException {
        super();
    }

    @Test(groups = "Discovery", dataProvider = "testUrls")
    public void testDiscovery(String testName, String url) throws OoyalaException {

        boolean result = false;
       /* PlayValidator play = pageFactory.getPlayValidator();
        DiscoveryValidator discoveryValidator = pageFactory.getDiscoveryValidator();
        PlayAction playAction = pageFactory.getPlayAction();
        EventValidator eventValidator = pageFactory.getEventValidator();*/
        try {
            driver.get(url);
            if (!getPlatform().equalsIgnoreCase("android")) {
                driver.manage().window().maximize();
            }

            play.waitForPage();

            injectScript();
            play.validate("playing_1", 60);

            logger.info("verified video is playing");

            discoveryValidator.validate("reportDiscoveryClick_1", 60);
            logger.info("verified discovery");

            sleep(2000);

            playAction.startAction();

            sleep(2000);

            eventValidator.validate("played_1", 60);
            logger.info("video played");

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(result, "Playback Discovery tests failed");
    }
}
