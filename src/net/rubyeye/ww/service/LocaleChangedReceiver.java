package net.rubyeye.ww.service;

import net.rubyeye.ww.WhetherWeatherSetting;
import net.rubyeye.ww.utils.Constants;
import net.rubyeye.ww.utils.UriUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
/**
 * Locale changed receiver
 * @author dennis
 *
 */
public class LocaleChangedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.settings.LOCALE_SETTINGS")) {
			Log.i(Constants.LOGTAG, " Locale was changed,refetch weather info");
			Uri uri = UriUtils
					.createUri(WhetherWeatherSetting.getCity(context));
			Intent serviceIntent = new Intent(Intent.ACTION_RUN, uri);
			serviceIntent.putExtra(Constants.EXTRA_UPDATE_AT_NOW, true);
			context.startService(serviceIntent);
		}

	}

}
