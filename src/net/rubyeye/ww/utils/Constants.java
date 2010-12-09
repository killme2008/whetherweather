package net.rubyeye.ww.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
	public static final String LOGTAG = "WhetherWeather";
	public static final String WW_PREFS = "WW_PREFS";
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd kk:mm:ss", Locale.CHINA);
	public static final String ACTION_STOP = "net.rubyeye.ww.STOP_WEATHER_SERVICE";
	public static final String ACTION_NETWORK_CHANGED = "net.rubyeye.ww.NETWORK_STATE_CHANGED";
	public static final String ACTION_CONFIG = "net.rubyeye.ww.CONFIG";
	public static final String ENCODING = "utf-8";
	public static final String EXTRA_UPDATE_AT_NOW = "update_at_now";
}
