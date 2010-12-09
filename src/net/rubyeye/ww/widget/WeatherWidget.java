package net.rubyeye.ww.widget;

import net.rubyeye.ww.WhetherWeatherSetting;
import net.rubyeye.ww.utils.Constants;
import net.rubyeye.ww.utils.UriUtils;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Weather widget
 * 
 * @author dennis
 * 
 */
public class WeatherWidget extends AppWidgetProvider {
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.i(Constants.LOGTAG, "Remove weather widget");
		String url = "weather://net.rubyeye/stop";
		Intent intent = new Intent(Constants.ACTION_STOP, Uri.parse(url));
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		context.startService(intent);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.i(Constants.LOGTAG, "Update weather widget");
		startWeatherService(context);
	}

	private void startWeatherService(Context context) {
		Intent intent = new Intent(Intent.ACTION_RUN,
				UriUtils.createUri(WhetherWeatherSetting.getCity(context)));
		intent.putExtra(Constants.EXTRA_UPDATE_AT_NOW, true);
		context.startService(intent);
	}

}
