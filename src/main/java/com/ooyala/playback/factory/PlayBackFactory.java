package com.ooyala.playback.factory;

import java.lang.reflect.Field;

import com.ooyala.playback.page.*;
import org.openqa.selenium.WebDriver;
import com.ooyala.playback.page.action.AutoplayAction;
import com.ooyala.playback.page.action.ClickDiscoveryButtonAction;
import com.ooyala.playback.page.action.FullScreenAction;
import com.ooyala.playback.page.action.LiveAction;
import com.ooyala.playback.page.action.PauseAction;
import com.ooyala.playback.page.action.PlayAction;
import com.ooyala.playback.page.action.PlayPauseAction;
import com.ooyala.playback.page.action.SeekAction;

public class PlayBackFactory {

	private static PlayBackFactory playbackFactory;
	private WebDriver driver;

	private CCValidator ccValidator;
	private DiscoveryValidator discoveryValidator;
	private FullScreenValidator fullScreenValidator;
	private PauseValidator pauseValidator;
	private PlayValidator playValidator;
	private SeekValidator seekValidator;
	private UpNextValidator upNextValidator;
	private PauseAction pauseAction;
	private PlayAction playAction;
	private AutoplayAction autoplay;
	private EventValidator eventValidator;
	private AspectRatioValidator aspectRatioValidator;
	private LiveAction liveAction;
	private ShareTabValidator shareTabValidator;
	private WaterMarkValidator waterMarkValidator;
	private VolumeValidator volumeValidator;
	private PlayPauseAction playPauseAction;
	private ControlBarValidator controlBarValidator;
	private StartScreenValidator startScreenValidator;
	private EndScreenValidator endScreenValidator;
	private ReplayValidator replayValidator;
	private ClickDiscoveryButtonAction clickDiscoveryButtonAction;
	private Bitratevalidator bitratevalidator;
    private SocailScreenValidator socailScreenValidator;
    private ThumbnailValidator thumbnailValidator;
	private FullScreenAction fullScreenAction;
	private SeekAction seekAction;

	private PlayBackFactory(WebDriver driver) {
		this.driver = driver;
	}

	public SeekAction getSeekAction(){
		if(seekAction==null)
			seekAction = new SeekAction(driver);
		return seekAction;
			
	}
	
	public FullScreenAction getFullScreenAction(){
		if(fullScreenAction==null)
			fullScreenAction = new FullScreenAction(driver);
		return fullScreenAction;
	}
	
	public PauseAction getPauseAction() {
		if (pauseAction == null)
			pauseAction = new PauseAction(driver);
		return pauseAction;
	}

	public PlayAction getPlayAction() {
		if (playAction == null)
			playAction = new PlayAction(driver);
		return playAction;
	}

	public CCValidator getCCValidator() {
		if (ccValidator == null)
			ccValidator = new CCValidator(driver);
		return ccValidator;
	}

	public UpNextValidator getUpNextValidator() {
		if (upNextValidator == null)
			upNextValidator = new UpNextValidator(driver);
		return upNextValidator;
	}

	public SeekValidator getSeekValidator() {
		if (seekValidator == null)
			seekValidator = new SeekValidator(driver);
		return seekValidator;
	}

	public PlayValidator getPlayValidator() {
		if (playValidator == null)
			playValidator = new PlayValidator(driver);
		return playValidator;
	}

	public PauseValidator getPauseValidator() {
		if (pauseValidator == null)
			pauseValidator = new PauseValidator(driver);
		return pauseValidator;
	}

	public FullScreenValidator getFullScreenValidator() {
		if (fullScreenValidator == null)
			fullScreenValidator = new FullScreenValidator(driver);
		return fullScreenValidator;
	}

	public DiscoveryValidator getDiscoveryValidator() {
		if (discoveryValidator == null)
			discoveryValidator = new DiscoveryValidator(driver);
		return discoveryValidator;
	}

	public EventValidator getEventValidator() {
		if (eventValidator == null)
			eventValidator = new EventValidator(driver);
		return eventValidator;
	}

	public AspectRatioValidator getAspectRatioValidator() {
		if (aspectRatioValidator == null)
			aspectRatioValidator = new AspectRatioValidator(driver);
		return aspectRatioValidator;
	}

	public ShareTabValidator getShareTabValidator() {
		if (shareTabValidator == null)
			shareTabValidator = new ShareTabValidator(driver);
		return shareTabValidator;
	}

	public VolumeValidator getVolumeValidator() {
		if (volumeValidator == null)
			volumeValidator = new VolumeValidator(driver);
		return volumeValidator;
	}

	public WaterMarkValidator getWaterMarkValidator() {
		if (waterMarkValidator == null)
			waterMarkValidator = new WaterMarkValidator(driver);
		return waterMarkValidator;
	}

	public ControlBarValidator getControlBarValidator() {
		if (controlBarValidator == null)
			controlBarValidator = new ControlBarValidator(driver);
		return controlBarValidator;
	}

	public StartScreenValidator getStartScreenValidator() {
		if (startScreenValidator == null)
			startScreenValidator = new StartScreenValidator(driver);
		return startScreenValidator;
	}

	public ClickDiscoveryButtonAction getClickDiscoveryButtonAction() {
		if (clickDiscoveryButtonAction == null)
			clickDiscoveryButtonAction = new ClickDiscoveryButtonAction(driver);
		return clickDiscoveryButtonAction;
	}

	public EndScreenValidator getEndScreenValidator() {
		if (endScreenValidator == null)
			endScreenValidator = new EndScreenValidator(driver);
		return endScreenValidator;
	}

	public ReplayValidator getReplayValidator() {
		if (replayValidator == null)
			replayValidator = new ReplayValidator(driver);
		return replayValidator;
	}

	public Bitratevalidator getBitratevalidator() {
		if (bitratevalidator == null)
			bitratevalidator = new Bitratevalidator(driver);
		return bitratevalidator;
	}
    public ThumbnailValidator getThumbnailValidator() {
        if (thumbnailValidator == null)
            thumbnailValidator = new ThumbnailValidator(driver);
        return thumbnailValidator;
    }

    public SocailScreenValidator getSocailScreenValidator() {
        if (socailScreenValidator == null)
            socailScreenValidator = new SocailScreenValidator(driver);
        return socailScreenValidator;
    }


    public PlayPauseAction getPlayPauseAction() {
		if (playPauseAction == null)
			playPauseAction = new PlayPauseAction(driver);
		return playPauseAction;
	}

	public AutoplayAction getAutoplay() {
		if (autoplay == null)
			autoplay = new AutoplayAction(driver);
		return autoplay;
	}

	public LiveAction getLiveAction() {
		if (liveAction == null)
			liveAction = new LiveAction(driver);
		return liveAction;
	}

	public static PlayBackFactory getInstance(WebDriver driver) {
		if (playbackFactory == null || playbackFactory.getDriver() == null) {
			playbackFactory = new PlayBackFactory(driver);
		}
		return playbackFactory;
	}

	public static void destroyInstance() {
		playbackFactory = null;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public <T> T getObject(Class<T> validator) throws Exception {

		Field[] fs = this.getClass().getDeclaredFields();
		fs[0].setAccessible(true);
		for (Field property : fs) {
			if (property.getType().isAssignableFrom(validator)) {
				if (property.get(this) == null)
					property.set(this, validator
							.getConstructor(WebDriver.class)
							.newInstance(driver));
				return (T) property.get(this);
			}

		}
		return null;
	}
	
}
