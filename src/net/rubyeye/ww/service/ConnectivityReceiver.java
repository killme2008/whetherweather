package net.rubyeye.ww.service;

import net.rubyeye.ww.utils.Constants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
/**
 * ConnectivityReceiver
 * @author dennis
 *
 */
public class ConnectivityReceiver extends BroadcastReceiver {
	static String CLAATAG = ConnectivityReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			Log.w(Constants.LOGTAG,
					" "
							+ CLAATAG
							+ "network state was changed,maybe have to try refetch weather info");
			NetworkInfo networkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			String url = "weather://net.rubyeye/network";
			Intent serviceIntent = new Intent(Constants.ACTION_NETWORK_CHANGED,
					Uri.parse(url));
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			serviceIntent.putExtra(ConnectivityManager.EXTRA_NETWORK_INFO,
					networkInfo);
			context.startService(serviceIntent);

		}

	}

}
