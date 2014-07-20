package com.ssm.walk_match;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class SAPReceiver extends BroadcastReceiver{
	
	public void onReceive(Context context, Intent intent)
	{
		Log.d("SAPReceiver","SAPReceiver in");
		int num = intent.getIntExtra("flag", 0);
		if(num == 1)
		{
			Intent serviceIntent = new Intent();
			serviceIntent.setAction("SAPService");
			context.startService(serviceIntent);
		}
		else if(num ==2)
		{
			Intent serviceIntent = new Intent();
			serviceIntent.setAction("SAPMatchService");
			context.startService(serviceIntent);
		}
		else
		{
			Log.d("SAPReceiver", "SAPReceiver error");
		}
		
		
	}
}
