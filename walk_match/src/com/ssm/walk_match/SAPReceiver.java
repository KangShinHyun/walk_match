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
		else if(num ==3)
		{
			Intent serviceIntent = new Intent();
			serviceIntent.putExtra("tease", 1);
			serviceIntent.setAction("SAPTeaseService");
			context.startService(serviceIntent);
		}
		else if(num ==4)
		{
			Intent serviceIntent = new Intent();
			serviceIntent.putExtra("tease", 2);
			serviceIntent.setAction("SAPTeaseService");
			context.startService(serviceIntent);
		}
		else if(num ==5)
		{
			Intent serviceIntent = new Intent();
			serviceIntent.putExtra("tease", 3);
			serviceIntent.setAction("SAPTeaseService");
			context.startService(serviceIntent);
			
		}
		else
		{
			Log.d("SAPReceiver", "SAPReceiver error");
		}
		
		
	}
}
