package com.ssm.walk_match;


import java.util.List;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ssm.walk_match.main.BaseActivity;
import com.ssm.walk_match.main.FirstActivity;
import com.ssm.walk_match.main.FriendActivity;
import com.ssm.walk_match.main.LoginActivity;
import com.ssm.walk_match.main.MainActivity;
import com.ssm.walk_match.main.MessageActivity;
import com.ssm.walk_match.main.RankActivity;
import com.ssm.walk_match.main.SignActivity;
import com.ssm.walk_match.util.AppManager;


public class SAPReceiver extends BroadcastReceiver{
	
	public void onReceive(Context context, Intent intent)
	{
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("SAPService");
		context.startService(serviceIntent);
	}
}
