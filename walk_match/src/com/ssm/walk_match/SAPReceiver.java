package com.ssm.walk_match;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class SAPReceiver extends BroadcastReceiver{
	
	public void onReceive(Context context, Intent intent)
	{
		Log.d("SAPReceiver","SAPReceiver in");
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("SAPService");
		context.startService(serviceIntent);
	}
}
