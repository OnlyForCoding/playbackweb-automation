package com.ooyala.playback.VTC;

import com.ooyala.playback.PlaybackWebTest;
import com.ooyala.playback.page.EventValidator;
import com.ooyala.playback.page.PlayValidator;
import com.ooyala.playback.page.SeekValidator;
import com.ooyala.playback.url.UrlObject;
import com.ooyala.qe.common.exception.OoyalaException;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by jitendra on 25/11/16.
 */
public class PlaybackVideoControllerEventTests extends PlaybackWebTest {

	private EventValidator eventValidator;
	private PlayValidator play;
	private SeekValidator seekValidator;

	public PlaybackVideoControllerEventTests() throws OoyalaException {
		super();
	}

	@Test(groups = "Playback", dataProvider = "testUrls")
	public void testVideoControllerEvents(String testName, UrlObject url) {

		boolean result = true;

		try {
			driver.get(url.getUrl());

			result = result && play.waitForPage();

			injectScript();

			result = result && play.validate("playing_1",60000);

            result = result && eventValidator.validate("focusVideo_1", 20000);

            result = result && eventValidator.validate("videoInFocus_1", 20000);

            result = result && seekValidator.validate("seeked_1", 60);

            result = result && eventValidator.validate("videoLostFocus_1", 60000);


            ((JavascriptExecutor) driver)
					.executeScript("pp.setEmbedCode('htcmtjczpHnIEJLJUrZ8YUs0CW0pyi2R')");


			result = result && eventValidator.validate("disposeVideo_1", 20000);

			result = result
					&& eventValidator.validate("videoElementDisposed_1", 20000);

			result = result && eventValidator.validate("setVideoStream_1",20000);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertTrue(result, "Playback Video Controller Event test failed");

	}
}
