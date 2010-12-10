package net.rubyeye.ww.data;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX handler to parse fetched result
 * 
 * @author dennis
 * 
 */
public class WeatherHandler extends DefaultHandler {

	private List<Weather> weatherList;

	private boolean inForecast;

	private Weather currentWeather;
	private Unit unit;
	private String currentTemp;
	private String windCondition;
	private String humidity;
	private String city;

	public WeatherData getWeatherData() {
		return new WeatherData(weatherList, currentTemp, windCondition, unit,
				humidity, city);
	}

	public void setWeatherList(List<Weather> weatherList) {
		this.weatherList = weatherList;
	}

	public WeatherHandler() {
		weatherList = new ArrayList<Weather>();
		inForecast = false;

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		String tagName = localName.length() != 0 ? localName : qName;
		tagName = tagName.toLowerCase();

		if (tagName.equals("forecast_conditions")) {
			inForecast = true;
			currentWeather = new Weather();
		}
		if (tagName.equals("unit_system")) {
			unit = Unit.valueOf(attributes.getValue("data"));
		}
		if (tagName.equals("temp_f")) {
			if (unit == Unit.US) {
				currentTemp = attributes.getValue("data");
			}
		}
		if (tagName.equals("temp_c")) {
			if (unit == Unit.SI) {
				currentTemp = attributes.getValue("data");
			}
		}
		if (tagName.equals("humidity")) {
			this.humidity = attributes.getValue("data");
		}
		if (tagName.equals("wind_condition")) {
			this.windCondition = attributes.getValue("data");

		}
		if (tagName.equals("city")) {
			this.city = attributes.getValue("data");
		}

		if (inForecast) {
			if (tagName.equals("day_of_week")) {
				currentWeather.day = (attributes.getValue("data"));
			} else if (tagName.equals("low")) {
				currentWeather.lowTemp = (attributes.getValue("data"));
			} else if (tagName.equals("high")) {
				currentWeather.highTemp = (attributes.getValue("data"));
			} else if (tagName.equals("icon")) {
				currentWeather.imageUrl = "http://www.google.com"
						+ (attributes.getValue("data"));
			} else if (tagName.equals("condition")) {
				currentWeather.condition = (attributes.getValue("data"));
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		String tagName = localName.length() != 0 ? localName : qName;
		tagName = tagName.toLowerCase();

		if (tagName.equals("forecast_conditions")) {
			inForecast = false;
			weatherList.add(currentWeather);
		}
	}

}