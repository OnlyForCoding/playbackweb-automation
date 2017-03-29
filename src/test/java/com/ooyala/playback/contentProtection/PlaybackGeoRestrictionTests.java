package com.ooyala.playback.contentProtection;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.GeoValidator;
import com.ooyala.qe.common.exception.OoyalaException;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Created by suraj on 3/22/17.
 */
public class PlaybackGeoRestrictionTests extends PlaybackWebTest {

    private static Logger logger = Logger
            .getLogger(PlaybackGeoRestrictionTests.class);
    private GeoValidator geo;

    public PlaybackGeoRestrictionTests() throws OoyalaException{
        super();
    }

    @Test(groups = "syndicationRules", dataProvider = "testUrls")
    public void testGeoRestriction(String testName,String url){
        boolean result = true;
        try{
            driver.get(url);

            result = result && geo.isPageLoaded();

            injectScript();

            result = result && geo.validate("",60000);

        }catch(Exception e){
            logger.error("Error while checking geo restriction" + e);
            extentTest.log(LogStatus.FAIL, e.getMessage());
            result = false;
        }

        Assert.assertTrue(result,"geo restriction test failed");
    }
}