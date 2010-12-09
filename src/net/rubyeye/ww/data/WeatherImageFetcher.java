package net.rubyeye.ww.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import net.rubyeye.ww.utils.Constants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Fetch weather image
 * 
 * @author dennis
 * 
 */
public class WeatherImageFetcher {
	private String imageUrl;
	private static final String CLASSTAG = WeatherImageFetcher.class
			.getSimpleName();
	static final int TIMEOUT = 10000; // default timeout,10 seconds

	public WeatherImageFetcher(String url) {
		imageUrl = url;
	}

	public Bitmap getImage() {
		InputStream inputStream = null;
		BufferedInputStream bis = null;
		try {
			URL url = new URL(this.imageUrl);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(TIMEOUT);
			conn.setReadTimeout(TIMEOUT);
			conn.connect();
			inputStream = conn.getInputStream();
			bis = new BufferedInputStream(inputStream, 8192);
			Bitmap bm = BitmapFactory.decodeStream(bis);
			return bm;
		} catch (IOException e) {
			Log.e(Constants.LOGTAG, " " + CLASSTAG, e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// ignore
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return null;
	}

	public static Bitmap getWeatherImage(Weather today) {
		WeatherImageFetcher weatherImageFetcher = new WeatherImageFetcher(
				today.imageUrl);
		Bitmap image = weatherImageFetcher.getImage();
		return image;
	}

}
