package com.ssm.walk_match;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class BootingService extends Service
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

	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
		SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
		int year = sp.getInt("year", 0);
		int month = sp.getInt("month", 0);
		int day = sp.getInt("day", 0);
		if(year==0||month==0||day==0)
		{
			return; //알람 없는것
		}
		AlarmManager alm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Intent intent2;
		PendingIntent sender;
		intent2 = new Intent(BootingService.this,AlarmReceiver.class);
		sender = PendingIntent.getBroadcast(BootingService.this,0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
		GregorianCalendar at = new GregorianCalendar(year,month,day,9,0);
		alm.set(AlarmManager.RTC_WAKEUP, at.getTimeInMillis(), sender);
	}
}


