package net.rubyeye.ww;

import java.text.DateFormat;
import java.util.Date;

import net.rubyeye.ww.data.Weather;
import net.rubyeye.ww.data.WeatherData;
import net.rubyeye.ww.utils.Constants;
import net.rubyeye.ww.utils.UriUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Weather detail activity
 * 
 * @author dennis
 * 
 */
public class WeatherDetail extends Activity {

	static final String MAP_KEY = "bitmap";
	private TextView detailDay;
	private TextView detailCondition;
	private TextView detailCurrentTemp;
	private TextView detailWindCond;
	private TextView detailHumidity;
	private TextView detailLowTemp;
	private TextView detailHighTemp;
	private TextView detailCity;
	private ImageView imageView;
	private TextView detailLastUpdate;

	private final Handler handler = new Handler();

	private ProgressDialog progressDialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_detail);
		detailDay = (TextView) findViewById(R.id.detail_day);
		detailCondition = (TextView) findViewById(R.id.detail_condition);
		detailWindCond = (TextView) findViewById(R.id.detail_win_cond);
		detailHumidity = (TextView) findViewById(R.id.detail_humidity);
		detailCurrentTemp = (TextView) findViewById(R.id.detail_current_temp);
		detailLowTemp = (TextView) findViewById(R.id.detail_low_temp);
		detailHighTemp = (TextView) findViewById(R.id.detail_high_temp);
		detailCity = (TextView) findViewById(R.id.detail_city);
		imageView = (ImageView) findViewById(R.id.detail_image);
		detailLastUpdate = (TextView) findViewById(R.id.detail_last_update);

		Button updateButton = (Button) findViewById(R.id.update_button);
		updateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				progressDialog = ProgressDialog.show(WeatherDetail.this,
						getResources().getString(R.string.progress_title),
						getResources().getString(R.string.progress_info), true);
				new Thread() {
					@Override
					public void run() {
						Uri uri = UriUtils.createUri(WhetherWeatherSetting
								.getCity(WeatherDetail.this));
						Intent intent = new Intent(Intent.ACTION_RUN, uri);
						intent.putExtra(Constants.EXTRA_UPDATE_AT_NOW, true);
						WeatherDetail.this.startService(intent);
						handler.post(new Runnable() {
							@Override
							public void run() {
								fillData();
							}
						});
						progressDialog.dismiss();

					}
				}.start();
			}
		});

		Button configButton = (Button) findViewById(R.id.config_button);
		configButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("weather://net.rubyeye/config");
				Intent intent = new Intent(Constants.ACTION_CONFIG, uri);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				WeatherDetail.this.startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fillData();
	}

	@Override
	protected void onStart() {
		super.onStart();
		fillData();
	}

	private void fillData() {
		WeatherApp weatherApp = (WeatherApp) getApplication();
		final WeatherData weatherData = weatherApp.getWeatherData();
		Weather weather = null;
		if (weatherData != null) {
			weather = weatherData.todayWeather;
		}
		final Bitmap weatherImage = weatherApp.getWeatherImage();
		if (weatherData != null && weather != null) {
			detailCity.setText(weatherData.city);
			detailCondition.setText(weather.condition);
			String unit = weatherData.unit.getUnit();
			detailLowTemp.setText(weather.lowTemp + unit);
			detailHighTemp.setText(weather.highTemp
					+ unit);
			detailDay.setText(weather.day);
			detailCurrentTemp.setText(weatherData.currentTemp+unit);
			detailWindCond.setText(weatherData.windCondition);
			detailHumidity.setText(weatherData.humidity);
			if (weatherImage != null) {
				imageView.setImageBitmap(weatherImage);
			}
			DateFormat simpleDateFormat = DateFormat.getDateTimeInstance();
			String lastUpdate = WhetherWeatherSetting.getLastUpdate(this);
			try {
				Date date = Constants.simpleDateFormat.parse(lastUpdate);
				// formate by default locale
				detailLastUpdate.setText(simpleDateFormat.format(date));
			} catch (Exception e) {
				Log.e(Constants.LOGTAG, " parse last_update error", e);
			}
		} else {
			if (detailCity.getText() == null
					|| detailCity.getText().length() == 0) {
				detailCity.setText(getResources().getString(
						R.string.no_data_label));
			}
		}
	}

}
