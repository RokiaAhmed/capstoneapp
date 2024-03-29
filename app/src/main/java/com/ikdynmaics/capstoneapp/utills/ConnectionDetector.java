package com.ikdynmaics.capstoneapp.utills;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

public class ConnectionDetector {
	private final Context mContext;

	public ConnectionDetector(Context context) {
		this.mContext = context;
	}

	/**
	 * Checking for all possible internet providers
	 **/
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            assert connectivityManager != null;
            Network[] networks = connectivityManager.getAllNetworks();
			NetworkInfo networkInfo;
			for (Network mNetwork : networks) {
				networkInfo = connectivityManager.getNetworkInfo(mNetwork);
				if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
					return true;
				}
			}
		} else {
			if (connectivityManager != null) {
				//noinspection deprecation
				NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
				if (info != null) {
					for (NetworkInfo anInfo : info) {
						if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
							//   LogUtils.d("Network",  "NETWORK NAME: " + anInfo.getTypeName());
							return true;
						}
					}
				}
			} else {

				return false;
			}
		}
		//   Toast.makeText(mContext, mContext.getString(R.string.please_connect_to_internet), Toast.LENGTH_SHORT).show();
		return false;
	}
}