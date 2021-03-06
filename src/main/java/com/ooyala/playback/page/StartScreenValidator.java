package com.ooyala.playback.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

/**
 * Created by soundarya on 11/16/16.
 */
public class StartScreenValidator extends PlayBackPage implements
		PlaybackValidator {
	private static Logger logger = Logger.getLogger(StartScreenValidator.class);

	public StartScreenValidator(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		addElementToPageElements("startscreen");
	}

	@Override
	public boolean validate(String element, int timeout) throws Exception {

		if (!waitOnElement("STATE_SCREEN_POSTER", 60000)) {
			return false;
		}

		boolean flag = true;

		// get the style attribute of class startScreenPoster which contailns
		// preview image url so that we compare it.
		String value = getWebElement("STATE_SCREEN_POSTER").getAttribute(
				"style");

		if (value != null) {
			String url = value.replaceAll(".*\\(|\\).*", "");
			url = url.replaceAll("^\"|\"$", "");

			if (!url.contains("http://cf.c.ooyala.com/piMXdiczqydplt6ojmhNzdfAERdgVvaj/3Gduepif0T1UGY8H4xMDoxOjBiO1q_Vi")) {
				extentTest.log(LogStatus.FAIL, "Preview Image is not matching");
				flag = false;
			}
		} else {
			extentTest.log(LogStatus.FAIL,
					"STATE_SCREEN_POSTER style attribute returns null.");
			flag = false;
		}

		// get title of video
		try {

			String startScreenTitle = getWebElement("STATE_SCREEN_TITLE")
					.getText();

			// get Discription of video
			String description = getWebElement("STATE_SCREEN_DEC").getText();

			String title = ((JavascriptExecutor) driver).executeScript(
					"var title=pp.getTitle();" + "{return title;}").toString();

			String desc = ((JavascriptExecutor) driver).executeScript(
					"var description=pp.getDescription();"
							+ "{return description;}").toString();
			if (!startScreenTitle.equalsIgnoreCase(title)) {
				extentTest.log(LogStatus.FAIL,
						"Title is not matching on start screen");
				flag = false;
			}

			if (!description.trim().equalsIgnoreCase(desc.trim())) {
				extentTest.log(LogStatus.FAIL,
						"Description is not matching on Start Screen");
				flag = false;
			}

		} catch (Exception e) {
			logger.error("Title or description is failing");
			flag = false;
		}
		return flag;
	}
}
