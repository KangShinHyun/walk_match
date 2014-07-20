package com.ssm.walk_match;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ssm.walk_match.main.BaseActivity;
import com.ssm.walk_match.main.FirstActivity;
import com.ssm.walk_match.main.FriendActivity;
import com.ssm.walk_match.main.LoginActivity;
import com.ssm.walk_match.main.MainActivity;
import com.ssm.walk_match.main.MessageActivity;
import com.ssm.walk_match.main.RankActivity;
import com.ssm.walk_match.main.SignActivity;
import com.ssm.walk_match.util.AppManager;

public class SAPService extends Service
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
	public static boolean SAPWalkAction = false;
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
		Log.d("SAPService","SAPService in");
		SAPWalkAction = true;
		if(isActivityTop())
		{
			 Intent intent2 = new Intent(AppManager.getInstance().getActivity(),MainActivity.class);
             intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(intent2);
		}
		else
		{
			NotificationManager nm = (NotificationManager) this
					.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.appicon,
					"Walk Match", System.currentTimeMillis());
			
			PendingIntent pendingIntent = null;
			pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
					this, FirstActivity.class)
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


