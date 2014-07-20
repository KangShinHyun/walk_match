package com.ssm.walk_match;


import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.ssm.walk_match.R;
import com.ssm.walk_match.R.drawable;
import com.ssm.walk_match.main.BaseActivity;
import com.ssm.walk_match.main.FirstActivity;
import com.ssm.walk_match.main.FriendActivity;
import com.ssm.walk_match.main.LodaingFirstActivity;
import com.ssm.walk_match.main.LoginActivity;
import com.ssm.walk_match.main.MainActivity;
import com.ssm.walk_match.main.MeActivity;
import com.ssm.walk_match.main.MessageActivity;
import com.ssm.walk_match.main.RankActivity;
import com.ssm.walk_match.main.SignActivity;
import com.ssm.walk_match.util.AppManager;


public class GCMIntentService extends GCMBaseIntentService {
	private static final String tag = "GCMIntentService";
	public static final String SEND_ID = "949552179833";
	public static int checkStyleTesase = 0; //놀리기
	public static String tesaseEmail = ""; //놀리기 
	public static boolean checkMatchReGCM = false; //매치성사
	public static String matchUpEmail = ""; //매치성사된애 이메일 
	public static boolean isPopup = true;

	public static boolean receiveGiveUp = false;
	public static int whoWin = -1;
	
	public GCMIntentService() {
		this(SEND_ID);
	}

	public GCMIntentService(String senderId) {
		super(senderId);
	}
	
	private boolean  isActivityTop()
    {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
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
         else if(info.get(0).topActivity.getClassName().equals(MeActivity.class.getName()))
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
	protected void onMessage(Context context, Intent intent) {
		try{
		Bundle b = intent.getExtras();
		String checkData = null;
		String checkKey = "check";
		checkData = b.get(checkKey).toString();
		
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long milliseconds = 1500;
		vibrator.vibrate(milliseconds);

		PowerManager powerManager = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);

		PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
				PowerManager.SCREEN_DIM_WAKE_LOCK
						| PowerManager.ACQUIRE_CAUSES_WAKEUP, "TEST_1");

		wakeLock.acquire(15000);
		
		if(checkData.equals("1")) // 놀리기
		{
			//놀리기
			String value = null;
			String value1 = null;
			String key = "msg";
			String key1 = "msg1";
			value = b.get(key).toString();
			value1 = b.get(key1).toString();
			tesaseEmail = value1;
			

			if(value.equals("1") )
			{
				checkStyleTesase = 1;
			}
			else if(value.equals("2"))
			{
				checkStyleTesase = 2;
			}
			else
			{
				checkStyleTesase =3;
			}
			if(isActivityTop())
			{
				//앱켜져있음
				 
				BaseActivity.activity_num = BaseActivity.MAIN_ACTIVITY;
				
				Intent intent2 = new Intent(AppManager.getInstance().getActivity(),MainActivity.class);
	            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            startActivity(intent2);
	            if(AppManager.getInstance().getActivity() != null && !AppManager.getInstance().getActivity().getClass().getName().equals("com.ssm.walk_match.main.MainActivity"))
					AppManager.getInstance().getActivity().finish();	
	             
			}
			else
			{
				NotificationManager nm = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.appicon,
						"Walk Match", System.currentTimeMillis());
				
				PendingIntent pendingIntent = null;
				pendingIntent = PendingIntent.getActivity(context, 0, new Intent(
						context, LodaingFirstActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET),
						PendingIntent.FLAG_UPDATE_CURRENT);
				notification.setLatestEventInfo(context, "Walk Match", "Are you walking?" ,
						pendingIntent);

				notification.flags = Notification.FLAG_AUTO_CANCEL;
				nm.cancel(0);
				nm.notify(0, notification);
			}
			
			
		}
		//게임 끝
		else if(checkData.equals("2"))
		{
			
			//7일다 됬을때
			String value = null;
			String key = "msg";
			value = b.get(key).toString();
			Log.d("gcm_end",value+"엔드데이터 ");
			String winner_step = null;
			String key2 = "winner_step";
			winner_step = b.get(key2).toString();
			String lose_step = null;
			String key3 = "lose_step";
			lose_step = b.get(key3).toString();
			MainActivity.winner_step = winner_step;
			MainActivity.lose_step = lose_step;
			if(value.equals("0"))
			{
				whoWin = 0;
			}
			else if(value.equals(MainActivity.fri_email))
			{
				//내가 진거
				whoWin = 1;
			}
			else
			{
				//내가 이긴거
				whoWin = 2;
			}
			
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
				NotificationManager nm = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.appicon,
						"Walk Match", System.currentTimeMillis());
				
				PendingIntent pendingIntent = null;
				pendingIntent = PendingIntent.getActivity(context, 0, new Intent(
						context, LodaingFirstActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET),
						PendingIntent.FLAG_UPDATE_CURRENT);
				notification.setLatestEventInfo(context, "Walk Match", value+"You Win!",
						pendingIntent);

				notification.flags = Notification.FLAG_AUTO_CANCEL;
				nm.cancel(0);
				nm.notify(0, notification);
			}
		}
		else if(checkData.equals("3"))
		{
			//상대가 포기했을때
			String value = null;
			String key = "msg";
			value = b.get(key).toString();
			receiveGiveUp = true;
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
				NotificationManager nm = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.appicon,
						"Walk Match", System.currentTimeMillis());
				
				PendingIntent pendingIntent = null;
				pendingIntent = PendingIntent.getActivity(context, 0, new Intent(
						context, LodaingFirstActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET),
						PendingIntent.FLAG_UPDATE_CURRENT);
				notification.setLatestEventInfo(context, "Walk Match", value+"You Win!",
						pendingIntent);

				notification.flags = Notification.FLAG_AUTO_CANCEL;
				nm.cancel(0);
				nm.notify(0, notification);
			}
			
		}
		else if(checkData.equals("4"))
		{
			//상대를 찾게될경우
			String value = null;
			String key = "msg";
			value = b.get(key).toString();
			checkMatchReGCM= true;
			matchUpEmail = value;
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
				NotificationManager nm = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.appicon,
						"Walk Match", System.currentTimeMillis());
				
				PendingIntent pendingIntent = null;
				pendingIntent = PendingIntent.getActivity(context, 0, new Intent(
						context, LodaingFirstActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET),
						PendingIntent.FLAG_UPDATE_CURRENT);
				notification.setLatestEventInfo(context, "Walk Match", "Match Start",
						pendingIntent);

				notification.flags = Notification.FLAG_AUTO_CANCEL;
				nm.cancel(0);
				nm.notify(0, notification);
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onError(Context context, String errorId) {
		Log.d(tag, "onError. errorId : " + errorId);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.d(tag, "onRegistered. regId : " + regId);
		LodaingFirstActivity.regId = regId;
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d(tag, "onUnregistered. regId : " + regId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.d(tag, "onRecoverableError. errorId : " + errorId);
		return super.onRecoverableError(context, errorId);
	}
}