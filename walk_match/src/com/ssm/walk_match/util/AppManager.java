package com.ssm.walk_match.util;

import android.app.Activity;

public class AppManager {

	private Activity activity;
	private static AppManager instance = new AppManager();
	
	private AppManager()
	{
		
	}
	public static AppManager getInstance()
	{
		return instance;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
}
