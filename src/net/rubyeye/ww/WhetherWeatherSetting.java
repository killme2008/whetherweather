package net.rubyeye.ww;

import java.util.Date;

import net.rubyeye.ww.utils.Constants;
import net.rubyeye.ww.utils.UriUtils;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class WhetherWeatherSetting extends PreferenceActivity {
	private final class PreferenceChangedListener implements
			SharedPreferences.OnSharedPreferenceChangeListener {
		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			// When update_interval or city was changed,start weather
			// service
			if (key != null) {
				if ((key.equals(getResources().getString(
						R.string.update_interval_key)))) {
					rescheduleService();
				} else if (key.equals(getResources().getString(
						R.string.city_key))) {
					updateWeather();
				}

			}

		}

		private void updateWeather() {
			progressDialog = ProgressDialog.show(WhetherWeatherSetting.this,
					getResources().getString(R.string.progress_title),
					getResources().getString(R.string.progress_info), true);
			new Thread() {
				@Override
				public void run() {
					Uri uri = UriUtils.createUri(WhetherWeatherSetting
							.getCity(WhetherWeatherSetting.this));
					Intent intent = new Intent(Intent.ACTION_RUN, uri);
					intent.putExtra(Constants.EXTRA_UPDATE_AT_NOW, true);
					startService(intent);
					progressDialog.dismiss();
				}
			}.start();
		}

		private void rescheduleService() {
			Log.i(Constants.LOGTAG,
					"Update interval was changed,restart timer task");
			Uri uri = UriUtils.createUri(WhetherWeatherSetting
					.getCity(WhetherWeatherSetting.this));
			Intent intent = new Intent(Intent.ACTION_RUN, uri);
			intent.putExtra(RESCHEDULE, true);
			intent.putExtra(Constants.EXTRA_UPDATE_AT_NOW, false);
			startService(intent);
		}
	}

	public static final String RESCHEDULE = "reschedule";
	private static final int DELAY = 12;
	static final String UPDATE_INTERVAL = "update_interval";
	private ProgressDialog progressDialog = null;
	private PreferenceChangedListener listener = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.preferences);
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(this);
		listener = new PreferenceChangedListener();
		spf.registerOnSharedPreferenceChangeListener(listener);

	}

	@Override
	protected void onStop() {
		super.onStop();
		setResult(RESULT_OK);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(this);
		spf.unregisterOnSharedPreferenceChangeListener(listener);
	}

	private static final String LAST_UPDATE = "last_update";

	private static final String LAST_LOW_TEMP = "last_low_temp";

	public static String getCity(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(
				ctx.getResources().getString(R.string.city_key), "北京");

	}

	/**
	 * Update last update time stamp to now
	 * 
	 * @param ctx
	 */
	public static void setCity(Context ctx, String city) {
		PreferenceManager
				.getDefaultSharedPreferences(ctx)
				.edit()
				.putString(ctx.getResources().getString(R.string.city_key),
						city).commit();
	}

	public static final int INVALID_LOW_TEMP = -10000;

	public static String getLastLowTemp(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(
				LAST_LOW_TEMP, String.valueOf(INVALID_LOW_TEMP));

	}

	/**
	 * Store last low temp in SI unit.
	 * 
	 * @param ctx
	 */
	public static void setLastLowTemp(Context ctx, String lowTemp) {
		PreferenceManager.getDefaultSharedPreferences(ctx).edit()
				.putString(LAST_LOW_TEMP, lowTemp).commit();
	}

	public static String getLastUpdate(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(
				LAST_UPDATE, Constants.simpleDateFormat.format(new Date()));

	}

	/**
	 * Update last update time stamp to now
	 * 
	 * @param ctx
	 */
	public static void updateLastUpdate(Context ctx) {
		PreferenceManager
				.getDefaultSharedPreferences(ctx)
				.edit()
				.putString(LAST_UPDATE,
						Constants.simpleDateFormat.format(new Date())).commit();
	}

	public static int getUpdateInterval(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(
				ctx.getResources().getString(R.string.update_interval_key),
				DELAY);

	}

	public static boolean isTempUpdateRemainderEnable(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
				ctx.getResources().getString(
						R.string.temp_update_reminder_enable_key), true);

	}

	public static boolean isSevereWeatherReminderEnable(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
				ctx.getResources().getString(
						R.string.severe_weather_reminder_key), true);

	}

	public static int getTempUpdateExtent(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(
				ctx.getResources().getString(R.string.temp_update_extent_key),
				5);

	}

}