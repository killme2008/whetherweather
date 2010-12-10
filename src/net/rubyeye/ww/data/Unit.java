package net.rubyeye.ww.data;

public enum Unit {
	SI, US;

	public String getUnit() {
		switch (this) {
		case SI:
			return "°C";
		case US:
			return "°F";
		}
		return "";
	}
}