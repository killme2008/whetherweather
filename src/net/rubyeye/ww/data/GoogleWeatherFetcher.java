package net.rubyeye.ww.data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.rubyeye.ww.utils.Constants;
import net.rubyeye.ww.utils.UriUtils;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

/**
 * Invoke google Weather API and parse into Weather.
 * 
 * @see WeatherHandler
 * 
 * @author dennis
 * 
 */
public class GoogleWeatherFetcher {

	private static final String CLASSTAG = GoogleWeatherFetcher.class
			.getSimpleName();
	static final String DEFAULT_LAN = "zh_CN";
	private static final String QBASE = "http://www.google.com/ig/api?hl=%s&weather=%s&ie="
			+ Constants.ENCODING + "&oe=" + Constants.ENCODING;

	private String query;
	private String city;
	private String locale;

	public GoogleWeatherFetcher(String city, String locale) {
		if (city == null || city.length() == 0 || locale == null
				|| locale.length() == 0) {
			return;
		}

		this.city = UriUtils.encodeURL(city);
		this.locale = locale;
		this.query = String.format(QBASE, this.locale, this.city);
	}

	public GoogleWeatherFetcher(String city) {
		this(city, DEFAULT_LAN);
	}

	public String getCharset() {
		return Constants.ENCODING;
	}

	public WeatherData getWeather() {
		WeatherData r = null;

		try {
			URL url = new URL(this.query);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			WeatherHandler handler = new WeatherHandler();
			xr.setContentHandler(handler);
			InputStream stream = url.openStream();
			InputStreamReader streamReader = new InputStreamReader(stream,
					getCharset());
			xr.parse(new InputSource(streamReader));
			r = handler.getWeatherData();
		} catch (Exception e) {
			Log.e(Constants.LOGTAG, " " + GoogleWeatherFetcher.CLASSTAG, e);
		}
		return r;
	}

}
