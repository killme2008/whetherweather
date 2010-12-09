package net.rubyeye.ww.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Weather data
 * 
 * @author dennis
 * 
 */
public class Weather implements Parcelable {
	public static enum Unit {
		SI, US;

		public static String getUnit(Weather today) {
			switch (today.unit) {
			case SI:
				return "°C";
			case US:
				return "°F";
			}
			return "";
		}
	}

	public Weather() {
		super();
	}

	public String day;

	public String lowTemp;

	public String highTemp;

	public String imageUrl;

	public String condition;
	public Unit unit;

	public String city;
	
	

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(String lowTemp) {
		this.lowTemp = lowTemp;
	}

	public String getHighTemp() {
		return highTemp;
	}

	public void setHighTemp(String highTemp) {
		this.highTemp = highTemp;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {

		@Override
		public Weather createFromParcel(Parcel source) {
			return new Weather(source);
		}

		@Override
		public Weather[] newArray(int size) {
			throw new UnsupportedOperationException();
		}

	};

	private Weather(Parcel in) {
		this.day = in.readString();
		this.lowTemp = in.readString();
		this.highTemp = in.readString();
		this.imageUrl = in.readString();
		this.condition = in.readString();
		this.unit = Unit.values()[in.readInt()];
		this.city = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(day);
		dest.writeString(lowTemp);
		dest.writeString(highTemp);
		dest.writeString(imageUrl);
		dest.writeString(condition);
		dest.writeInt(unit.ordinal());
		dest.writeString(city);
	}

	/**
	 * Check if it is a severe weather
	 * 
	 * @param severeWeathers
	 * @return
	 */
	public boolean isSevereWeather(String[] severeWeathers) {
		if (severeWeathers == null || severeWeathers.length == 0)
			return false;
		for (String severeWeather : severeWeathers) {
			if (this.condition.toLowerCase().contains(
					severeWeather.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Weather [day=" + day + ", lowTemp=" + lowTemp + ", highTemp="
				+ highTemp + ", imageUrl=" + imageUrl + ", condition="
				+ condition + ", unit=" + unit + ", city=" + city + "]";
	}

}