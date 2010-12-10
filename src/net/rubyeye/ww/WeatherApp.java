package net.rubyeye.ww;

import net.rubyeye.ww.data.WeatherData;
import android.app.Application;
import android.graphics.Bitmap;

/**
 * Weather app to store global weather info
 * 
 * @author dennis
 * 
 */
public class WeatherApp extends Application {
	private WeatherData weatherData;
	private Bitmap weatherImage;

	public WeatherData getWeatherData() {
		return weatherData;
	}

	public void setWeatherData(WeatherData weatherData) {
		this.weatherData = weatherData;
	}

	public Bitmap getWeatherImage() {
		return weatherImage;
	}

	public void setWeatherImage(Bitmap weatherImage) {
		this.weatherImage = weatherImage;
	}

}
