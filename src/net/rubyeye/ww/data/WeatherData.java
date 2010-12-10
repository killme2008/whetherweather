package net.rubyeye.ww.data;

import java.util.List;

/**
 * Feteched data
 * 
 * @author dennis
 * 
 */
public class WeatherData {
	public List<Weather> weathers;
	public Weather todayWeather;
	public String currentTemp;
	public String windCondition;
	public String humidity;
	public Unit unit;
	public String city;

	public WeatherData(List<Weather> weathers, String currentTemp,
			String windCondition, Unit unit, String humidity, String city) {
		super();
		this.weathers = weathers;
		if (!this.weathers.isEmpty()) {
			this.todayWeather = weathers.get(0);
		}
		this.currentTemp = currentTemp;
		this.unit = unit;
		this.windCondition = windCondition;
		this.humidity = humidity;
		this.city = city;
	}
}
