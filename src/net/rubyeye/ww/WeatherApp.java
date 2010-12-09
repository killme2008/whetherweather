package net.rubyeye.ww;

import net.rubyeye.ww.data.Weather;
import android.app.Application;
import android.graphics.Bitmap;

public class WeatherApp extends Application {
	private Weather weather;
	private Bitmap weatherImage;

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public Bitmap getWeatherImage() {
		return weatherImage;
	}

	public void setWeatherImage(Bitmap weatherImage) {
		this.weatherImage = weatherImage;
	}

}
