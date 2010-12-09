package net.rubyeye.ww.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.net.Uri;
import android.util.Log;

public class UriUtils {

	public static Uri createUri(String city) {
		String url = "weather://net.rubyeye/loc?city=" + city;
		return Uri.parse(url);
	}

	public static String encodeURL(String url) {
		try {
			url = URLEncoder.encode(url, Constants.ENCODING);
		} catch (UnsupportedEncodingException e) {
			Log.e(Constants.LOGTAG, e.getMessage(), e);
		}
		return url;
	}
}
