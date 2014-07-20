package com.ssm.walk_match;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.ssm.walk_match.R;
import com.ssm.walk_match.R.drawable;
import com.ssm.walk_match.main.BaseActivity;
import com.ssm.walk_match.main.FirstActivity;
import com.ssm.walk_match.main.FriendActivity;
import com.ssm.walk_match.main.LodaingFirstActivity;
import com.ssm.walk_match.main.LoginActivity;
import com.ssm.walk_match.main.MainActivity;
import com.ssm.walk_match.main.MessageActivity;
import com.ssm.walk_match.main.RankActivity;
import com.ssm.walk_match.main.SignActivity;
import com.ssm.walk_match.util.AppManager;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;

public class arService extends Service
{
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate() 
	{
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	public static boolean checkAlarm = false;
	private boolean isActivityTop()
    {
         ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
         List<RunningTaskInfo> info;
         info = activityManager.getRunningTasks(1);
         if(info.get(0).topActivity.getClassName().equals(BaseActivity.class.getName()))
         {
              return true;
         }
         else if(info.get(0).topActivity.getClassName().equals(FirstActivity.class.getName()))
         {
        	 return true;
         }
         else if(info.get(0).topActivity.getClassName().equals(FriendActivity.class.getName()))
         {
        	 return true;
         }
         else if(info.get(0).topActivity.getClassName().equals(LoginActivity.class.getName()))
         {
        	 return true;
         }
         else if(info.get(0).topActivity.getClassName().equals(MainActivity.class.getName()))
         {
        	 return true;
         }
         else if(info.get(0).topActivity.getClassName().equals(MessageActivity.class.getName()))
         {
        	 return true;
         }
         else if(info.get(0).topActivity.getClassName().equals(RankActivity.class.getName()))
         {
        	 return true;
         }
         else if(info.get(0).topActivity.getClassName().equals(SignActivity.class.getName()))
         {
        	 return true;
         }
         else
         {
              return false;
         }
    }
	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
		checkAlarm = true;
		
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long milliseconds = 1500;
		vibrator.vibrate(milliseconds);

		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

		PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
				PowerManager.SCREEN_DIM_WAKE_LOCK
						| PowerManager.ACQUIRE_CAUSES_WAKEUP, "TEST_1");

		wakeLock.acquire(15000);
		
		if(isActivityTop())
		{
			BaseActivity.activity_num = BaseActivity.MAIN_ACTIVITY;
			
			Intent intent2 = new Intent(AppManager.getInstance().getActivity(),MainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
            if(AppManager.getInstance().getActivity() != null && !AppManager.getInstance().getActivity().getClass().getName().equals("com.ssm.walk_match.main.MainActivity"))
				AppManager.getInstance().getActivity().finish();	
		}
		else
		{
			
			NotificationManager nm = (NotificationManager) this
					.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.appicon,
					"Walk Match", System.currentTimeMillis());
			
			PendingIntent pendingIntent = null;
			pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
					this, LodaingFirstActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET),
					PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(this, "Walk Match", "Match End!",
					pendingIntent);
			
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nm.cancel(0);
			nm.notify(0, notification);
		}
	}
}


